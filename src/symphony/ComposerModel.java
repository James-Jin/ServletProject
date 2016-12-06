/*
 * ConductorModel.java
 */

package symphony;

import java.util.List;

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
	 * @param name
	 */
	public ComposerModel(String name) {
		this(new ComposerPK(name));
	}

	/**
	 * Creates a new instance of ConductorModel
	 * 
	 * @param primarykey
	 * @param name
	 */
	public ComposerModel(ComposerPK primarykey) {
		super();
		setPrimarykey(primarykey);
	}
	
	
	/* ACCESSORS	--------------------------------------------------	*/
 	public String getName()					{ return getPrimarykey().getName(); }

}
