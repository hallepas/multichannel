package gui.listener.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import message.Attachment;
import message.Message;
import message.MessageWithSubjectAndAttachment;

public class AttachementActionListener implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTable table;
	private List<Message> messages;

	public AttachementActionListener(JTable table, List<Message> messages) {
		this.table = table;
		this.messages = messages;
	}

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

		List<Attachment> attachement = ((MessageWithSubjectAndAttachment) messages.get(selectedRows[0])).getAttachments();
		if (attachement.size() == 0) {
			JOptionPane.showConfirmDialog(null, "Die ausgewählte Nachricht hat keinen Anhang.", "Kein Anhang vorhanden" , JOptionPane.PLAIN_MESSAGE);
			return;
		} 
		
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			if (fc.getSelectedFile().isDirectory()) {
				// TODO speichern
			}
		}
	}

}