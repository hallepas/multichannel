package gui.components;

import gui.font.MessageFont;
import gui.helper.GridBagManager;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.html.HTMLEditorKit;

import message.Message;
import message.MessageType;
import table.model.MessageTableModel;

public class MessagesTab extends JComponent{

	private static final long serialVersionUID = 1L;

	private MessageType messageType;
	private GridBagManager guiManager;
	private JTextPane messageTextField;
	private JTable messagesTable;

	private JButton createButton;
	private JButton deleteButton;
	private JButton printButton;
	private JButton seeMessagesButton;
	private String tabTitle;
	private List<Message> messages;
		
	public MessagesTab(List<Message> messages, MessageType messageType) {
		this.messages = messages;
		this.messageType = messageType;
		this.guiManager = new GridBagManager(this);
		this.messageTextField = new JTextPane();
		this.messagesTable = new JTable(new MessageTableModel(messages, messageType));

		this.createButton = new JButton(this.messageType.getTypeName() + " erstellen");
		this.deleteButton = new JButton(this.messageType.getTypeName() + " löschen");
		this.printButton = new JButton(this.messageType.getTypeName() +" drucken");
		this.seeMessagesButton = new JButton("Entwürfe anschauen");
		this.tabTitle = messageType.getTypeName();

		// Um den Text schön darzustellen (Fett, Kursiv, Abbruch etc.)
		HTMLEditorKit eKit = new HTMLEditorKit();
		messageTextField.setEditable(false);
		messageTextField.setEditorKit(eKit);

		configureFrame();
	}

	private void configureFrame() {

		messagesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				Message m = messages.get(messagesTable.getSelectedRow());
				//TODO überprüfen ob Betreff vorhanden ist
				messageTextField.setText("<html><b>Von:</b> " + m.getFrom() + "<br><b>Betreff:</b>"  + "<br><br><br>" + m.getMessage() + "</html>");
			}
		});

		createButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				MessageDialog mf = new MessageDialog(messageType);
				mf.setVisible(true);
			}
		});

		messageTextField.setFont(MessageFont.MESSAGE_FONT);

		guiManager.setX(0).setY(0).setWidth(6).setScrollPanel().setComp(messagesTable);
		guiManager.setX(6).setY(0).setWidth(6).setWeightX(10).setScrollPanel().setComp(messageTextField);

		guiManager.setX(0).setY(1).setWidth(3).setFill(GridBagConstraints.HORIZONTAL).setComp(createButton);
		guiManager.setX(3).setY(1).setWidth(3).setFill(GridBagConstraints.HORIZONTAL).setComp(deleteButton);
		guiManager.setX(6).setY(1).setWidth(3).setFill(GridBagConstraints.HORIZONTAL).setComp(seeMessagesButton);

		guiManager.setX(9).setY(1).setWidth(3).setFill(GridBagConstraints.HORIZONTAL).setComp(printButton);

	}

	public String getTabTitle(){
		return tabTitle;
	}
	
}
