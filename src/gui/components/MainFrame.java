package gui.components;

import gui.helper.GridBagManager;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import message.Message;
import message.MessageType;

public class MainFrame  extends JFrame{

	private static final long serialVersionUID = 1L;
	private GridBagManager guiManager;
	private JTabbedPane pane;
	private MessagesTab mf;
	
	public MainFrame(MessageType mt, ArrayList<Message> messages) {
		this.pane = new JTabbedPane();
		this. mf = new MessagesTab(mt, messages);
		this.guiManager = new GridBagManager(this);
		configureFrame();
	}
	
	private void configureFrame() {

		pane.addTab(mf.getTabTitle(), mf);
		guiManager.setX(0).setY(0).setComp(pane);
		
		setTitle("Multichannel");
		setSize(950, 650);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
