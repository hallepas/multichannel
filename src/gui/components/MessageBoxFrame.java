package gui.components;

import gui.font.MessageFont;
import gui.helper.GridBagManager;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextArea;

import table.model.MessageTableModel;

public class MessageBoxFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private String messageType;
	private GridBagManager guiManager;
	private JTextArea messageTextField;
	private JTable messagesTAble;
	
	private JButton createButton;
	private JButton deleteButton;
	private JButton closeButton;
	private JButton seeMessagesButton;

	public MessageBoxFrame(String messageType) {
		this.messageType = messageType;
		this.guiManager = new GridBagManager(this);
		this.messageTextField = new JTextArea();
		this.messagesTAble = new JTable(new MessageTableModel());
	
		this.createButton = new JButton(this.messageType+" erstellen");
		this.deleteButton = new JButton(this.messageType+" löschen");
		this.closeButton = new JButton("Schliessen");
		this.seeMessagesButton = new JButton("Entwürfe anschauen");
		
		configureFrame();
	}

	private void configureFrame() {

		createButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MessageDialog mf = new MessageDialog(messageType);
				mf.setVisible(true);
			}
		});
		
		messageTextField.setFont(MessageFont.MESSAGE_FONT);
		
		 guiManager.setX(0).setY(0).setWidth(6).setScrollPanel().setComp(messagesTAble);
		 guiManager.setX(6).setY(0).setWidth(6).setWeightX(20).setScrollPanel().setComp(messageTextField);
		 
		 guiManager.setX(0).setY(1).setWidth(3).setFill(GridBagConstraints.HORIZONTAL).setComp(createButton);
		 guiManager.setX(3).setY(1).setWidth(3).setFill(GridBagConstraints.HORIZONTAL).setComp(deleteButton);		
		 guiManager.setX(6).setY(1).setWidth(3).setFill(GridBagConstraints.HORIZONTAL).setComp(seeMessagesButton);

		 guiManager.setX(9).setY(1).setWidth(3).setFill(GridBagConstraints.HORIZONTAL).setComp(closeButton);
	
		
		 setTitle(messageType +" erfassen");
		 setSize(950, 650);
		 setLocationRelativeTo(null);
		 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
