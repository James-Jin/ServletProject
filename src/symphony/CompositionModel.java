package symphony;

import sql.CorePersistenceModel;

/**
 * CompositionModel represents the persistence model for a customer object.
 */
public class CompositionModel extends CorePersistenceModel<CompositionPK>	{
	/**
	 * Creates a default instance of CompositionModel
	 */
	public CompositionModel() { super();		}

	/**
	 * Creates a new instance of CompositionModel
	 * @param composer The composer of the composition
	 * @param compositionName The name of the composition
	 */
	public CompositionModel(String composer, String compositionName )	{
		this(new CompositionPK(compositionName), composer);
	}

	/**
	 * Creates a new instance of CompositionModel which represents all of the persistent domain
	 * data for this object type.
	 * @param primarykey	The primary key for this composition object.
	 * @param composer The composer of the composition
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
	/** Composer name.																	*/
	private String composer;											

}
