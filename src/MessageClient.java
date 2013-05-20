import java.text.MessageFormat;
import java.util.ArrayList;

import userAg.UserAgent;

import message.Message;

public class MessageClient {
	private MessageClient messageClient = new MessageClient();
	private ArrayList<UserAgent> handlers = new ArrayList<UserAgent>();
	private Message newMessage;
	private Mailbox inbox = new Mailbox();
	private Mailbox outbox = new Mailbox();
	private Mailbox drafts;
	
	public void newMessage(MessageFormat format) {
		// eine Message wird mit einem entsprechenden Format verfasst
	}
	
	public void getNextNewMessage() {
		
	}
	
	public void getSynchronized() {
		//die Statusliste soll synchronisiert werden, ich weiss nicht wie
	}
	
	public void changeMessageType() {
		
	}
	
}
