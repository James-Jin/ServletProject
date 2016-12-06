package symphony;



public class Composer {
	
	public Composer(ComposerModel model){
		setModel(model);
	}
	
	/* MODIFIERS --------------------------------------------------	*/
	public void setModel(ComposerModel model){
		this.model = model;
	}
	
	/* ACCESSORS	--------------------------------------------------	*/
	public ComposerModel getModel()				{ return model;												}
	public ComposerPK getPrimaryKey()			{ return getModel().getPrimarykey();					}
	
	/* ATTRIBUTES   --------------------------------------------------  */
	private ComposerModel model;

}
