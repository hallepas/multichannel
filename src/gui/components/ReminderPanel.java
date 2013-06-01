package gui.components;

import gui.helper.GridBagManager;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class ReminderPanel extends JPanel {

	private GridBagManager guiManager;
	private JLabel timeLb;
	private JLabel dateLb;
	private JLabel imgLb;

	public ReminderPanel() {
		this.guiManager = new GridBagManager(this);
		this.timeLb = new JLabel("Zeit:[time]");
		this.dateLb = new JLabel("Datum: [date]");
		this.imgLb = new JLabel(new ImageIcon("data/images/clock2.jpg"));
		prepare();
	}

	private void prepare() {
		setBorder(new TitledBorder("Reminder"));
		guiManager.setX(0).setY(0).setHeight(3).setComp(imgLb);
		guiManager.setX(1).setY(0).setComp(dateLb);
		guiManager.setX(1).setY(1).setComp(timeLb);
		guiManager.setX(1).setY(2).setHeight(2).setComp(new JLabel(""));
	}

}
