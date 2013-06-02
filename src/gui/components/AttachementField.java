package gui.components;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.helper.GridBagManager;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class AttachementField extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel lbAttachement;
	private GridBagManager guiManager;
	private JButton btDelete;

	public AttachementField(String path, JButton btDelete) {
		this.lbAttachement = new JLabel(path);
		this.btDelete = btDelete;
		this.guiManager = new GridBagManager(this);
		configure();
	}

	private void configure() {
		btDelete.setPreferredSize(new Dimension(20, 20));
		
		guiManager.setX(0).setY(0).setWeightX(4).setWidth(4).setFill(GridBagConstraints.HORIZONTAL).setComp(lbAttachement);
		guiManager.setX(4).setY(0).setWidth(1).setFill(GridBagConstraints.HORIZONTAL).setComp(btDelete);
	}
	
	public String getPath(){
		return lbAttachement.getText();
	}

}
