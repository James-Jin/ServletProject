/*
 * IndywinnerModel.java
 *
 * Created on July 2, 2005, 2:13 PM
 */

package symphony;

import sql.CorePersistenceModel;

/**
 * IndywinnerModel represents the persistence model for a customer object.
 * @author Reg
 */
public class MovementModel extends CorePersistenceModel<MovementPK>	{
	/**
	 * Creates a default instance of IndywinnerModel
	 */
	public MovementModel() { super();		}

	/**
	 * Creates a new instance of IndywinnerModel with appropriate initial state.
	 * @param year	The year this driver won the Indy.
	 * @param driver	The winning driver for the year of the race.
	 * @param averageSpeed	The average speed the driver attained for the year of the race.
	 */
	public MovementModel(int movementNumber, String movementName )	{
		this(new MovementPK(movementNumber, movementName));
	}

	/**
	 * Creates a new instance of IndywinnerModel which represents all of the persistent domain
	 * data for this object type.
	 * @param primarykey	The primary key for this indy winner object.
	 * @param driver	The winning driver for the year of the race.
	 * @param averageSpeed	The average speed the driver attained for the year of the race.
	 */
	public MovementModel(MovementPK primarykey )	{
		super(primarykey);
	}


	/* ACCESSORS	--------------------------------------------------	*/
	public String  getMovementName()			{ return getPrimarykey().getMovementName();		}
	public int getMovementNumber()			{ return getPrimarykey().getMovementNumber();								}

	


}
