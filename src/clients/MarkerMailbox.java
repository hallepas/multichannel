package clients;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import message.Message;

/**
 * Die MarkerMailbox kann Nachrichten als ungelesen markieren
 * und überwachen, ob Nachrichten bereits gelesen worden sind.
 *
 */
public class MarkerMailbox extends Mailbox {
	private static final long serialVersionUID = 1L;

	private List<Message> unreadMessages = new ArrayList<Message>();
	
	public void setMessages(List<Message> messages, boolean asUnread) {
		super.setMessages(messages);
		if (asUnread) this.unreadMessages = messages;
	}
	public void setMessages(List<Message> messages) {
		super.setMessages(messages);
		this.unreadMessages = messages;
	}
	
	public void add(Message message) {
		super.add(message);
		unreadMessages.add(message);
	}
	public void sort() {
		super.sort();
		Collections.sort(unreadMessages);
	}
	public long unreadMessageCount(){
		return this.unreadMessages.size();
	}
	public List<Message> getUnreadMessages(){
		return unreadMessages;
	}
	public void markAllMessagesAsRead(){
		unreadMessages = new ArrayList<Message>();
	}
	
	public List<Message> popUnreadMessages(){
		List<Message> messages = unreadMessages;
		this.markAllMessagesAsRead();
		return messages;
	}
	public void markMessageAsRead(Message message) {
		if(unreadMessages.contains(message)){
			unreadMessages.remove(message);
		}
	}
	public void deleteMessage(Message message){
		try {
			unreadMessages.remove(message);
		} catch (NullPointerException e) {}
		super.deleteMessage(message);
	}
	

}
