/*
 * CoreDAOImpl.java
 *
 *
 */

package sql;

import java.sql.*;

/**
 *	Core implementation of the DAO interface.  All DAO object should
 * extend this class.
 * @param MD	Model for this DAO object.
 * @param PK	Primary key for this DAO object.
 * @author Reg
 */
public abstract class CoreDAOImpl<MD, PK> implements CoreDAO<MD, PK> {
	/**
	 * Creates a new instance of CustomerDAO
	 */
	public CoreDAOImpl() { this(CoreDAO.DRIVER_NAME, CoreDAO.URL, CoreDAO.USER, CoreDAO.PASSWORD);		}

	/**
	 *	Parameterized constructor.  When extending this class the
	 *	derived class must invoke one of this classes constructors
	 *	for proper initialization.
	 *	@param	drivername	
	 *	@param	url		Database URL.
	 *	@param	username		Database user name.
	 *	@param	password	Database password for access.
	 */
	public CoreDAOImpl(String drivername,
						String url,
						String username,
						String password)	{
		this.driverName = drivername;
		this.url = url;
		this.username = username;
		this.password = password;
	}


	/* ACCESSORS	-----------------------------------------------	*/
	protected DatabaseMetaData	getDbMetaData()		{ return dbMetaData;			}
	protected String getDriverName()						{ return driverName;			}
	protected String getUrl()								{ return url;					}
	protected String getUsername()						{ return username;			}
	protected String getPassword()						{ return password;			}

	
	/* MUTATORS	--------------------------------------------------	*/
	protected void setDriverName(String dr)			{ driverName = dr;			}
	protected void setUrl(String url)					{ this.url = url;				}
	protected void setUsername(String user)			{ username = user;			}
	protected void setPassword(String pass)			{ password = pass;			}



	
	/* BEHAVIOR	--------------------------------------------------------	*/
	/**
	 * Called by load() to refresh entity attribute values from the database.
	 *	@return	The data structure for a Customer entity.
	 *	@throws	DAOSysException
	 * @throws	NoSuchEntityException
	 */
	@Override
	public MD dbLoad(PK primarykey)
			throws DAOSysException, NoSuchEntityException {
		return dbSelectByPrimaryKey(primarykey);
	}
	
	/**
	 * Called by load() to refresh entity attribute values from the database.
	 *	@param selectStm 
	 * @return	The data persistence model for the entity.
	 *	@throws	DAOSysException
	 * @throws	NoSuchEntityException
	 */
	@Override
	public MD dbLoad(PK primarykey, String selectStm)
				throws DAOSysException, NoSuchEntityException {
		return dbSelectByPrimaryKey(primarykey, selectStm);
	}

	
	/**
	 * Update state for an entity in the database.
	 *	@param	model	Persistence model for the entity.
	 *	@throws	DAOSysException
	 */
	@Override
	public void dbStore(MD model)	throws DAOSysException {
		dbUpdate(model);
	}
	
	
	/**
	 * Called by store(MD model) to update state for an entity in the database.
	 *	@param	model	Persistence model for the entity.
	 *	@param	storeStm	Statement to store the data in the data store.
	 *	@throws	DAOSysException
	 */
	@Override
	public void dbStore(MD model, String storeStm)	throws DAOSysException {
		dbUpdate(model, storeStm);
	}



	
	/**
	 *	Connection to the database using the current attribute settings.
	 * @return Database connection.
	 * @throws java.sql.SQLException 
	 */
	protected Connection connectToDB() throws SQLException	{
		return connectToDB(getDriverName(), getUrl(), getUsername(), getPassword());
	}

	/**
	 *	Connect to the database.
	 *	@param	driver	Database driver.
	 *	@param	url		Database URL.
	 *	@param	username		Database user name.
	 *	@param	password	Database password for access.
	 *	@return  Database connection.
	 * @throws	SQLException	If a SQL error occurs trying to open a connection.
	 */
	protected Connection connectToDB(String driver,
											 String url,
											 String username,
											 String password)
					throws SQLException		{
		Connection connection = null;
		try {
			/* Try to instantiate the driver.											*/
			Class.forName(driver).newInstance();
			connection = DriverManager.getConnection(url, username, password);

		}	catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
			System.err.println("Fatal error connecting to database <" + ex.toString() + ">");
			System.err.println("\tDriver:" + driver
							+ "\n\tURL:" + url
							+ "\n\tusername:" + username
							+ "\n\tpassword:" + password
							);
			ex.printStackTrace();
			throw new SQLException(ex.getMessage());
		}
		
		return connection;
	}
	
	
	/**
	 *	Release the acquired resources.  Successful execution of this method
	 *	results in both the statement and connection being closed and set to
	 *	null references.
	 * @param rs
	 * @param stm 
	 * @param con
	 * @throws java.sql.SQLException 
	 */
	public void releaseAll(ResultSet rs, PreparedStatement stm, Connection con) throws SQLException	{
		try {
			releaseResultSet(rs);
			releaseStatement(stm);
			releaseConnection(con);

		} catch (SQLException sqlex) {
			System.err.println("Fatal error releasing resources:" + sqlex.toString());
			sqlex.printStackTrace();
		}
	}

	/**
	 *	Close and set the result set object reference to a null.
	 * 
	 * @param rs
	 * @throws java.sql.SQLException 
	 */
	public void releaseResultSet(ResultSet rs) throws SQLException	{
		try	{
			if (rs != null)	{
				rs.close();
			}
			rs = null;

		} catch (SQLException sqlex) {
			System.err.println("Fatal error releasing statement resource:" + sqlex.toString());
			sqlex.printStackTrace();
			throw sqlex;
		}
	}

	/**
	 *	Close and set the statement object reference to a null.
	 * 
	 * @param stm
	 * @throws java.sql.SQLException 
	 */
	public void releaseStatement(PreparedStatement stm) throws SQLException	{
		try	{
			if (stm != null)	{
				stm.close();
			}
			stm = null;

		} catch (SQLException sqlex) {
			System.err.println("Fatal error releasing statement resource:" + sqlex.toString());
			sqlex.printStackTrace();
			throw sqlex;
		}
	}


	/**
	 *	Close and set the connection object reference to a null.
	 * 
	 * @param con
	 * @throws java.sql.SQLException 
	 */
	public void releaseConnection(Connection con) throws SQLException	{
		try	{
			if (con != null)	{
				con.close();
			}
			con = null;

		} catch (SQLException sqlex) {
			System.err.println("Fatal error releasing connection resource:" + sqlex.toString());
			sqlex.printStackTrace();
			throw sqlex;
		}
	}

	/* ATTRIBUTES	-----------------------------------------------	*/
	/** Database driver.																		*/
	protected String driverName;

	/** Database URL.																			*/
	protected String url;

	/** User name for access to database.												*/
	protected String username;

	/** Password for access to database.												*/
	protected String password;

	/** Database meta data.																	*/
	protected DatabaseMetaData dbMetaData;

}	/*	END OF CLASS:	CoreDAOImpl.java				*/
