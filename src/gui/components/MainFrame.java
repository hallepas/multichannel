package gui.components;

import gui.helper.GridBagManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import clients.MessageClient;

import devices.Device;
import devices.Smartphone;

import message.Message;
import message.MessageType;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private GridBagManager guiManager;
	private JTabbedPane pane;
	private ArrayList<Message> messages;
	private Device device;

	public MainFrame(MessageType mt, ArrayList<Message> messages, Device device) {
		this.pane = new JTabbedPane();
		this.guiManager = new GridBagManager(this);
		this.messages = messages;
		this.device = device;
		configureFrame();
	}

	private void configureFrame() {
		createTabs();
		guiManager.setX(0).setY(0).setComp(pane);

		setTitle("Multichannel");
		setSize(950, 650);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void createTabs() {
		MessageType messageType;
		Iterator<MessageType> iterator = device.getSupportedMessageFormats().iterator();
		MessageClient mc = device.getMessageClient();

		while (iterator.hasNext()) {
			messageType = iterator.next();
			if (!messageType.equals(MessageType.PRINT)) {
				MessagesTab mf = new MessagesTab(mc.getOnlyType(mc.getMessagesFromInbox(), messageType), messageType);
				pane.addTab(mf.getTabTitle(), mf);
			}
		}
	}

}
