import java.text.MessageFormat;
import java.util.*;
import message.Message;
import agent.UserAgent;


 public class MessageClient extends UserAgent {
	private MessageClient messageClient;
	private UserAgent userAgent; 
	private Mailbox inbox; 
	private Mailbox outbox; 
	private Mailbox draft; 
	
	public void mapMessagClient (UserAgent useragent) {
		//map MessageType
	  }
	
	public void newMessage(MessageType type) {
		// eine Message wird mit einem entsprechenden Format verfasst
	}
	
	public void getNextMessage(MessageType type) {
		// erhält eine Nachricht
	}
	
	public void getsynchronized(ArrayList<UserAgent> handlers) {
		
	}

	public MessageClient getMessageClient() {
		return messageClient;
	}

	public void setMessageClient(MessageClient messageClient) {
		this.messageClient = messageClient;
	}
	
	public Mailbox getInbox() {
		return inbox;
	}

	public void setInbox(Mailbox inbox) {
		this.inbox = inbox;
	}

	public Mailbox getOutbox() {
		return outbox;
	}

	public void setOutbox(Mailbox outbox) {
		this.outbox = outbox;
	}

	public Mailbox getDraft() {
		return draft;
	}

	public void setDraft(Mailbox draft) {
		this.draft = draft;
	}

	public MessageType getType() {
		return type; // ich weiss nicht, wie man enum erbt, es muss ...  eigentlich eine if-mehthode...
	}

	public void setType(MessageType type) {
		this.type = type; // ich weiss nicht, wie man enum erbt
	}

	public UserAgent getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(UserAgent userAgent) {
		this.userAgent = userAgent;
	}
}
 
