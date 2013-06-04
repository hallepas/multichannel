package gui.components;

import gui.helper.GridBagManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import message.Attachment;

/**
 * Beinhaltet alle AttachmentFields
 * 
 */
public class AttachmentPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Verwaltet das Panel
	 */
	private GridBagManager guiManager;

	/**
	 * Enthält alle Anhänge
	 */
	private ArrayList<Attachment> attachments;

	/**
	 * Die y-Koordinate welche das nächste AttachmentField platziert wrid
	 */
	private int y = 0;

	/**
	 * Initialisiert das AttachmentPanel
	 */
	public AttachmentPanel() {
		this.guiManager = new GridBagManager(this);
		this.attachments = new ArrayList<Attachment>();
		setBorder(new TitledBorder("Anhang"));
	}

	// TODO muss refresht werden
	/**
	 * Fügt es dem Panel hinzu. Zusätzlich wird ein Delete-Button angehängt
	 * welcher beim klicken die Datei wieder entfernt
	 * 
	 * @param attachment
	 *            Die ausgewählte Datei
	 */
	public void addAttachement(Attachment attachment) {
		JButton btDelete = new JButton("X");
		final AttachmentField attachmentField = new AttachmentField(attachment, btDelete);

		btDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guiManager.remove(attachmentField);
				attachments.remove(attachmentField.getAttachment());
				repaint();
			}
		});

		attachments.add(attachment);
		guiManager.setX(0).setY(y).setComp(attachmentField);
		repaint();
		y++;
	}

	/**
	 * Gibt alle ausgewählte Dateien zurück
	 * @return Gibt alle ausgewählte Dateien zurück
	 */
	public ArrayList<Attachment> getAttachments() {
		return attachments;
	}

}
