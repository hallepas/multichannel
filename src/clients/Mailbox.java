package clients;
import java.io.Serializable;
import java.util.*;
import message.Message;

public class Mailbox implements Serializable {
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
	public double messageCount() {
		return this.messages.size();
	}
	
	
	
}
