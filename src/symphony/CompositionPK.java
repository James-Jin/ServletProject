package symphony;


/**
 * CompositionPK is the primary key class for a Composition entity.
 */
public class CompositionPK implements java.io.Serializable	{
	/**
	 *	Default constructor.
	 */
	public CompositionPK()	{}

	/**
	 *	Constructor to build a primary key from a name.
	 *	@param	compositionName	The name of the composition.
	 */
	public CompositionPK(String compositionName)	{ this.compositionName = compositionName;		}

	/**
	 *	Constructor to build a primary key from a another CompositionPK argument.
	 *	@param	primarykey	A CompositionPK object.
	 */
	public CompositionPK(CompositionPK primarykey)	{ compositionName = primarykey.getCompositionName();		}


	/* ACCESSORS	--------------------------------------------------	*/
	/**
	 *	Get the composition name
	 *	@return	The composition name.
	 */
	public String getCompositionName()	{ return compositionName;		}


	/* BEHAVIOR	-----------------------------------------------------	*/
	/**
	 *	Convert this primary key object into a meaningful string.
	 *	@return	This object as a string.
	 */
	@Override
	public String toString()	{ return	compositionName;		}


	/**
	 *	Implementation of the "object" equals method.
	 *	@return	True if the fields of this primary key object equal the
	 *	contents of the fields from the passed primary key object, otherwise
	 *	false, they are not equal.
	 */
	@Override
	public boolean equals(Object obj)	{
		return	obj instanceof CompositionPK
			&&	getCompositionName().equals(((CompositionPK) obj).getCompositionName());
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
		return	getCompositionName().toString().hashCode();
	}



	/*	Composition Entity PRIMARY KEY FIELDS ------------------------------	*/
	/** compositionName.																	*/
	private String compositionName;

}	/*	End of Class:	CompositionPK.java				*/