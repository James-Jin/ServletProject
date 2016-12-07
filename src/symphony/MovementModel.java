package symphony;

import sql.CorePersistenceModel;

/**
 * MovementModel represents the persistence model for a customer object.
 */
public class MovementModel extends CorePersistenceModel<MovementPK>	{
	/**
	 * Creates a default instance of MovementModel
	 */
	public MovementModel() { super();		}

	/**
	 * Creates a new instance of MovementModel with appropriate initial state.
	 *  @param movementNumber represents the position of a movement in the composition 
	 *  @param name The name of the movement
	 */
	public MovementModel(int movementNumber, String movementName )	{
		this(new MovementPK(movementNumber, movementName));
	}

	/**
	 * Creates a new instance of MovementModel which represents all of the persistent domain
	 * data for this object type.
	 *  @param movementNumber represents the position of a movement in the composition 
	 *  @param name The name of the movement
	 */
	public MovementModel(MovementPK primarykey )	{
		super(primarykey);
	}


	/* ACCESSORS	--------------------------------------------------	*/
	public String  getMovementName()			{ return getPrimarykey().getMovementName();		}
	public int getMovementNumber()			{ return getPrimarykey().getMovementNumber();								}

	
}
