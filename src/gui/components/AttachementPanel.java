package gui.components;

import gui.helper.GridBagManager;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AttachementPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private GridBagManager guiManager;
	
	
	public AttachementPanel(String path) {
		this.guiManager = new GridBagManager(this);
	}
	
	public void addAttachement(String path){
	}

}
