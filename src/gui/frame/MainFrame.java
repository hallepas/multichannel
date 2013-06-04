package gui.frame;

import gui.components.MessagesTab;
import gui.helper.GridBagManager;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import message.MessageType;
import devices.Device;

/**
 * Das Hauptfenster des Programmes
 * 
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Verwaltet das Fenster 
	 */
	private GridBagManager guiManager;
	
	/**
	 * Der TabbedPane
	 */
	private JTabbedPane tabbedPane;
	
	/**
	 * Das Gerät mit welcher der User angemeldet ist
	 */
	private Device device;

	/**
	 * Initialisiert die Klasse
	 * 
	 * @param messageType Der MessageType
	 * @param device Das Gerät mit welchem der User angemolden ist
	 */
	
	public MainFrame(MessageType messageType, Device device) {
		this.tabbedPane = new JTabbedPane();
		this.guiManager = new GridBagManager(this);
		this.device = device;
		configureFrame();
	}

	/**
	 * Initialisiert die Klasse
	 * @param device Das Gerät mit welchem der User angemolden ist
	 */
	public MainFrame(Device device) {
		this.tabbedPane = new JTabbedPane();
		this.guiManager = new GridBagManager(this);
		this.device = device;
		configureFrame();
	}

	/**
	 * Baut das Fenster zusammen
	 */
	private void configureFrame() {
		createTabs();
		guiManager.setX(0).setY(0).setWeightY(0).setHeight(1).setComp(new JLabel("Angemeldet mit: " + device.getDeviceName()));
		guiManager.setX(0).setY(1).setWeightY(1).setHeight(1).setWidth(9).setComp(tabbedPane);

		setTitle("Multichannel: " + device.getDeviceType());
		setSize(1000, 650);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	/**
	 * Erstellt alle Tabs welche der Device akzeptiert
	 */
	private void createTabs() {
		for (MessageType type : device.getSupportedMessageFormats()) {
			if (!type.equals(MessageType.PRINT)) {
				MessagesTab mf = new MessagesTab(device, device.getMessageClient(), type);
				tabbedPane.addTab(mf.getTabTitle(), mf);
			}
		}
	}

}
