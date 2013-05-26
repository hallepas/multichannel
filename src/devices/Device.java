package devices;

import java.util.Set;

import clients.MessageClient;
import message.Message;
import message.MessageType;
import message.UserAgent;

/**
 * Superklasse für alle Geräte. Unterklassen müssen MessageClient definieren und
 * setzen.
 * 
 */
public abstract class Device {
	private final MessageClient messageclient;
	private String deviceName;
	private String deviceType;

	protected Device(MessageClient client, String deviceName, String deviceType) {
		messageclient = client;
		this.deviceName = deviceName;
		this.deviceType = deviceType;
	}

	public MessageClient getMessageClient() {
		return messageclient;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public String getDeviceType() {
		return deviceType;
	}

	protected Message newMessage(MessageType type) {
		try {
			return messageclient.newMessage(type);
		} catch (IllegalArgumentException e) {
			System.out.println("Kann keine Nachricht vom Typ " + type + " erstellen.");
			return null;
		}
	}

	protected UserAgent getUserAgentFor(MessageType type) {
		return messageclient.getUserAgentFor(type);
	}

	/**
	 * Um zu überprüfen, welche Nachrichtenformate unterstützt werden:
	 * 
	 * @return Set mit den Typen der unterstützten Formate.
	 */
	public Set<MessageType> getSupportedMessageFormats() {
		return messageclient.getSupportedMessageFormats();
	}

}
