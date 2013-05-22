package devices;

import message.Message;
import message.MessageType;
import message.UserAgent;
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
	
	protected MessageClient getMessageClient() {
		return messageclient;
	}
	
	
	protected Message newMessage(MessageType type){
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
	
}

