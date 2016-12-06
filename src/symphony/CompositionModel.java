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
public class CompositionModel extends CorePersistenceModel<CompositionPK>	{
	/**
	 * Creates a default instance of IndywinnerModel
	 */
	public CompositionModel() { super();		}

	/**
	 * Creates a new instance of IndywinnerModel with appropriate initial state.
	 * @param year	The year this driver won the Indy.
	 * @param driver	The winning driver for the year of the race.
	 * @param averageSpeed	The average speed the driver attained for the year of the race.
	 */
	public CompositionModel(String composer, String compositionName )	{
		this(new CompositionPK(compositionName), composer);
	}

	/**
	 * Creates a new instance of IndywinnerModel which represents all of the persistent domain
	 * data for this object type.
	 * @param primarykey	The primary key for this indy winner object.
	 * @param driver	The winning driver for the year of the race.
	 * @param averageSpeed	The average speed the driver attained for the year of the race.
	 */
	public CompositionModel(CompositionPK primarykey,
								String composer )	{
		super(primarykey);
		this.composer = composer;
	}


	/* ACCESSORS	--------------------------------------------------	*/
	public String  getCompostionName()			{ return getPrimarykey().getCompositionName();		}
	public String getComposer()			{ return composer;								}

	
	/* MODIFIERS	--------------------------------------------------	*/
	public void setComposer(String composer)					{ this.composer = composer;					}


	/* ATTRIBUTES	--------------------------------------------------	*/
	/** Drivers name.																	*/
	private String composer;
	/** Average speed during race.												*/

}
