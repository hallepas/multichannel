package gui.frame;

import gui.components.MessagesTab;
import gui.helper.GridBagManager;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import message.MessageType;
import devices.Device;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private GridBagManager guiManager;
	private JTabbedPane tabbedPane;
	private Device device;

	public MainFrame(MessageType mt, Device device) {
		this.tabbedPane = new JTabbedPane();
		this.guiManager = new GridBagManager(this);
		this.device = device;
		configureFrame();
	}
	
	public MainFrame(Device device) {
	        this.tabbedPane = new JTabbedPane();
	        this.guiManager = new GridBagManager(this);
	        this.device = device;
	        configureFrame();
	}

	private void configureFrame() {
		createTabs();
		guiManager.setX(0).setY(0).setComp(new JLabel("Angemeldet mit: "+device.getDeviceName()));
		guiManager.setX(0).setY(1).setWeightX(15).setHeight(10).setComp(tabbedPane);

		setTitle("Multichannel: "+device.getDeviceType());
		setSize(1000, 650);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	private void createTabs() {
		for(MessageType type: device.getSupportedMessageFormats()){
			if (!type.equals(MessageType.PRINT)) {
				MessagesTab mf = new MessagesTab(device, device.getMessageClient(), type);
				tabbedPane.addTab(mf.getTabTitle(), mf);
			}
		}
	}

}
