package symphony;



public class Composer {
	
	
	
	/* ACCESSORS	--------------------------------------------------	*/
	public ComposerModel getModel()				{ return model;												}
	public ComposerPK getPrimaryKey()			{ return getModel().getPrimarykey();					}
	
	/* ATTRIBUTES   --------------------------------------------------  */
	private ComposerModel model;

}
