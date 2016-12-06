/*
 *  @(#)IndywinnerPK.java
 *
 *  (c) Copyright 2001, 2002 by Dyer Consulting
 *      Quebec, Canada
 *      All rights reserved.
 *
 *  This software contains confidential and proprietary information
 *  of Dyer Consulting ("Confidential Information").  You shall not disclose
 *  such Confidential Information and shall use it only in accordance with the
 *  terms of the license agreement you entered into with Dyer Consulting.
 *
 *  This software is provided "AS IS,".  No warrantee of any kind, express
 *  or implied, is included with this software; use at your own risk, responsibility
 *  for damages (if any) to anyone resulting from the use of this software rests
 *  entirely with the user even if Dyer Consulting has been advised of the
 *  possibility of such damages.
 *
 *  This software is not designed or intended for use in on-line control of
 *  aircraft, air traffic, aircraft navigation or aircraft communications; or in
 *  the design, construction, operation or maintenance of any nuclear
 *  facility. Licensee represents and warrants that it will not use or
 *  redistribute the Software for such purposes.
 *
 *  Distribute freely, except: don't remove my name from the source or
 *  documentation, mark your changes (don't blame me for your possible bugs),
 *  don't alter or remove any of this notice.
 */

package symphony;


/**
 * CompositionPK is the primary key class for a Composition entity.
 * @author    R. Dyer
 * @version   1.0.0
 */
public class MovementPK implements java.io.Serializable	{
	/**
	 *	Default constructor.
	 */
	public MovementPK()	{}

	/**
	 *	Constructor to build a primary key from an Number.
	 *	@param	compositionName	The name of the composition.
	 */
	public MovementPK(int movementNumber, String movementName)	{
		this.movementNumber = movementNumber;
		this.movementName = movementName;
		}

	/**
	 *	Constructor to build a primary key from a another IndywinnerPK argument.
	 *	@param	primarykey	A IndywinnerPK object.
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
	/** compositionName.
	 * 																	*/
	private int movementNumber;
	private String movementName;

}	/*	End of Class:	IndywinnerPK.java				*/