import java.util.Map;
import userAg.UserAgent;

public class MessageClient {
	private MessageType type; 
	Map<MessageType, UserAgent> handlers = new Map<MessageType, UserAgent>();
	private Mailbox inbox; 
	private Mailbox outbox; 
	private Mailbox draft; 
	
	public void newMessage() {
	
		
	}
	
	public void getNextNewMessage() {
		
	}
	
	public void getSynchronized() {
		//die Statusliste soll synchronisiert werden, ich weiss nicht wie
	}
	
	public void changeMessageType() {
		
	}
	
	
	
}
