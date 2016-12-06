/*
 *  @(#)ConductorPK.java
 */

package	symphony;

/**
 * ConductorPK is the primary key class for a Conductor entity.
 * @author    R. Dyer
 * @version   1.0.0 May 2002
 */
public class ComposerPK implements java.io.Serializable	{
	/**
	 *	Default constructor.
	 */
	public ComposerPK()	{}

	/**
	 *	Constructor to build a primary key from an Id.
	 *	@param	id	The customer id.
	 */
	public ComposerPK(String id)	{ this.id = id;		}

	/**
	 *	Constructor to build a primary key from a another ConductorPK argument.
	 *	@param	primarykey	A ConductorPK object.
	 */
	public ComposerPK(ComposerPK primarykey)	{ id = primarykey.getId();		}


	/* ACCESSORS	--------------------------------------------------	*/
	/**
	 *	Get the customer Id.
	 *	@return	The customer id.
	 */
	public String getId()	{ return id;		}


	/* BEHAVIOR	-----------------------------------------------------	*/
	/**
	 *	Convert this primary key object into a meaningful string.
	 *	@return	This object as a string.
	 */
	public String toString()	{ return	id;		}


	/**
	 *	Implemenation of the "object" equals method.
	 *	@return	True if the fields of this primary key object equal the
	 *	contents of the fields from the passed primary key object, otherwise
	 *	false, they are not equal.
	 */
	public boolean equals(Object obj)	{
		return	obj instanceof ComposerPK
			&&	getId().equals(((ComposerPK) obj).getId()
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
	public int hashCode() {
		return	getId().hashCode();
	}



	/*	Conductor Entity PRIMARY KEY FIELDS ------------------------------	*/
	/** Conductor id.																	*/
	private String id;

}	/*	End of Class:	ConductorPK.java				*/