package gui.components;

import java.util.Vector;

import gui.helper.GridBagManager;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class AttachementPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private GridBagManager guiManager;
	private Vector<AttachementField> fields;
	private int y=0;
	
	
	public AttachementPanel() {
		this.guiManager = new GridBagManager(this);
		this.fields = new Vector<AttachementField>();
		setBorder(new TitledBorder("Anhang"));
	}
	
	public void addAttachement(String path){
		guiManager.setX(0).setY(y).setComp(new AttachementField(path));
		repaint();
		y++;
	}

}
