package gui.dialog;

import gui.helper.GridBagManager;
import gui.helper.MessageProperties;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import message.Message;
import message.MessageWithSubjectAndAttachment;
import clients.MessageClient;

/**
 * Dialog welcher dem User auf den Reminder erinnert
 *
 */
public class ReminderRememberDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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

	/**
	 * Initialisiert die Klasse
	 * @param message Die betroffene Nachricht
	 * @param messageClient Der MessageClient
	 */
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

		configure();
	}

	/**
	 * Statische Methode um diesen Dialog zu erstellen
	 * @param message
	 * @param client
	 */
	public static void createDialog(Message message, MessageClient client) {
		ReminderRememberDialog dialog = new ReminderRememberDialog(message, client);
		dialog.setVisible(true);
	}

	/**
	 * Bereit die Labels vor
	 */
	private void prepareLabels() {
		dateLb.setText("<html><b>Erinnerung: </b>" + MessageProperties.DATE_AND_TIME_FORMATTER.format(message.getReminder()));
		toLb.setText("<html><b>" + toLb.getText() + "</b>" + getSeperatedTo(message.getTo()));

		if (message instanceof MessageWithSubjectAndAttachment) {
			subjectLb.setText("<html><b>" + subjectLb.getText() + "</b>" + ((MessageWithSubjectAndAttachment) message).getSubject());
			String attachementText = ((MessageWithSubjectAndAttachment) message).getAttachments().toString();
			attachementLb.setText("<html><b>" + attachementLb.getText() + "</b> " + (attachementText == "[]" ? "Keine" : attachementText));
		}
	}

	/**
	 * Baut den Dialog auf
	 */
	private void configure() {
		sendButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				messageClient.submit(message);
				dispose();
			}
		});

		bearbeitenButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				message.setReminder(null);
				MessageDialog md = new MessageDialog(message, message.getType(), messageClient, false);
				md.setVisible(true);
				dispose();
			}
		});

		laterButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				message.setReminder(null);
				ReminderDialog rd = new ReminderDialog(message);
				rd.setVisible(true);
				dispose();
			}
		});

		deleteReminderButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				message.setReminder(null);
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

	/**
	 * 
	 * @param list Die Liste aus welchen man einen schönen Text erstellt
	 * @return Gibt einen schönen Text zurück
	 */
	private String getSeperatedTo(ArrayList<String> list) {
		// Letztes Semikolon entfernen
		String tempTos = "";
		if (list.size() == 0) {
			return "";
		}

		for (int i = 0; i < list.size(); i++) {
			tempTos += list.get(i) + "; ";
		}

		String tos = tempTos.substring(0, tempTos.length() - 2);

		return tos;
	}
}
