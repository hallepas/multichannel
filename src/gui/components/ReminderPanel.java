package gui.components;

import gui.helper.GridBagManager;
import gui.helper.MessageProperties;

import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import message.Message;

/**
 * Zeigt die Daten des Reminders an
 * 
 * @author Milijana
 * 
 */
public class ReminderPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	/**
	 * Verwaltet das Panel
	 */
	private GridBagManager guiManager;

	/**
	 * Zeittitel
	 */
	private JLabel timeLb;

	/**
	 * Datetitel
	 */
	private JLabel dateLb;

	/**
	 * Reminderlogo
	 */
	private JLabel imgLb;

	/**
	 * Die ausgewählte Nachricht
	 */
	private Message message;

	/**
	 * Die ausgewählte Nachricht
	 * 
	 * @param message
	 *            Die ausgewählte Nachricht
	 */
	public ReminderPanel(Message message) {
		this.message = message;
		this.guiManager = new GridBagManager(this);
		this.timeLb = new JLabel();
		this.dateLb = new JLabel();
		// this.imgLb = new JLabel(new ImageIcon("data/images/clock2.png"));
		this.imgLb = new JLabel(new ImageIcon("data/images/reminder_clock3.png"));
		configure();
	}

	/**
	 * 
	 * @param date
	 *            Erneuert den Reminder mit <code>date</code>
	 */
	public void reminderUpdate(Date date) {
		if (date != null) {
			this.timeLb.setText("<html><b>Zeit:</b> " + MessageProperties.TIME_FORMATTER.format(date) + "</html");
			this.dateLb.setText("<html><b>Datum:</b> " + MessageProperties.DATE_FORMATTER.format(date) + "</html");
		} else {
			this.timeLb.setText("<html><b>Zeit:</b> -</html");
			this.dateLb.setText("<html><b>Datum:</b>-</html");
		}
	}

	/**
	 * Baut das Panel zusammen
	 */
	private void configure() {
		reminderUpdate(message.getReminder());
		guiManager.setX(0).setY(0).setHeight(3).setComp(imgLb);
		guiManager.setX(1).setY(0).setComp(dateLb);
		guiManager.setX(1).setY(1).setComp(timeLb);
		guiManager.setX(1).setY(2).setHeight(2).setComp(new JLabel(""));
	}

}
