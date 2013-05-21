package clients;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import message.Message;
import message.MessageType;
import message.UserAgent;


/**
 * Der MessageClient verwaltet und speichert die Nachrichten.
 * Dafür hat er drei Mailboxen: Inbox, Outbox und Entwürfe.
 * Er kann Nachrichten in der Inbox als Gelesen markieren.
 * Der Typ der verwalteten Nachrichten wird dynamisch bei
 * der initialisierung festgelegt.
 */
public class MessageClient {
	private HashMap<MessageType, UserAgent> handlers;
	private MarkerMailbox inbox = new MarkerMailbox(); 
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
	public UserAgent getUserAgentFor(MessageType type) {
		return handlers.get(type);
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
	

	public List<Message> getMessagesFromInbox() {
		return inbox.getMessages();
	}
	public List<Message> getMessagesFromOutbox() {
		return outbox.getMessages();
	}
	public List<Message> getDrafts() {
		return drafts.getMessages();
	}
	public List<Message> getUnreadMessages() {
		return this.inbox.getUnreadMessages();
	}
	public void deleteMessageFromInbox(Message message) {
		this.inbox.deleteMessage(message);
	}
	public void deleteDraft(Message message) {
		this.drafts.deleteMessage(message);
	}
	public void addToInbox(Message message) {
		this.inbox.add(message);
	}
	public void saveDraft(Message message) {
		this.drafts.add(message);
	}
	public void addToOutbox(Message message) {
		this.outbox.add(message);
	}
	
	
}
