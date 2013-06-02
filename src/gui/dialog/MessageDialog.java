package gui.dialog;

import gui.components.AttachementPanel;
import gui.components.ReminderPanel;
import gui.font.MessageFont;
import gui.helper.GridBagManager;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import message.Attachment;
import message.Message;
import message.MessageType;
import message.MessageWithSubjectAndAttachment;
import message.Status;
import clients.MessageClient;
import clients.useragents.UserAgent;

public class MessageDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private GridBagManager guiManager;

	private MessageType messageType;

	private JTextField toField;
	private JTextField subjectField;
	// private JTextField attachementField;

	private JButton searchButton;
	private JButton cancelButton;
	private JButton sendButton;
	private JButton saveButton;
	private JButton reminderButton;

	private JTextArea messageTextField;
	private File[] attachementFiles;
	private Message message;
	private MessageClient messageClient;
	private boolean draft;
	private ReminderPanel reminderPanel;
	private AttachementPanel attachmentPanel;

	public MessageDialog(Message message, MessageType messageType, MessageClient messageClient, boolean draft) {
		this.messageClient = messageClient;
		this.attachmentPanel = new AttachementPanel();
		this.guiManager = new GridBagManager(this);
		this.messageType = messageType;
		this.toField = new JTextField();
		this.subjectField = new JTextField();
		// this.attachementField = new JTextField();
		this.searchButton = new JButton("...");
		this.cancelButton = new JButton("Abbrechen");
		this.sendButton = new JButton("Senden");
		this.saveButton = new JButton("Speichern");
		this.reminderButton = new JButton("Reminder erstellen");
		this.messageTextField = new JTextArea();
		this.message = message;
		this.draft = draft;
		this.reminderPanel = new ReminderPanel(message);
		fillComponentsWithMessageProperties();
		configureFrame();
	}

	public void fillComponentsWithMessageProperties() {
		String toListe = "";

		if (message.getTo() != null) {
			for (String s : message.getTo()) {
				toListe = toListe + s + "; ";
			}

			if (toListe.length() > 1) {
				toListe = toListe.substring(0, toListe.length() - 2);
			}
		}

		toField.setText(toListe);
		messageTextField.setText(message.getMessage());

		if (message instanceof MessageWithSubjectAndAttachment) {
			subjectField.setText(((MessageWithSubjectAndAttachment) message).getSubject());

			for (Attachment file : ((MessageWithSubjectAndAttachment) message).getAttachments()) {
				attachmentPanel.addAttachement(file);
			}

		}
	}

	public void fillAttachmentPanel() {

	}

	private void configureFrame() {

		reminderButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ReminderDialog rd = new ReminderDialog(message);
				rd.setVisible(true);

				Date reminder = message.getReminder();
				if (reminder != null) {
					reminderButton.setText("Reminder entfernen");
				} else {
					reminderButton.setText("Reminder erstellen");
				}

				reminderPanel.reminderUpdate(reminder);
			}
		});

		searchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				fc.setMultiSelectionEnabled(true);

				if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					attachementFiles = fc.getSelectedFiles();

					for (File file : attachementFiles) {
						if (file.isDirectory()) {
							JOptionPane.showConfirmDialog(null, "Sie haben einen Ordner ausgewählt, sie müssen eine Datei auswählen", "Ordner nicht erlaubt", JOptionPane.PLAIN_MESSAGE);
							continue;
						}

						Attachment at;

						try {
							at = new Attachment(file.getPath());
							attachmentPanel.addAttachement(at);
						} catch (IOException e1) {
							JOptionPane.showConfirmDialog(null, "Datei fehlerhaft", "Anhang", JOptionPane.PLAIN_MESSAGE);
							e1.printStackTrace();
						}
					}
					// TODO
					// for (File file : attachementFiles) {
					// attachementField.setText(attachementField.getText() +
					// file.getPath() + ";");
					// }

				}

			}
		});

		sendButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				message = buildMessage();
				messageClient.submit(message);
				dispose();
			}
		});

		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				message = buildMessage();
				if (draft == false) {
					messageClient.saveDraft(message);
				}
				dispose();
			}
		});

		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		messageTextField.setSize(100, 100);
		messageTextField.setFont(MessageFont.MESSAGE_FONT);

		guiManager.setX(0).setY(0).setWidth(1).setWeightX(1).setComp(new JLabel("An"));

		if (messageType.instance() instanceof MessageWithSubjectAndAttachment) {
			guiManager.setX(1).setY(0).setWidth(6).setWeightX(6).setFill(GridBagConstraints.HORIZONTAL).setComp(toField);
			guiManager.setX(7).setY(0).setWidth(1).setWeightX(1).setHeight(2).setComp(reminderPanel);
			guiManager.setX(0).setY(1).setWidth(1).setWeightX(1).setComp(new JLabel("Betreff"));
			guiManager.setX(1).setY(1).setWidth(6).setWeightX(6).setFill(GridBagConstraints.HORIZONTAL).setComp(subjectField);

			guiManager.setX(0).setY(2).setWidth(1).setWeightX(1).setComp(new JLabel("Anhang"));
			guiManager.setX(1).setY(2).setWidth(6).setWeightX(8).setFill(GridBagConstraints.HORIZONTAL).setComp(attachmentPanel);
			guiManager.setX(7).setY(2).setWeightX(1).setHeight(1).setFill(GridBagConstraints.HORIZONTAL).setComp(searchButton);
		} else {

			guiManager.setX(1).setY(0).setWidth(6).setWeightX(6).setFill(GridBagConstraints.HORIZONTAL).setComp(toField);
			guiManager.setX(7).setY(0).setWidth(1).setWeightX(1).setComp(reminderPanel);
		}

		guiManager.setX(0).setY(3).setWidth(8).setWeightX(8).setWeightY(25).setHeight(5).setScrollPanel().setComp(messageTextField);

		guiManager.setX(0).setY(8).setWidth(2).setWeightX(2).setFill(GridBagConstraints.HORIZONTAL).setComp(cancelButton);
		guiManager.setX(2).setY(8).setWidth(2).setWeightX(2).setFill(GridBagConstraints.HORIZONTAL).setComp(sendButton);
		guiManager.setX(4).setY(8).setWidth(2).setWeightX(2).setFill(GridBagConstraints.HORIZONTAL).setComp(saveButton);
		guiManager.setX(6).setY(8).setWidth(2).setWeightX(2).setFill(GridBagConstraints.HORIZONTAL).setComp(reminderButton);

		setTitle(messageType.getTypeName() + " erfassen");
		setSize(700, 500);
		setLocationRelativeTo(null);
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	private Message buildMessage() {
		message.setMessage(messageTextField.getText());
		message.setTo(getSeperatedList(toField.getText()));

		if (messageType.instance() instanceof MessageWithSubjectAndAttachment) {
			((MessageWithSubjectAndAttachment) message).setSubject(subjectField.getText());
			((MessageWithSubjectAndAttachment) message).fillAttachements(attachmentPanel.getAttachments());
		}

		return message;
	}

	private ArrayList<String> getSeperatedList(String listText) {

		if (listText == null || listText.length() < 1) {
			return null;
		}

		// Letztes Semikolon entfernen
		String text = listText;

		if (listText.endsWith(";")) {
			text = listText.substring(0, listText.length() - 1);
		}

		ArrayList<String> tos = new ArrayList<String>();
		String[] tolist = text.split(";");

		for (String s : tolist) {
			s=s.trim();
			tos.add(s);
		}

		return tos;
	}
}
