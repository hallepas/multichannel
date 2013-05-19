package clients;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import exceptions.NoAccountException;

import message.Message;
import message.MessageType;
import message.Status;
import message.UserAgent;

public class MessageClient {
	private HashMap<MessageType, UserAgent> handlers;
	private Mailbox inbox = new Mailbox(); 
	private Mailbox outbox = new Mailbox(); 
	private Mailbox drafts = new Mailbox(); 
	
	public MessageClient(HashMap<MessageType, UserAgent> handlers) {
		this.handlers = handlers;
	}
	/**
	 * Dieser Konstruktor akzeptiert ein Array von Typen und 
	 * erstellt einen MessageClient, der diese Typen verarbeiten
	 * kann.
	 * @param types Array aus MessageType
	 */
	public MessageClient(MessageType[] types) {
		handlers = new HashMap<MessageType, UserAgent>();
		for(MessageType type : types) {
			handlers.put(type, type.agent());
		}
	}
	
	
	/**
	 * Um zu überprüfen, welche Nachrichtenformate unterstützt werden:
	 * @return Set mit den Typen der unterstützten Formate.
	 */
	public Set<MessageType> getSupportedMessageFormats(){
		return handlers.keySet();
	}
	
	/**
	 * Factory für Nachrichten
	 * @param type Nachrichtentyp
	 * @return Nachricht im entsprechenden Typ.
	 */
	public Message newMessage(MessageType type) {
		if (!handlers.containsKey(type)) {
			throw new IllegalArgumentException(
					"Kann keine " + type + "-Nachricht erstellen.");
		}
		return handlers.get(type).newMessage();
	}
	

	public List<Status> synchronize() throws NoAccountException {
		List<Status> status = new ArrayList<Status>();
		for (UserAgent agent: handlers.values()) {
			status.addAll(agent.syncMessages());
		}
		return status;
	}
	
	
}
