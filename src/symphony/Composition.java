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
public class Composition		{
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
	public static Composition create(String composer,
											String compositionName)
													throws CreateException	{

		CompositionModel model = new CompositionModel(composer, compositionName);
		CompositionDAO dao = null;
		try	{
			dao = (CompositionDAO) DAOFactory.getDAO(className);
			dao.dbInsert(model);
			/* This Indywinner has no other objects to reference			*/

		} catch (Exception sqlex)	{
			throw new CreateException(sqlex.getMessage());
		}

		return	new Composition(model);
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
	public static Composition findByPrimarykey(CompositionPK primarykey)
								throws FinderException, NoSuchEntityException			{

		CompositionModel model = null;
		Composition entity = null;
		CompositionDAO dao = null;
		try	{
			dao = (CompositionDAO) DAOFactory.getDAO(className);
			model = (CompositionModel) dao.dbSelectByPrimaryKey(primarykey);
			entity  = new Composition(model);

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
	public static Collection<Composition> findAll() throws FinderException, CreateException			{
		ArrayList<Composition> listOfCompositions = new ArrayList<>();
		CompositionDAO dao = null;

		try	{
			dao = (CompositionDAO) DAOFactory.getDAO(className);
			Collection<CompositionPK> c = dao.dbSelectAll();
			Iterator<CompositionPK> itr = c.iterator();
			while (itr.hasNext())	{
				CompositionPK cpk = itr.next();
				try	{
					Composition entity = Composition.findByPrimarykey(cpk);

					/* TODO:	build/get any other objects for this IndyWinner here		*/


					/* Add this Indywinner to the list.						*/
					listOfCompositions.add(entity);

				} catch (FinderException | NoSuchEntityException ex)	{
					System.err.println("Composition: Error processing list <" + ex.toString());
				}
			}

		} catch (Exception sqlex)	{
			sqlex.printStackTrace();
			throw new CreateException(sqlex.getMessage());
		}


		return listOfCompositions;
	}
	
	/**
	 *	Find Indywinner entities based a primary key starting point and retrieve a limited number of entities.
	 *	@return	A collection of Indywinner instances.
	 *	@throws	FinderException
	 * @throws	CreateException
	 */
//	public static Collection<Composition> findSetGreaterThan(int year, int setSize) throws FinderException, CreateException			{
//		ArrayList<Composition> listOfIndyWinners = new ArrayList<>();
//		CompositionDAO dao = null;
//		CompositionPK primarykey = new CompositionPK(year);
//		
//		try	{
//			dao = (CompositionDAO) DAOFactory.getDAO(className);
//			Collection<CompositionPK> c = dao.dbSelectSetByYear(primarykey, setSize);
//			Iterator<CompositionPK> itr = c.iterator();
//			while (itr.hasNext())	{
//				CompositionPK cpk = itr.next();
//				try	{
//					Composition entity = Composition.findByPrimarykey(cpk);
//
//					/* TODO:	build/get any other objects for this IndyWinner here		*/
//
//
//					
//					
//					/* Add this Indywinner to the list.						*/
//					listOfIndyWinners.add(entity);
//
//				} catch (FinderException | NoSuchEntityException ex)	{
//					System.err.println("IndyWinner: Error processing list <" + ex.toString());
//				}
//			}
//
//		} catch (Exception sqlex)	{
//			sqlex.printStackTrace();
//			throw new CreateException(sqlex.getMessage());
//		}
//
//
//		return listOfIndyWinners;
//	}


	/* REMOVERS	-----------------------------------------------------	*/
	/**
	 *	Remove an Indywinner by primary key.
	 *	@param	primarykey	The primary key for the Indywinner to find.
	 *	@throws	ObjectNotFoundException
	 */
	private static int removeByPrimarykey(CompositionPK primarykey)
								throws	DAOSysException, NoSuchEntityException	{
		int rc = 0;
		CompositionDAO dao = null;

		/*	TODO:	remove any other objects here first ...				*/



		/* ...then remove the Indy Winner									*/
		dao = (CompositionDAO) DAOFactory.getDAO(className);
		rc = dao.dbRemove(primarykey);

		return rc;
	}


	/* CONSTRUCTORS	-----------------------------------------------	*/
	/**
	 *	Default constructor
	 */
	private Composition()	{ super();		}

	/**
	 *	Parameterized constructor.
	 * @param year	The year this driver won the Indy.
	 * @param driver	The winning driver for the year of the race.
	 * @param averageSpeed	The average speed the driver attained for the year of the race.
	 */
	private Composition(String composer, String compositionName)		{
		this(new CompositionModel(composer, compositionName));
	}

	/**
	 *	Parameterized constructor.
	 *	@param	model	The persistence model for a Indywinner object.
	 */
	private Composition(CompositionModel model)	{
		setModel(model);

		/* TODO:	add any other initialization requirements here			*/

	}


	/* ACCESSORS	--------------------------------------------------	*/
	public CompositionModel getModel()			{ return model;												}
	public CompositionPK getPrimaryKey()			{ return getModel().getPrimarykey();					}
	public String getCompositionName()						{ return getModel().getPrimarykey().getCompositionName(); 		}
 	public String getComposer()						{ return getModel().getComposer();							}


	/* MODIFIERS	--------------------------------------------------	*/
	private void setModel(CompositionModel model)	{ this.model = model;								}

	private void setPrimarykey(CompositionPK pk)	{ getModel().setPrimarykey(pk);						}
	public void setComposer(String composer)				{
		getModel().setComposer(composer);
		update();
	}


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
		return	obj instanceof Composition
			&&	(getCompositionName().equals(((Composition) obj).getCompositionName())
			);
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
		return	getCompositionName().concat(getComposer()).hashCode();
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
		return "compositionName=" + getCompositionName()
				+ sep + "composer=" + getComposer()
			;
	}

	/**
	 *	Remove a Indywinner from the data store (by primary key).
	 * @return
	 * @throws sql.NoSuchEntityException
	 * @throws sql.DAOSysException
	 */
	public Composition remove()	throws NoSuchEntityException, DAOSysException	{
		Composition c = null;
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
		CompositionDAO dao = null;
		try	{
			dao = (CompositionDAO) DAOFactory.getDAO(className);
			setModel((CompositionModel)dao.dbLoad(getPrimaryKey()));

		} catch (DAOSysException | NoSuchEntityException ex)	{
			throw new DAOSysException(ex.getMessage());
		}
	}


	/**
	 * Invoke this method to save the cached attribute values to the datastore.
	 */
	private void store()	throws DAOSysException		{
		CompositionDAO dao = null;
		try	{
			dao = (CompositionDAO) DAOFactory.getDAO(className);
			dao.dbStore(getModel());
		} catch (Exception ex)	{
			throw new DAOSysException(ex.getMessage());
		}
	}



	/* ATTRIBUTES	--------------------------------------------------	*/
	private static final boolean _debug = false;

	/** Class name for static method purposes.								*/
	private static String className = "symphony.Composition";

	/** Persistence model for an Indy Winner object.							*/
	private CompositionModel model;


	/* REFERENCE ATTRIBUTES	-----------------------------------------	*/


}	/*	End of CLASS:	Composition.java				*/