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
	public String getId()				{ return getPrimarykey().getName();	}
 	public String getName()					{ return name; }
 	public List<Composition> getCompositions() { return compositions; }


	/* MODIFIERS	--------------------------------------------------	*/
	public void setName(String name)				{ this.name = name;	}
	public void setCompositions(List<Composition> compositions) { this.compositions = compositions; }
	
	/* ATTRIBUTES	--------------------------------------------------	*/
															
 	private String name;
 	private List<Composition> compositions;
														

}
