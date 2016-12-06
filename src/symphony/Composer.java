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
public class Composer		{
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
	public static Composer create(String name)
													throws CreateException	{

		ComposerModel model = new ComposerModel(name);
		ComposerDAO dao = null;
		try	{
			dao = (ComposerDAO) DAOFactory.getDAO(className);
			dao.dbInsert(model);
			/* This Indywinner has no other objects to reference			*/

		} catch (Exception sqlex)	{
			throw new CreateException(sqlex.getMessage());
		}

		return	new Composer(model);
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
	public static Composer findByPrimarykey(ComposerPK primarykey)
								throws FinderException, NoSuchEntityException			{

		ComposerModel model = null;
		Composer entity = null;
		ComposerDAO dao = null;
		try	{
			dao = (ComposerDAO) DAOFactory.getDAO(className);
			model = (ComposerModel) dao.dbSelectByPrimaryKey(primarykey);
			entity  = new Composer(model);

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
	public static Collection<Composer> findAll() throws FinderException, CreateException			{
		ArrayList<Composer> listOfComposers = new ArrayList<>();
		ComposerDAO dao = null;

		try	{
			dao = (ComposerDAO) DAOFactory.getDAO(className);
			Collection<ComposerPK> c = dao.dbSelectAll();
			Iterator<ComposerPK> itr = c.iterator();
			while (itr.hasNext())	{
				ComposerPK cpk = itr.next();
				try	{
					Composer entity = Composer.findByPrimarykey(cpk);

					/* TODO:	build/get any other objects for this IndyWinner here		*/					
					listOfComposers.add(entity);

				} catch (FinderException | NoSuchEntityException ex)	{
					System.err.println("Composer: Error processing list <" + ex.toString());
				}
			}

		} catch (Exception sqlex)	{
			sqlex.printStackTrace();
			throw new CreateException(sqlex.getMessage());
		}


		return listOfComposers;
	}
	


	/* REMOVERS	-----------------------------------------------------	*/
	/**
	 *	Remove an Indywinner by primary key.
	 *	@param	primarykey	The primary key for the Indywinner to find.
	 *	@throws	ObjectNotFoundException
	 */
	private static int removeByPrimarykey(ComposerPK primarykey)
								throws	DAOSysException, NoSuchEntityException	{
		int rc = 0;
		ComposerDAO dao = null;

		/*	TODO:	remove any other objects here first ...				*/



		/* ...then remove the Indy Winner									*/
		dao = (ComposerDAO) DAOFactory.getDAO(className);
		rc = dao.dbRemove(primarykey);

		return rc;
	}


	/* CONSTRUCTORS	-----------------------------------------------	*/
	/**
	 *	Default constructor
	 */
	private Composer()	{ super();		}

	/**
	 *	Parameterized constructor.
	 * @param year	The year this driver won the Indy.
	 * @param driver	The winning driver for the year of the race.
	 * @param averageSpeed	The average speed the driver attained for the year of the race.
	 */
	private Composer(String name)		{
		this(new ComposerModel(name));
	}

	/**
	 *	Parameterized constructor.
	 *	@param	model	The persistence model for a Indywinner object.
	 */
	private Composer(ComposerModel model)	{
		setModel(model);

		/* TODO:	add any other initialization requirements here			*/

	}


	/* ACCESSORS	--------------------------------------------------	*/
	public ComposerModel getModel()			{ return model;												}
	public ComposerPK getPrimaryKey()			{ return getModel().getPrimarykey();					}
	public String getComposerName()						{ return getModel().getPrimarykey().getName(); 		}


	/* MODIFIERS	--------------------------------------------------	*/
	private void setModel(ComposerModel model)	{ this.model = model;								}

	private void setPrimarykey(ComposerPK pk)	{ getModel().setPrimarykey(pk);						}


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
		return	obj instanceof Composer
			&&	(getComposerName().equals(((Composer) obj).getComposerName()));
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
		return	getComposerName().hashCode();
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
		return "movement=" + getComposerName()
				+ sep + "movement name=" + getComposerName()
			;
	}

	/**
	 *	Remove a Indywinner from the data store (by primary key).
	 * @return
	 * @throws sql.NoSuchEntityException
	 * @throws sql.DAOSysException
	 */
	public Composer remove()	throws NoSuchEntityException, DAOSysException	{
		Composer c = null;
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
		ComposerDAO dao = null;
		try	{
			dao = (ComposerDAO) DAOFactory.getDAO(className);
			setModel((ComposerModel)dao.dbLoad(getPrimaryKey()));

		} catch (DAOSysException | NoSuchEntityException ex)	{
			throw new DAOSysException(ex.getMessage());
		}
	}


	/**
	 * Invoke this method to save the cached attribute values to the datastore.
	 */
	private void store()	throws DAOSysException		{
		ComposerDAO dao = null;
		try	{
			dao = (ComposerDAO) DAOFactory.getDAO(className);
			dao.dbStore(getModel());
		} catch (Exception ex)	{
			throw new DAOSysException(ex.getMessage());
		}
	}



	/* ATTRIBUTES	--------------------------------------------------	*/
	private static final boolean _debug = false;

	/** Class name for static method purposes.								*/
	private static String className = "symphony.Composer";

	private ComposerModel model;


	/* REFERENCE ATTRIBUTES	-----------------------------------------	*/


}	/*	End of CLASS:	Composer.java				*/