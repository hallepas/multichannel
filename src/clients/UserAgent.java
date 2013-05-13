package clients;
import java.util.*;
import message.Message;
import message.Status;
 
/**
 * Der User Agent kümmert sich um das schicken und Empfangen
 * der Nachrichten.
 * Vorläufig auch um das Erstellen und Validieren.
 *
 */
public abstract class UserAgent implements MessageHandler {
	ArrayList<Message> recieveMessage = new ArrayList<Message>(); 
	ArrayList<Message> syncMessage = new ArrayList<Message>();
	
	public abstract Status sendMessages();
	
	public abstract Status receiveMessages();
	
	public List<Status> syncMessages() {
		List<Status> status = new ArrayList<Status>();
		status.add(this.sendMessages());
		status.add(this.receiveMessages());
		return status;
	}
	
}
