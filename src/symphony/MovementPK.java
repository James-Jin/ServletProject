package symphony;

/**
 * CompositionPK is the primary key class for a Composition entity.
 */
public class MovementPK implements java.io.Serializable	{
	/**
	 *	Default constructor.
	 */
	public MovementPK()	{}

	/**
	 *	Constructor to build a primary key from a movementNumber and movementName.
	 *	@param	compositionName	The name of the composition.
	 */
	public MovementPK(int movementNumber, String movementName)	{
		this.movementNumber = movementNumber;
		this.movementName = movementName;
		}

	/**
	 *	Constructor to build a primary key from a another MovementPK argument.
	 *	@param	primarykey	A MovementPK object.
	 */
	public MovementPK(MovementPK primarykey)	{ 
		movementNumber = primarykey.getMovementNumber();
		movementName = primarykey.getMovementName();
		}


	/* ACCESSORS	--------------------------------------------------	*/
	/**
	 *	Get the customer Number.
	 *	@return	The customer number.
	 */
	public int getMovementNumber()	{ return movementNumber;		}
	public String getMovementName()	{ return movementName;		}



	/* BEHAVIOR	-----------------------------------------------------	*/
	/**
	 *	Convert this primary key object into a meaningful string.
	 *	@return	This object as a string.
	 */
	@Override
	public String toString()	{ return	movementNumber + "   " + movementName;		}


	/**
	 *	Implementation of the "object" equals method.
	 *	@return	True if the fields of this primary key object equal the
	 *	contents of the fields from the passed primary key object, otherwise
	 *	false, they are not equal.
	 */
	@Override
	public boolean equals(Object obj)	{
		return	obj instanceof MovementPK
			&&	getMovementName().equals(((MovementPK) obj).getMovementName())
			&&  getMovementNumber() == ((MovementPK) obj).getMovementNumber();
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

	/*	Composition Entity PRIMARY KEY FIELDS ------------------------------	*/
	private int movementNumber;
	private String movementName;

}	/*	End of Class:	MovementPK.java				*/