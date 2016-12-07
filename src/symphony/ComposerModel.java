package symphony;

import sql.CorePersistenceModel;

/**
 * ConductorModel represents the persistence model for a conductor object.
 * 
 */
public class ComposerModel extends CorePersistenceModel<ComposerPK>	{
	/**
	 * Creates a new instance of ConductorModel
	 */
	public ComposerModel() { super();		}
	
	/**
	 * Creates a new instance of ConductorModel
	 * 
	 * @param name The name of the composer
	 */
	public ComposerModel(String name) {
		this(new ComposerPK(name));
	}

	/**
	 * Creates a new instance of ConductorModel
	 * 
	 * @param primarykey The primary key for a composer
	 */
	public ComposerModel(ComposerPK primarykey) {
		super();
		setPrimarykey(primarykey);
	}
	
	
	/* ACCESSORS	--------------------------------------------------	*/
 	public String getName()					{ return getPrimarykey().getName(); }

}
