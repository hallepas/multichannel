package gui.components;

import gui.helper.GridBagManager;

import java.awt.Dimension;
import java.awt.GridBagConstraints;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import message.Attachment;


/**
 *Zeigt den Dateiname an. Diese kann auch gelöscht werden.
 *
 */
public class AttachmentField extends JPanel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Zeigt den Dateiname an
	 */
	private JLabel lbAttachement;
	
	/**
	 * Verwaltet das Panel
	 */
	private GridBagManager guiManager;
	
	/**
	 * Die ausgewählte Datei
	 */
	private Attachment attachment;
	
	/**
	 *Der Delete-Button welcher den Anhang entfernt 
	 */
	private JButton btDelete;

	/**
	 * Initialisiert den <code>AttachmentField</code>
	 * 
	 * @param attachment die ausgewählte Datei (<code>Attachment</code>) 
	 * @param btDelete der Delete-Button. Beim klicken wird der Attachment gelöscht
	 */
	public AttachmentField(Attachment attachment, JButton btDelete) {
		this.lbAttachement = new JLabel(attachment.getFileName());
		this.btDelete = btDelete;
		this.attachment = attachment;
		this.guiManager = new GridBagManager(this);
		configure();
	}

	/**
	 * Baut das Panel zusammen
	 */
	private void configure() {
		btDelete.setPreferredSize(new Dimension(20, 20));

		guiManager.setX(0).setY(0).setWeightX(4).setWidth(4).setFill(GridBagConstraints.HORIZONTAL).setComp(lbAttachement);
		guiManager.setX(4).setY(0).setWidth(1).setFill(GridBagConstraints.HORIZONTAL).setComp(btDelete);
	}

	/**
	 * Gibt die ausgewählte Datei (<code>Attachment</code>) zurück
	 * @return die ausgewählte Datei (<code>Attachment</code>) 
	 */
	public Attachment getAttachment() {
		return attachment;
	}
}
