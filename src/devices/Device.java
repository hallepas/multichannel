package devices;

import message.Message;
import message.MessageType;
import clients.MessageClient;

/**
 * Superklasse für alle Geräte.
 * Unterklassen müssen MessageClient definieren und setzen.
 * 
 */
public abstract class Device {
	private final MessageClient messageclient; 
	
	protected Device(MessageClient client) {
		messageclient = client;
	}
	
	protected Message newMessage(MessageType type){
		try {
			return messageclient.newMessage(type);
		} catch (IllegalArgumentException e) {
			System.out.println("Kann keine Nachricht vom Typ " + type + " erstellen.");
			return null;
		}
	}
}


