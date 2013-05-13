import java.text.MessageFormat;
import java.util.ArrayList;

import userAg.UserAgent;

import message.Message;

public class MessageClient {
	MessageClient messageClient = new MessageClient();
	ArrayList<UserAgent> handlers = new ArrayList<UserAgent>();
	Message newMessage = new Message();
	Mailbox inbox = new Mailbox();
	Mailbox outbox = new Mailbox();
	Mailbox drafts;
	
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
