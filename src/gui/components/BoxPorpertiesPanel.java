package gui.components;

import gui.helper.GridBagManager;

import java.awt.GridBagConstraints;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import message.MessageType;
import message.MessageWithSubjectAndAttachment;

import org.jdesktop.swingx.JXHyperlink;

import clients.MessageClient;

public class BoxPorpertiesPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GridBagManager guiManagerPropertiesPanel;

	public BoxPorpertiesPanel(MessageType messageType, MessageClient messageClient, JXHyperlink lbInbox, JXHyperlink lbEntwürfe, JButton createButton, JButton deleteButton, JButton printButton, JButton attachementButton) {
		this.guiManagerPropertiesPanel = new GridBagManager(this);

		createPropertiesPanel(messageType, messageClient, lbInbox, lbEntwürfe, createButton, deleteButton, printButton, attachementButton);
	}

	private void createPropertiesPanel(MessageType messageType, MessageClient messageClient, JXHyperlink lbInbox, JXHyperlink lbEntwürfe, JButton createButton, JButton deleteButton, JButton printButton, JButton attachementButton) {
		guiManagerPropertiesPanel.setX(0).setY(0).setComp(lbInbox);
		guiManagerPropertiesPanel.setX(0).setY(1).setComp(lbEntwürfe);
		guiManagerPropertiesPanel.setX(0).setY(2).setFill(GridBagConstraints.HORIZONTAL).setComp(createButton);
		guiManagerPropertiesPanel.setX(0).setY(3).setFill(GridBagConstraints.HORIZONTAL).setComp(deleteButton);

		if (messageClient.canPrint()) {
			guiManagerPropertiesPanel.setX(0).setY(4).setFill(GridBagConstraints.HORIZONTAL).setComp(printButton);
		}

		if (messageType.instance() instanceof MessageWithSubjectAndAttachment) {
			guiManagerPropertiesPanel.setX(0).setY(5).setFill(GridBagConstraints.HORIZONTAL).setComp(attachementButton);
		}

		guiManagerPropertiesPanel.setX(0).setY(6).setWeightY(20).setHeight(10).setComp(new JLabel());
	}
}
