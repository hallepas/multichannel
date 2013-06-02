package gui.dialog;

import gui.helper.GridBagManager;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

import org.jdesktop.swingx.JXDatePicker;

public class ReminderDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private GridBagManager guiManager;
	private JButton okButton;
	private JFormattedTextField  timeField;

	private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
	private JXDatePicker datePicker;

	public ReminderDialog() {
		this.guiManager = new GridBagManager(this);
		this.okButton = new JButton("Ok");
		this.timeField = new JFormattedTextField(timeFormat);
		this.datePicker = new JXDatePicker();
		configure();
	}

	private void configure() {
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//TODO
			}
		});

		//Aktuelle Datum ins Feld setzen
		datePicker.setDate(new Date(System.currentTimeMillis()));
		//Aktuelle Zeit ins Feld setzen
		timeField.setValue(new Date(System.currentTimeMillis()));
		
		guiManager.setX(0).setY(0).setWidth(1).setFill(GridBagConstraints.HORIZONTAL).setComp(new JLabel("Datum"));
		guiManager.setX(1).setY(0).setWidth(1).setFill(GridBagConstraints.HORIZONTAL).setComp(datePicker);

		guiManager.setX(0).setY(1).setWidth(1).setFill(GridBagConstraints.HORIZONTAL).setComp(new JLabel("Zeit"));
		guiManager.setX(1).setY(1).setWidth(1).setFill(GridBagConstraints.HORIZONTAL).setComp(timeField);
		guiManager.setX(0).setY(2).setWidth(2).setFill(GridBagConstraints.HORIZONTAL).setComp(okButton);

		setTitle("Reminder erstellen");
		pack();
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setModal(true);
	}

}
