package gui.components;

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

	public AttachementField(String path) {
		this.lbAttachement = new JLabel(path);
		this.btDelete = new JButton("X");
		this.guiManager = new GridBagManager(this);
		configure();
	}

	private void configure() {
		btDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				guiManager.removeAll();
			}
		});
		
		
		guiManager.setX(0).setY(0).setWidth(1).setFill(GridBagConstraints.HORIZONTAL).setComp(lbAttachement);
		guiManager.setX(1).setY(0).setWidth(1).setFill(GridBagConstraints.HORIZONTAL).setComp(btDelete);
	}
	
	public String getPath(){
		return lbAttachement.getText();
	}

}
