/*
 *	DAOFactory.java
 *
 * Created on July 3, 2005, 10:54 AM
 */

package sql;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;

/**
 * Boat data access object factory.
 * @version   1.0.0 Aug 2002
 */
public class DAOFactory	{
	/**
	 *	Default constructor.
	 */
	private DAOFactory()	{}
	
	/**
	 * Instantiate a subclass that implements the abstract DAO interface.
	 * @param classname	Class to instantiate
	 * @return	A CoreDAO object.
	 * @throws sql.DAOSysException If DAO class cannot be instantiated.
	 */
	@SuppressWarnings("rawtypes")
	public static CoreDAO getDAO(String classname)	throws DAOSysException	{
		String daoclassname = classname + "DAO";
		CoreDAO dao = daos.get(daoclassname);

		try {
			if (dao == null)	{
				/*	Instantiate class, which will provide implementation
				 *	for the prescribed interface
				 */
				dao = (CoreDAO) Class.forName(daoclassname).newInstance();
				daos.put(daoclassname, dao);
			}

		}	catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex)	{
			ex.printStackTrace();
			throw new DAOSysException(
					"DAOFactory.getDAO() "
					+ " Error instantiating DAO object <" + daoclassname + "> "	+ ex.getMessage());
		}

		return dao;
	}

	/** This hash contains an class instance for each requested DAO accessible by
	 * the class name
	 */
	@SuppressWarnings("rawtypes")
	private static Map<String, CoreDAO> daos = 
		Collections.synchronizedMap(new Hashtable<String, CoreDAO>());
	
	}	/*	END OF CLASS:	DAOFactory.java				*/
