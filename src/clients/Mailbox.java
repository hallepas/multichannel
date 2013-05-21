package clients;
import java.io.Serializable;
import java.util.*;
import message.Message;

/**
 * Die Mailbox enthält Nachrichten. Sie wird sowohl im MessageClient
 * als auch im MessageServer verwendet.
 * Ausserdem ist sie serialisierbar, so dass Nachrichten persistent sind.
 *
 */
public class Mailbox implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<Message> messages = new ArrayList<Message>();

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
	public void add(Message message) {
		messages.add(message);
	}
	
	public void sort() {
		Collections.sort(messages);
	}
	public long messageCount() {
		return this.messages.size();
	}
	public void deleteMessage(Message message){
		try {
			messages.remove(message);
		} catch (NullPointerException e) {}
	}
	
	
}
