package gui.listener.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import message.Attachment;
import message.Message;
import message.MessageWithSubjectAndAttachment;

/**
 * Listener für den Anhang
 *
 */
public class AttachementActionListener implements ActionListener {

	/**
	 * Die Messagetabelle
	 */
	private JTable table;
	
	/**
	 * Die Messages
	 */
	private List<Message> messages;

	/**
	 * Initialisiert den Listener
	 * @param Die Messagetabelle
	 * @param Die Messages
	 */
	public AttachementActionListener(JTable table, List<Message> messages) {
		this.table = table;
		this.messages = messages;
	}

	/**
	 * Aktion 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		int[] selectedRows = table.getSelectedRows();

		if (selectedRows.length != 1) {
			if (selectedRows.length == 0) {
				JOptionPane.showConfirmDialog(null, "Sie haben keine Nachricht ausgewählt. Bitte wählen Sie eine Nachricht aus", "Keine Nachricht ausgewählt", JOptionPane.PLAIN_MESSAGE);
				return;
			} else if (selectedRows.length > 1) {
				JOptionPane.showConfirmDialog(null, "Es kann nur der Anhang einer Nachricht gespeichert werden. Bitte wählen Sie eine Nachricht aus", "Mehrere Nachricht ausgewählt", JOptionPane.PLAIN_MESSAGE);
				return;
			}
		}

		List<Attachment> attachements = new ArrayList<Attachment>();

		for (int index : selectedRows) {
			int selectedRow = table.convertRowIndexToModel(index);
			MessageWithSubjectAndAttachment m = (MessageWithSubjectAndAttachment) messages.get(selectedRow);
			attachements.addAll(m.getAttachments());
		}
		

		if (attachements.size() == 0) {
			JOptionPane.showConfirmDialog(null, "Die ausgewählten Nachrichten haben keine Anhänge.", "Kein Anhang vorhanden", JOptionPane.PLAIN_MESSAGE);
			return;
		}

		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		// TODO: Auf Mac gehen nicht alle Ordner. 
		// Ist ein Bug in JAVA: http://stackoverflow.com/questions/3063812/jfilechooser-returns-incorrect-path-in-os-x-folders-only-mode
		if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fc.getSelectedFile();
			if (selectedFile.isDirectory()) {

				for (Attachment at : attachements) {
					try {
						at.saveAttachment(selectedFile.getPath());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

			}
		}
	}

	/**
	 * @param messages  Updatet die Liste
	 */
	public void updateMessages(List<Message> messages) {
		this.messages = messages;
	}

}