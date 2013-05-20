package gui.components;

import gui.font.MessageFont;
import gui.helper.GridBagManager;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.SimpleFormatter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.html.HTMLEditorKit;

import org.jdesktop.swingx.JXTaskPane;

import message.Message;
import message.SMSMessage;
import table.model.MessageTableModel;

public class MessageBoxFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private String messageType;
	private GridBagManager guiManager;
	private JTextPane messageTextField;
	private JTable messagesTable;

	private JButton createButton;
	private JButton deleteButton;
	private JButton closeButton;
	private JButton seeMessagesButton;
	private ArrayList<SMSMessage> messages;
	private SimpleFormatter dateFormat;

	// TODO
	private JXTaskPane pane;

	public MessageBoxFrame(String messageType, ArrayList<SMSMessage> messages2) {
		this.messageType = messageType;
		this.guiManager = new GridBagManager(this);
		this.messageTextField = new JTextPane();
		this.messages = messages2;
		this.messagesTable = new JTable(new MessageTableModel(messages2));

		this.createButton = new JButton(this.messageType + " erstellen");
		this.deleteButton = new JButton(this.messageType + " löschen");
		this.closeButton = new JButton("Schliessen");
		this.seeMessagesButton = new JButton("Entwürfe anschauen");
		this.pane = new JXTaskPane();

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
				messageTextField.setText("<html><b>Von:</b> " + m.getFrom() + "<br><b>Betreff:</b>" + m.getSubject() + "<br><br><br>" + m.getMessage() + "</html>");
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
		guiManager.setX(6).setY(0).setWidth(6).setWeightX(20).setScrollPanel().setComp(messageTextField);

		guiManager.setX(0).setY(1).setWidth(3).setFill(GridBagConstraints.HORIZONTAL).setComp(createButton);
		guiManager.setX(3).setY(1).setWidth(3).setFill(GridBagConstraints.HORIZONTAL).setComp(deleteButton);
		guiManager.setX(6).setY(1).setWidth(3).setFill(GridBagConstraints.HORIZONTAL).setComp(seeMessagesButton);

		guiManager.setX(9).setY(1).setWidth(3).setFill(GridBagConstraints.HORIZONTAL).setComp(closeButton);

		setTitle(messageType + " erfassen");
		setSize(950, 650);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
