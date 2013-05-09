package gui.components;

import gui.font.MessageFont;
import gui.helper.GridBagManager;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MessageDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private GridBagManager guiManager;

	private String messageType;

	private JTextField toField;
	private JTextField subjectField;
	private JTextField attachementField;

	private JButton searchButton;
	private JButton cancelButton;
	private JButton sendButton;
	private JButton saveButton;
	private JButton reminderButton;

	private JTextArea messageTextField;

	public MessageDialog(String messageType) {
		this.guiManager = new GridBagManager(this);
		this.messageType = messageType;
		this.toField = new JTextField();
		this.subjectField = new JTextField();
		this.attachementField = new JTextField();
		this.searchButton = new JButton("...");
		this.cancelButton = new JButton("Abbrechen");
		this.sendButton = new JButton("Senden");
		this.saveButton = new JButton("Speichern");
		this.reminderButton = new JButton("Reminder erstellen");
		this.messageTextField = new JTextArea();
		configureFrame();
	}

	private void configureFrame() {

		reminderButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ReminderDialog rd = new ReminderDialog();
				rd.setVisible(true);
			}
		});

		searchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				
				if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
					attachementField.setText(attachementField.getText()+fc.getSelectedFile()+";");
				}


			}
		});

		messageTextField.setSize(100, 100);
		messageTextField.setFont(MessageFont.MESSAGE_FONT);
		

		guiManager.setX(0).setY(0).setWidth(1).setWeightX(1).setComp(new JLabel("An"));
		guiManager.setX(1).setY(0).setWidth(7).setWeightX(7).setFill(GridBagConstraints.HORIZONTAL).setComp(toField);

		guiManager.setX(0).setY(1).setWidth(1).setWeightX(1).setComp(new JLabel("Betreff"));
		guiManager.setX(1).setY(1).setWidth(7).setWeightX(7).setFill(GridBagConstraints.HORIZONTAL).setComp(subjectField);

		guiManager.setX(0).setY(2).setWidth(1).setWeightX(1).setComp(new JLabel("Anhang"));
		guiManager.setX(1).setY(2).setWidth(6).setWeightX(8).setFill(GridBagConstraints.HORIZONTAL).setComp(attachementField);
		guiManager.setX(7).setY(2).setWeightX(1).setHeight(1).setFill(GridBagConstraints.HORIZONTAL).setComp(searchButton);

		guiManager.setX(0).setY(3).setWidth(8).setWeightX(8).setWeightY(25).setHeight(5).setScrollPanel().setComp(messageTextField);

		guiManager.setX(0).setY(8).setWidth(2).setWeightX(2).setFill(GridBagConstraints.HORIZONTAL).setComp(cancelButton);
		guiManager.setX(2).setY(8).setWidth(2).setWeightX(2).setFill(GridBagConstraints.HORIZONTAL).setComp(sendButton);
		guiManager.setX(4).setY(8).setWidth(2).setWeightX(2).setFill(GridBagConstraints.HORIZONTAL).setComp(saveButton);
		guiManager.setX(6).setY(8).setWidth(2).setWeightX(2).setFill(GridBagConstraints.HORIZONTAL).setComp(reminderButton);

		setTitle(messageType + " erfassen");
		setSize(700, 500);
		setLocationRelativeTo(null);
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

}
