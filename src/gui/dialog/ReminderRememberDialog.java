package gui.dialog;

import gui.helper.GridBagManager;
import gui.helper.MessageProperties;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import clients.MessageClient;

import message.EmailMessage;
import message.Message;
import message.MessageWithSubjectAndAttachment;

public class ReminderRememberDialog extends JDialog {

	private GridBagManager guiManager;
	private JButton sendButton;
	private JButton deleteReminderButton;
	private JButton bearbeitenButton;
	private JButton laterButton;
	private JLabel dateLb;
	private JLabel toLb;
	private JLabel subjectLb;
	private JLabel attachementLb;
	private Message message;
	private MessageClient messageClient;
	private Observable obs;

	public ReminderRememberDialog(Message message, MessageClient messageClient) {
		this.messageClient = messageClient;
		this.guiManager = new GridBagManager(this);
		this.dateLb = new JLabel("An: ");
		this.toLb = new JLabel("An: ");
		this.subjectLb = new JLabel("Betreff: ");
		this.attachementLb = new JLabel("Anhang:");
		this.sendButton = new JButton("Jetzt senden");
		this.deleteReminderButton = new JButton("Reminder löschen");
		this.bearbeitenButton = new JButton("Bearbeiten");
		this.laterButton = new JButton("Später bearbeiten");
		this.message = message;

		configureFrame();
	}
	
	public static void createDialog(Message message, MessageClient client) {
	    ReminderRememberDialog dialog = new ReminderRememberDialog(message, client);
	    dialog.setVisible(true);
	}

	private void prepareLabels() {
		dateLb.setText("<html><b>Erinnerung: </b>" + MessageProperties.DATE_AND_TIME_FORMATTER.format(message.getReminder()));
		toLb.setText("<html><b>" + toLb.getText() + "</b>" + getSeperatedTo(message.getTo()));

		if (message instanceof MessageWithSubjectAndAttachment) {
			subjectLb.setText("<html><b>" + subjectLb.getText() + "</b>" + ((MessageWithSubjectAndAttachment) message).getSubject());
			String attachementText = ((MessageWithSubjectAndAttachment) message).getAttachments().toString();
			attachementLb.setText("<html><b>" + attachementLb.getText() + "</b> " + (attachementText == "[]" ? "Keine" : attachementText));
		}
	}

	private void configureFrame() {
		sendButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO senden und mailbox refreshen
				dispose();
			}
		});

		bearbeitenButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO dieses fenster erst schliessen wenn md sauber
				// geschlossen ist (abbrechen: dieses fenster sollte erscheinen)
				MessageDialog md = new MessageDialog(message, message.getType(), messageClient, false);
				md.setVisible(true);
			}
		});

		laterButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				ReminderDialog rd = new ReminderDialog();
				rd.setVisible(true);
			}
		});

		deleteReminderButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				message.setReminder(null);
				//TODO mailbox refresh?
				dispose();
			}
		});

		prepareLabels();
		guiManager.setX(0).setY(0).setFill(GridBagConstraints.HORIZONTAL).setComp(dateLb);
		guiManager.setX(0).setY(1).setFill(GridBagConstraints.HORIZONTAL).setComp(new JLabel());
		guiManager.setX(0).setY(2).setFill(GridBagConstraints.HORIZONTAL).setComp(toLb);
		
		if (message instanceof MessageWithSubjectAndAttachment) {
			guiManager.setX(0).setY(3).setFill(GridBagConstraints.HORIZONTAL).setComp(subjectLb);
			guiManager.setX(0).setY(4).setFill(GridBagConstraints.HORIZONTAL).setComp(attachementLb);
		}
		
		guiManager.setX(0).setY(5).setFill(GridBagConstraints.HORIZONTAL).setComp(sendButton);
		guiManager.setX(1).setY(5).setFill(GridBagConstraints.HORIZONTAL).setComp(bearbeitenButton);
		guiManager.setX(0).setY(6).setFill(GridBagConstraints.HORIZONTAL).setComp(deleteReminderButton);
		guiManager.setX(1).setY(6).setFill(GridBagConstraints.HORIZONTAL).setComp(laterButton);

		setTitle("Reminder");

		if (message instanceof MessageWithSubjectAndAttachment) {
			setSize(350, 200);
		} else {
			setSize(350, 150);
		}
		setLocationRelativeTo(null);
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	private String getSeperatedTo(ArrayList<String> text) {
		// Letztes Semikolon entfernen
		String tempTos = "";
		if (text.size() == 0) {
			return "";
		}

		for (int i = 0; i < text.size(); i++) {
			tempTos += text.get(i) + "; ";
		}

		String tos = tempTos.substring(0, tempTos.length() - 2);

		return tos;
	}
}
