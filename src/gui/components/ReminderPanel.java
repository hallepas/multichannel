package gui.components;

import java.util.Date;

import gui.helper.GridBagManager;
import gui.helper.MessageProperties;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import message.Message;

public class ReminderPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private GridBagManager guiManager;
	private JLabel timeLb;
	private JLabel dateLb;
	private JLabel imgLb;
	private Message message;

	public ReminderPanel(Message message) {
		this.message = message;
		this.guiManager = new GridBagManager(this);
		this.timeLb = new JLabel();
		this.dateLb = new JLabel();
		// this.imgLb = new JLabel(new ImageIcon("data/images/clock2.png"));
		this.imgLb = new JLabel(new ImageIcon("data/images/reminder_clock3.png"));
		prepare();
	}

	public void reminderUpdate(Date date) {
		if (date != null) {
			this.timeLb.setText("<html><b>Zeit:</b> "+MessageProperties.TIME_FORMATTER.format(date)+"</html");
			this.dateLb.setText("<html><b>Datum:</b> "+MessageProperties.DATE_FORMATTER.format(date)+"</html");
		} else {
			this.timeLb.setText("<html><b>Zeit:</b> -</html");
			this.dateLb.setText("<html><b>Datum:</b>-</html");
		}
	}

	private void prepare(){ 
		reminderUpdate(message.getReminder());
		// setBorder(new TitledBorder("Reminder"));
		guiManager.setX(0).setY(0).setHeight(3).setComp(imgLb);
		guiManager.setX(1).setY(0).setComp(dateLb);
		guiManager.setX(1).setY(1).setComp(timeLb);
		guiManager.setX(1).setY(2).setHeight(2).setComp(new JLabel(""));
	}

}
