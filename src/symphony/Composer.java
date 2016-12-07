package symphony;

import java.util.*;
import sql.*;

/**
 * Class representing a Composer.
 */
public class Composer		{
	/* CREATORS	-----------------------------------------------------	*/
	/**
	 *	Create an instance of a new Composer.
	 * @param name The name of the composer
	 *	@return	An instance of a Composer entity.
	 *	@throws sql.CreateException
	 */
	public static Composer create(String name)
													throws CreateException	{

		ComposerModel model = new ComposerModel(name);
		ComposerDAO dao = null;
		try	{
			dao = (ComposerDAO) DAOFactory.getDAO(className);
			dao.dbInsert(model);

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
	 *	Find a Composer by its primary key.
	 *	@return	An instance of an existing Composer entity.
	 *	@throws sql.FinderException
	 * @throws sql.NoSuchEntityException
	 * @param	primarykey	The primary key of Composer to find.
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

		} catch (DAOSysException | NoSuchEntityException sqlex)	{
			throw new FinderException(sqlex.getMessage());
		}

		return entity;
	}

	/**
	 *	Find all composers.
	 *	@return	A collection of composers.
	 *	@throws	FinderException
	 * @throws	CreateException
	 */
	public static ArrayList<Composer> findAll() throws FinderException, CreateException			{
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
	 *	Remove a composer by primary key.
	 *	@param	primarykey	The primary key for the composer to find.
	 *	@throws	ObjectNotFoundException
	 */
	private static int removeByPrimarykey(ComposerPK primarykey)
								throws	DAOSysException, NoSuchEntityException	{
		int rc = 0;
		ComposerDAO dao = null;
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
	 * @param name The name of the composer
	 */
	private Composer(String name)		{
		this(new ComposerModel(name));
	}

	/**
	 *	Parameterized constructor.
	 *	@param	model	The persistence model for a Composer object.
	 */
	private Composer(ComposerModel model)	{
		setModel(model);
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
	 *	Implementation of the "object" equals method.  Composer objects are equal
	 *	if their primary keys are equal.
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
		return "composer=" + getComposerName()
				+ sep + "composer name=" + getComposerName()
			;
	}

	/**
	 *	Remove a Composer from the data store (by primary key).
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