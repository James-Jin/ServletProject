package symphony;

import java.util.*;
import sql.*;

/**
 * Class representing a Composition.
 */
public class Composition		{
	/* CREATORS	-----------------------------------------------------	*/
	/**
	 *	Create an instance of a Composition.
	 * @param composer The composer of the composition
	 * @param compositionName The name of the composition
	 *	@return	An instance of a Composition entity.
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
	 *	Find a composition by its primary key.
	 *	@return	An instance of an existing composition entity.
	 *	@throws sql.FinderException
	 * @throws sql.NoSuchEntityException
	 * @param	primarykey	The primary key of the composition to find.
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

		} catch (DAOSysException | NoSuchEntityException sqlex)	{
			throw new FinderException(sqlex.getMessage());
		}

		return entity;
	}

	/**
	 *	Find all composition entities.
	 *	@return	A collection of composition instances.
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
	 *	Find composition entities based the composer's name and retrieve a limited number of entities.
	 *	@return	A collection of composition instances.
	 *	@throws	FinderException
	 * @throws	CreateException
	 */
	public static Collection<Composition> findByComposerName(String composerName) throws FinderException, CreateException			{
		ArrayList<Composition> listOfCompositions = new ArrayList<>();
		CompositionDAO dao = null;
		
		try	{
			dao = (CompositionDAO) DAOFactory.getDAO(className);
			Collection<CompositionPK> c = dao.dbSelectSetByComposerName(composerName);
			Iterator<CompositionPK> itr = c.iterator();
			while (itr.hasNext())	{
				CompositionPK cpk = itr.next();
				try	{
					Composition entity = Composition.findByPrimarykey(cpk);

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


	/* REMOVERS	-----------------------------------------------------	*/
	/**
	 *	Remove a composition by primary key.
	 *	@param	primarykey	The primary key for a composition to find.
	 *	@throws	ObjectNotFoundException
	 */
	private static int removeByPrimarykey(CompositionPK primarykey)
								throws	DAOSysException, NoSuchEntityException	{
		int rc = 0;
		CompositionDAO dao = null;
		
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
	 * @param composer The composer of the composition
	 * @param compositionName The name of the composition
	 */
	private Composition(String composer, String compositionName)		{
		this(new CompositionModel(composer, compositionName));
	}

	/**
	 *	Parameterized constructor.
	 *	@param	model	The persistence model for a Composition object.
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
	 *	Implementation of the "object" equals method.  Composition objects are equal
	 *	if their names are equal.
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
	 *	Remove a composition from the data store (by primary key).
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

	/** Persistence model for a composition object.							*/
	private CompositionModel model;


	/* REFERENCE ATTRIBUTES	-----------------------------------------	*/


}	/*	End of CLASS:	Composition.java				*/