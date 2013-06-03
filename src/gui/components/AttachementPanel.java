package gui.components;

import gui.helper.GridBagManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import message.Attachment;

public class AttachementPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private GridBagManager guiManager;
	private ArrayList<Attachment> attachments;
	private int y = 0;

	public AttachementPanel() {
		this.guiManager = new GridBagManager(this);
		this.attachments = new ArrayList<Attachment>();
		setBorder(new TitledBorder("Anhang"));
	}

	// TODO muss refresht werden
	public void addAttachement(Attachment attachment) {
		JButton btDelete = new JButton("X");
		final AttachementField attachmentField = new AttachementField(attachment, btDelete);

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

	public ArrayList<Attachment> getAttachments() {
		return attachments;
	}

}
