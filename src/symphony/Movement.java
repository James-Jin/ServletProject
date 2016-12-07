package symphony;

import java.util.*;
import sql.*;

/**
 * Class representing a Movement in a composition.
 */
public class Movement		{
	/* CREATORS	-----------------------------------------------------	*/
	/**
	 *	Create an instance of a Movement.
	 *  @param movementNumber represents the position of a movement in the composition 
	 *  @param movementName The name of the movement
	 *	@return	An instance of a Movement entity.
	 *	@throws sql.CreateException
	 */
	public static Movement create(int movementNumber,
											String movementName)
													throws CreateException	{

		MovementModel model = new MovementModel(movementNumber, movementName);
		MovementDAO dao = null;
		try	{
			dao = (MovementDAO) DAOFactory.getDAO(className);
			dao.dbInsert(model);

		} catch (Exception sqlex)	{
			throw new CreateException(sqlex.getMessage());
		}

		return	new Movement(model);
	}

	/* FINDERS	-----------------------------------------------------	*/
	/*	Finder methods are used to search for a particular instance
	 *	or a collection of instances, therefore finders either return
	 *	an instance to the entity, or a collection of instances.
	 */
	/**
	 *	Find a Movement by its primary key.
	 *	@return	An instance of an existing Movement entity.
	 *	@throws sql.FinderException
	 * @throws sql.NoSuchEntityException
	 * @param	primarykey	The primary key of the Movement to find.
	 */
	public static Movement findByPrimarykey(MovementPK primarykey)
								throws FinderException, NoSuchEntityException			{

		MovementModel model = null;
		Movement entity = null;
		MovementDAO dao = null;
		try	{
			dao = (MovementDAO) DAOFactory.getDAO(className);
			model = (MovementModel) dao.dbSelectByPrimaryKey(primarykey);
			entity  = new Movement(model);

		} catch (DAOSysException | NoSuchEntityException sqlex)	{
			throw new FinderException(sqlex.getMessage());
		}

		return entity;
	}

	/**
	 *	Find all Movement entities.
	 *	@return	A collection of Movement instances.
	 *	@throws	FinderException
	 * @throws	CreateException
	 */
	public static Collection<Movement> findAll() throws FinderException, CreateException			{
		ArrayList<Movement> listOfMovements = new ArrayList<>();
		MovementDAO dao = null;

		try	{
			dao = (MovementDAO) DAOFactory.getDAO(className);
			Collection<MovementPK> c = dao.dbSelectAll();
			Iterator<MovementPK> itr = c.iterator();
			while (itr.hasNext())	{
				MovementPK cpk = itr.next();
				try	{
					Movement entity = Movement.findByPrimarykey(cpk);

					/* TODO:	build/get any other objects for this Movement here		*/					
					listOfMovements.add(entity);

				} catch (FinderException | NoSuchEntityException ex)	{
					System.err.println("Movement: Error processing list <" + ex.toString());
				}
			}

		} catch (Exception sqlex)	{
			sqlex.printStackTrace();
			throw new CreateException(sqlex.getMessage());
		}


		return listOfMovements;
	}
	

	public static Collection<Movement> findByCompositionName(String compositionName) throws FinderException, CreateException			{
		ArrayList<Movement> listOfMovements = new ArrayList<>();
		MovementDAO dao = null;
		
		try	{
			dao = (MovementDAO) DAOFactory.getDAO(className);
			Collection<MovementPK> c = dao.dbSelectByCompositionName(compositionName);
			Iterator<MovementPK> itr = c.iterator();
			while (itr.hasNext())	{
				MovementPK cpk = itr.next();
				try	{
					Movement entity = Movement.findByPrimarykey(cpk);

					listOfMovements.add(entity);

				} catch (FinderException | NoSuchEntityException ex)	{
					System.err.println("Movement: Error processing list <" + ex.toString());
				}
			}

		} catch (Exception sqlex)	{
			sqlex.printStackTrace();
			throw new CreateException(sqlex.getMessage());
		}


		return listOfMovements;
	}


	/* REMOVERS	-----------------------------------------------------	*/
	/**
	 *	Remove a Movement by primary key.
	 *	@param	primarykey	The primary key for the Movement to find.
	 *	@throws	ObjectNotFoundException
	 */
	private static int removeByPrimarykey(MovementPK primarykey)
								throws	DAOSysException, NoSuchEntityException	{
		int rc = 0;
		MovementDAO dao = null;

		dao = (MovementDAO) DAOFactory.getDAO(className);
		rc = dao.dbRemove(primarykey);

		return rc;
	}


	/* CONSTRUCTORS	-----------------------------------------------	*/
	/**
	 *	Default constructor
	 */
	private Movement()	{ super();		}

	/**
	 *	Parameterized constructor.
	 *  @param movementNumber represents the position of a movement in the composition 
	 *  @param movementName The name of the movement
	 */
	private Movement(int movementNumber, String movementName)		{
		this(new MovementModel(movementNumber, movementName));
	}

	/**
	 *	Parameterized constructor.
	 *	@param	model	The persistence model for a Movement object.
	 */
	private Movement(MovementModel model)	{
		setModel(model);

	}


	/* ACCESSORS	--------------------------------------------------	*/
	public MovementModel getModel()			{ return model;												}
	public MovementPK getPrimaryKey()			{ return getModel().getPrimarykey();					}
	public String getMovementName()						{ return getModel().getPrimarykey().getMovementName(); 		}
 	public int getMovementNumber()						{ return getModel().getPrimarykey().getMovementNumber(); }


	/* MODIFIERS	--------------------------------------------------	*/
	private void setModel(MovementModel model)	{ this.model = model;								}

	private void setPrimarykey(MovementPK pk)	{ getModel().setPrimarykey(pk);						}


	/* BEHAVIOR	-----------------------------------------------------	*/
	/**
	 *	Implementation of the "object" equals method.  Movement objects are equal
	 *	if their names and numbers are equal.
	 *	@return	True if the fields of this Movement object equal the
	 *	contents of the fields from the passed Movement object, otherwise
	 *	false, they are not equal.
	 */
	@Override
	public boolean equals(Object obj)	{
		return	obj instanceof Movement
			&&	(getMovementName().equals(((Movement) obj).getMovementName())
			&& getMovementNumber() == ((Movement) obj).getMovementNumber());
	}

	/**
	 *	Implementation of the "object"hashCode()" method.
	 * Whenever it is invoked on the same object more than once during
	 * an execution of a Java application, the hashCode method
	 * must consistently return the same integer, provided no information
	 * used in equals comparisons on the object is modified.
	 *	@return	A hash code value for the object.
	 */
	@Override
	public int hashCode() {
		return	getMovementName().concat(getMovementNumber()+"").hashCode();
	}

	/**
	 *	Flush cached attribute values to the datastore.
	 *	Catch and report any errors.
	 */
	public void update()	{
		try	{
			store();
		} catch (Exception ex)	{
			System.out.println("C: Error in update(), <" + ex.toString() + ">");
		}
	}


	@Override
	public String toString()	{ return this.toString(", ");				}
	public String toString(String sep)	{
		return "movement=" + getMovementName()
				+ sep + "movement name=" + getMovementName()
			;
	}

	/**
	 *	Remove a Movement from the data store (by primary key).
	 * @return
	 * @throws sql.NoSuchEntityException
	 * @throws sql.DAOSysException
	 */
	public Movement remove()	throws NoSuchEntityException, DAOSysException	{
		Movement c = null;
		if (removeByPrimarykey(getPrimaryKey()) > 0)	{
			c = this;
		}

		return c;
	}

	/**
	 * Invoke this method to refresh the cached attribute values
	 * from the database.
	 */
	private void load() throws DAOSysException		{
		MovementDAO dao = null;
		try	{
			dao = (MovementDAO) DAOFactory.getDAO(className);
			setModel((MovementModel)dao.dbLoad(getPrimaryKey()));

		} catch (DAOSysException | NoSuchEntityException ex)	{
			throw new DAOSysException(ex.getMessage());
		}
	}


	/**
	 * Invoke this method to save the cached attribute values to the datastore.
	 */
	private void store()	throws DAOSysException		{
		MovementDAO dao = null;
		try	{
			dao = (MovementDAO) DAOFactory.getDAO(className);
			dao.dbStore(getModel());
		} catch (Exception ex)	{
			throw new DAOSysException(ex.getMessage());
		}
	}



	/* ATTRIBUTES	--------------------------------------------------	*/
	private static final boolean _debug = false;

	/** Class name for static method purposes.								*/
	private static String className = "symphony.Movement";

	private MovementModel model;


	/* REFERENCE ATTRIBUTES	-----------------------------------------	*/


}	/*	End of CLASS:	Movement.java				*/