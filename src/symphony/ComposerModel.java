/*
 * ConductorModel.java
 */

package symphony;

import sql.CorePersistenceModel;

/**
 * ConductorModel represents the persistence model for a conductor object.
 * @author Reg
 */
public class ComposerModel extends CorePersistenceModel<ComposerPK>	{
	/**
	 * Creates a new instance of ConductorModel
	 */
	public ComposerModel() { super();		}
	
	/**
	 * Creates a new instance of ConductorModel
	 * 
	 * @param id
	 * @param name
	 */
	public ComposerModel(String id,
								String name) {
		this(new ComposerPK(id), name);
	}

	/**
	 * Creates a new instance of ConductorModel
	 * 
	 * @param primarykey
	 * @param name
	 */
	public ComposerModel(ComposerPK primarykey,
								String name) {
		super();
		setPrimarykey(primarykey);
		setName(name);
	}
	
	
	/* ACCESSORS	--------------------------------------------------	*/
	public String getId()				{ return getPrimarykey().getId();	}
 	public String getName()					{ return name;									}


	/* MODIFIERS	--------------------------------------------------	*/
	public void setName(String name)				{ this.name = name;					}

	
	/* ATTRIBUTES	--------------------------------------------------	*/
	/** Name of this customer.														*/
 	private String name;

}
