package symphony;

import java.sql.*;
import java.util.*;

import sql.CoreDAO;
import sql.CoreDAOImpl;
import sql.DAOSysException;
import sql.NoSuchEntityException;

/**
 *	Data access object for Movement data.  This class bridges the
 *	object to non-object data-store layer.
 */
public class MovementDAO extends CoreDAOImpl<MovementModel, MovementPK>	{
	/**
	 * Creates a new instance of MovementDAO
	 */
	public MovementDAO() { this(CoreDAO.DRIVER_NAME, CoreDAO.URL, CoreDAO.USER, CoreDAO.PASSWORD);		}

	/**
	 *	Parameterized constructor.  When extending this class the
	 *	derived class must invoke one of this classes constructors
	 *	for proper initialization.
	 *	@param drivername
	 * @param url
	 * @param	user		Database user name.
	 *	@param	password	Database password for access.
	 */
	public MovementDAO(String drivername,
						String url,
						String user,
						String password)	{
		super(drivername, url, user, password);
	}


	/* BEHAVIOR	--------------------------------------------------------	*/
	/**
	 * Called by create() to insert entity state into the data store for a new entity.
	 *	@param	model	Persistence data model for the entity.
	 *	@throws	DAOSysException
	 */
	@Override
	public void dbInsert(MovementModel model)	throws DAOSysException {
		dbInsert(model, MovementDAO.INSERT_STM);
	}


	/**
	 * Called by create() to insert entity state into the data store for a new entity.
	 *	@param	model	Persistence data model for the entity.
	 *	@param	insertStm	Data store statement for inserting into the data store.
	 *	@throws	DAOSysException
	 */
	@Override
	public void dbInsert(MovementModel model, String insertStm) throws DAOSysException		{
		PreparedStatement preparedStm = null;
		Connection connection = null;

		try	{
			connection = connectToDB();
			preparedStm = connection.prepareStatement(insertStm);
			preparedStm.setInt(1, model.getMovementNumber());
			preparedStm.setString(2, model.getMovementName());
			preparedStm.executeUpdate();

		}	catch (SQLException sex)	{
			throw new DAOSysException("Error adding composition <" + model.getMovementNumber() + "   "+ model.getMovementName() + "> " + sex.getMessage());

		}	finally	{
			try	{
				releaseAll(null, preparedStm, connection);
			} catch (Exception ex)	{
				System.err.println("Error releasing resources <" + ex.toString());
			}
		}
	}

	/**
	 * Called by findByPrimaryKey() to retrieve an entity by the primary key.
	 *	@param	primarykey The primary key for an entity.
	 *	@return	The persistence model for the entity, else null entity data is not found.
	 * @throws sql.DAOSysException
	 * @throws sql.NoSuchEntityException
	 */
	@Override
	public MovementModel dbSelectByPrimaryKey(MovementPK primarykey)
				throws DAOSysException, NoSuchEntityException	{
		return dbSelectByPrimaryKey(primarykey, MovementDAO.SELECT_STM);
	}

	/**
	 * Called by findByPrimaryKey() to retrieve an entity by the primary key.
	 *	@param	primarykey The primary key for an entity.
	 *	@param	selectStm	Data store statement to get the entity.
	 *	@return	The persistence model for the entity, else null entity data is not found.
	 */
	@Override
	public MovementModel dbSelectByPrimaryKey(MovementPK primarykey, String selectStm)
				throws DAOSysException, NoSuchEntityException	{
		
		MovementPK pk = primarykey;
		Connection connection = null;
		PreparedStatement preparedStm = null;
		ResultSet rs = null;
		boolean result = false;
		MovementModel model = new MovementModel();
		try	{
			connection = connectToDB();
			preparedStm = connection.prepareStatement(selectStm);
			preparedStm.setInt(1, pk.getMovementNumber());
			preparedStm.setString(2, pk.getMovementName());
			rs = preparedStm.executeQuery();

			result = rs.next();
			if (result)	{
				model.setPrimarykey(new MovementPK(rs.getInt(1), rs.getString(2)));
	
			}	else	{
				throw new NoSuchEntityException("movement for <"
						+ primarykey + "> not found in the database.");
			}

		}	catch (SQLException sex)	{
			sex.printStackTrace();
			throw new DAOSysException(
				"dbSelectByPrimaryKey() SQL Exception\n"
				+ sex.getMessage());

		}	finally	{
			try	{
				releaseAll(rs, preparedStm, connection);
			} catch (Exception ex)	{
				System.err.println("Error releasing resources <" + ex.toString());
			}
		}

		return model;

	}

	/**
	 * Called by findAll() to find all entities in the data store.
	 *	@return	A collection of primary keys representing all of the entities.
	 */
	@Override
	public Collection<MovementPK> dbSelectAll()	throws DAOSysException {
		return dbSelectAll(MovementDAO.SELECT_ALL_STM);
	}


	/**
	 * Called by findAll() to find all entities in the data store.
	 *	@param selectStm
	 * @return	A collection of primary keys representing all of the entities.
	 */
	@Override
	public Collection<MovementPK> dbSelectAll(String selectStm)	throws DAOSysException {
		Connection connection = null;
		PreparedStatement preparedStm = null;
		ResultSet rs = null;
		ArrayList<MovementPK> list = null;

		try	{
			connection = connectToDB();
			preparedStm = connection.prepareStatement(selectStm);
			rs = preparedStm.executeQuery();

			list = new ArrayList<>();
			while (rs.next())	{
				list.add(new MovementPK(rs.getInt("movementNumber"), rs.getString("movementName")));
			}

		}	catch (SQLException sex)	{
			throw new DAOSysException(
						"dbSelectAll() SQL Exception\n"
						+ sex.getMessage());
		}	finally	{
			try	{
				releaseAll(rs, preparedStm, connection);
			} catch (Exception ex)	{
				System.err.println("Error releasing resources <" + ex.toString());
			}
		}

		return list;
	}

	
	/**
	 * Called by findAll() to find all entities in the data store.
	 * @return	A collection of primary keys representing all of the entities.
	 */
	public Collection<MovementPK> dbSelectByCompositionName(String compositionName)	throws DAOSysException {
		return dbSelectByCompositionName(MovementDAO.SELECT_BY_COMPOSITION_NAME,compositionName);
	}


	/**
	 * Called by findAll() to find all entities in the data store.
	 * @param selectStm
	 * @return	A collection of primary keys representing all of the entities.
	 */
	public Collection<MovementPK> dbSelectByCompositionName(String selectStm,  String compositionName)	throws DAOSysException {
		Connection connection = null;
		PreparedStatement preparedStm = null;
		ResultSet rs = null;
		ArrayList<MovementPK> list = null;

		try	{
			connection = connectToDB();
			preparedStm = connection.prepareStatement(selectStm);
			preparedStm.setString(1, compositionName);
			rs = preparedStm.executeQuery();

			list = new ArrayList<>();
			int count = 0;
			while (rs.next()){
				list.add(new MovementPK(rs.getInt(1), rs.getString(2)));
				count++;
			}

		}	catch (SQLException sex)	{
			throw new DAOSysException(
						"dbSelectByCompositionName() SQL Exception\n"
						+ sex.getMessage());
		}	finally	{
			try	{
				releaseAll(rs, preparedStm, connection);
			} catch (Exception ex)	{
				System.err.println("Error releasing resources <" + ex.toString());
			}
		}

		return list;
	}

	
	/**
	 * Called by update() to update state for an entity in the database.
	 *	@param	data	Persistence model for the entity.
	 *	@throws	DAOSysException
	 */
	@Override
	public void dbUpdate(MovementModel data)	throws DAOSysException	{
		dbUpdate(data, MovementDAO.UPDATE_STM);
	}

	/**
	 * Called by update() to update state for an entity in the database.
	 *	@param	data	Persistence model for the entity.
	 *	@param	updateStm	Data store update statement.
	 *	@throws	DAOSysException
	 */
	@Override
	public void dbUpdate(MovementModel data, String updateStm)	throws DAOSysException {
//		MovementModel model = data;
//		Connection connection = null;
//		PreparedStatement preparedStm = null;
//		try	{
//			connection = connectToDB();
//			preparedStm = connection.prepareStatement(updateStm);
//
//			/*	Grab values from persistent fields to store in database	*/
//			preparedStm.setString(1, model.getComposer());
//
// 			int rowCount = preparedStm.executeUpdate();
//			if (rowCount == 0)	{
// 				throw new DAOSysException(
// 					"Failed to store state for Movement <"
// 					+ model.getCompostionName() + ">");
// 			}
//
//		}	catch (SQLException sex)	{
//			throw new DAOSysException(
//					"dbUpdate() SQL Exception <"
//					+ sex.getMessage() + ">");
//
//		}	finally	{
//			try	{
//				releaseAll(null, preparedStm, connection);
//			} catch (Exception ex)	{
//				System.err.println("Error releasing resources <" + ex.toString());
//			}
//		}
	}

	/**
	 * Called by remove() to remove the state for an entity from the data store.
	 *	@param	primarykey	The primary key of the entity to be removed.
	 *	@return
	 * @throws	DAOSysException
	 */
	@Override
	public int dbRemove(MovementPK primarykey)	throws DAOSysException	{
		return dbRemove(primarykey, MovementDAO.DELETE_STM);
	}


	/**
	 * Called by remove() to remove the state for a Movement entity from the database.
	 *	@param	primarykey	The primary key of the Movement entity
	 *	to be removed from the data store.
	 *	@param	deleteStm	Statement to remove entity data from the data store.
	 *	@throws	DAOSysException
	 */
	@Override
	public int dbRemove(MovementPK primarykey, String deleteStm)	throws DAOSysException	{
		MovementPK pk = primarykey;
		Connection connection = null;
		PreparedStatement preparedStm = null;
		int result = 0;

		try	{
			connection = connectToDB();
			preparedStm = connection.prepareStatement(deleteStm);
			preparedStm.setInt(1, pk.getMovementNumber());
			preparedStm.setString(2, pk.getMovementName());
			result = preparedStm.executeUpdate();

			if (result == 0)	{
				throw new SQLException(
						"Failed to remove Movement <"
						+ pk.toString() + ">.");
			}

		}	catch (SQLException sex)	{
			throw new DAOSysException(
					"dbRemove() SQL Exception <" + pk.toString() + "> " + sex.getMessage());

		}	finally	{
			try	{
				releaseAll(null, preparedStm, connection);
			} catch (SQLException sqlex)	{
				throw new DAOSysException(sqlex.toString());
			}
		}

		return result;
	}


	/**
	 * Called by getTotalEntities().
	 *	@return	The total number of entities in the data store.
	 *	@throws	DAOSysException
	 */
	@Override
	public int dbCountTotalEntities()	throws DAOSysException	{
		Connection connection = null;
		PreparedStatement preparedStm = null;
		ResultSet rs = null;
		int count = 0;

		try	{
			connection = connectToDB();
			/*	Request a resultset that is scrollable to easily count rows	*/
			preparedStm = connection.prepareStatement(
										MovementDAO.SELECT_DISTINCT_STM,
										ResultSet.TYPE_SCROLL_INSENSITIVE,
										ResultSet.CONCUR_UPDATABLE);
			rs = preparedStm.executeQuery();

			/*	Go to the last row and get its row number							*/
			rs.last();
			count = rs.getRow();

		}	catch (SQLException sex)	{
			throw new DAOSysException(
						"dbCountTotalEntities() SQL Exception\n"
						+ sex.getMessage());

		}	finally	{
			try	{
				releaseAll(rs, preparedStm, connection);
			} catch	(SQLException sqlex)	{
				throw new DAOSysException(sqlex.toString());
			}
		}

		return count;
	}



	/* ATTRIBUTES	-----------------------------------------------	*/
	private final static boolean _debug = false;

	private static String SELECT_DISTINCT_STM =
		"SELECT DISTINCT movementNumber, movementName FROM " + "Movement";

	private static String DELETE_STM =
		"DELETE FROM " + "movement"
		+ " WHERE movementNumber = ? AND movementName = ?";

	//place holder, nothing to update for movement
	private static String UPDATE_STM =
		"UPDATE " + "movement"
		+ " SET "
		+ "composer = ? ";

	private static String SELECT_ALL_STM =
			"SELECT movementNumber, movementName " + "FROM " + "movement";
	
	private static String SELECT_BY_COMPOSITION_NAME = 
			"SELECT movementNumber, movementName FROM movements WHERE compositionName = ?";

	private static String SELECT_STM = "SELECT "
		+ " movementNumber, "
		+ " movementName "
		+ " FROM movement "
		+ " WHERE movementNumber = ?"
		+ " AND movementName = ?";

	private static String INSERT_STM = "INSERT INTO "
		+ "movement"
		+ " VALUES "
		+ "( ?, ? )";


}	/*	End of Class:	MovementDAO.java				*/
