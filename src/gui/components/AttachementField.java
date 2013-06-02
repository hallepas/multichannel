package gui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.helper.GridBagManager;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

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

		guiManager.setX(0).setY(0).setWidth(6).setComp(lbAttachement);
		guiManager.setX(6).setY(0).setWidth(1).setComp(btDelete);
	}
	
	public String getPath(){
		return lbAttachement.getText();
	}

}
