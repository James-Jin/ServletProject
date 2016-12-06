/*
 *  @(#)IndyWinner.java
 *
 *
 */

package symphony;

import java.util.*;
import sql.*;

/**
 * Class representing an Indianapolis 500 race winner.
 */
public class Movement		{
	/* STATIC PRE-OBJECT BEHAVIOR	-----------------------------------	*/
	/* CREATORS	-----------------------------------------------------	*/
	/**
	 *	Create an instance of a new Indy winner.
	 * @param year	The year this driver won the Indy.
	 * @param driver	The winning driver for the year of the race.
	 * @param averageSpeed	The average speed the driver attained for the year of the race.
	 *	@return	An instance of an IndyWinner entity.
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
			/* This Indywinner has no other objects to reference			*/

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
	 *	Find an Indy winner by its primary key.
	 *	@return	An instance of an existing Indy winner entity.
	 *	@throws sql.FinderException
	 * @throws sql.NoSuchEntityException
	 * @param	primarykey	The primary key of the IndyWinner to find.
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

			/* TODO:	add any other objects for this IndyWinner here		*/



		} catch (DAOSysException | NoSuchEntityException sqlex)	{
			throw new FinderException(sqlex.getMessage());
		}

		return entity;
	}

	/**
	 *	Find all Indywinner entities.
	 *	@return	A collection of Indywinner instances.
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

					/* TODO:	build/get any other objects for this IndyWinner here		*/					
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
		ArrayList<Movement> listOfIndyWinners = new ArrayList<>();
		MovementDAO dao = null;
		
		try	{
			dao = (MovementDAO) DAOFactory.getDAO(className);
			Collection<MovementPK> c = dao.dbSelectByCompositionName(compositionName);
			Iterator<MovementPK> itr = c.iterator();
			while (itr.hasNext())	{
				MovementPK cpk = itr.next();
				try	{
					Movement entity = Movement.findByPrimarykey(cpk);

					/* TODO:	build/get any other objects for this IndyWinner here		*/


					
					
					/* Add this Indywinner to the list.						*/
					listOfIndyWinners.add(entity);

				} catch (FinderException | NoSuchEntityException ex)	{
					System.err.println("IndyWinner: Error processing list <" + ex.toString());
				}
			}

		} catch (Exception sqlex)	{
			sqlex.printStackTrace();
			throw new CreateException(sqlex.getMessage());
		}


		return listOfIndyWinners;
	}


	/* REMOVERS	-----------------------------------------------------	*/
	/**
	 *	Remove an Indywinner by primary key.
	 *	@param	primarykey	The primary key for the Indywinner to find.
	 *	@throws	ObjectNotFoundException
	 */
	private static int removeByPrimarykey(MovementPK primarykey)
								throws	DAOSysException, NoSuchEntityException	{
		int rc = 0;
		MovementDAO dao = null;

		/*	TODO:	remove any other objects here first ...				*/



		/* ...then remove the Indy Winner									*/
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
	 * @param year	The year this driver won the Indy.
	 * @param driver	The winning driver for the year of the race.
	 * @param averageSpeed	The average speed the driver attained for the year of the race.
	 */
	private Movement(int movementNumber, String movementName)		{
		this(new MovementModel(movementNumber, movementName));
	}

	/**
	 *	Parameterized constructor.
	 *	@param	model	The persistence model for a Indywinner object.
	 */
	private Movement(MovementModel model)	{
		setModel(model);

		/* TODO:	add any other initialization requirements here			*/

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
	 *	Implementation of the "object" equals method.  IndyWinners objects are equal
	 *	if their primary key's are equal.
	 *	@return	True if the fields of this primary key object equal the
	 *	contents of the fields from the passed primary key object, otherwise
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
	 *	Remove a Indywinner from the data store (by primary key).
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