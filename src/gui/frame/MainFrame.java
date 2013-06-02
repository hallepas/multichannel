package gui.frame;

import gui.components.MessagesTab;
import gui.helper.GridBagManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import sun.security.util.DerValue;

import clients.MessageClient;

import devices.Device;
import devices.Smartphone;

import message.Message;
import message.MessageType;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private GridBagManager guiManager;
	private JTabbedPane pane;
	private Device device;

	public MainFrame(MessageType mt, Device device) {
		this.pane = new JTabbedPane();
		this.guiManager = new GridBagManager(this);
		this.device = device;
		configureFrame();
	}
	public MainFrame(Device device) {
	        this.pane = new JTabbedPane();
	        this.guiManager = new GridBagManager(this);
	        this.device = device;
	        configureFrame();
	}

	private void configureFrame() {
		createTabs();
		//TODO Username anzeigen
		guiManager.setX(0).setY(0).setComp(new JLabel("Angemeldet mit: "+device.getDeviceName()+" ("+device.getDeviceType()+")"));
		guiManager.setX(0).setY(1).setComp(pane);

		setTitle("Multichannel");
		setSize(1000, 650);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	private void createTabs() {
		for(MessageType type: device.getSupportedMessageFormats()){
			if (!type.equals(MessageType.PRINT)) {
				MessagesTab mf = new MessagesTab(device.getMessageClient(), type);
				pane.addTab(mf.getTabTitle(), mf);
			}
		}
	}

}
