import java.util.ArrayList;

import agent.UserAgent;


public class MessageClient {
	MessageClient messageClient = new MessageClient();
	ArrayList<UserAgent> handlers = new ArryListy<UserAgent>();
	Message newMessage = new Message();
	Mailbox inbox = new Mailbox();
	Mailbox outbox = new Mailbox();
	Mailbox drafts = new drafts();
	
	public void newMessage(MessageFormat format) {
		// eine Message wird mit einem entsprechenden Format verfasst
	}
	
	public void getNextMessage() {
		// erhält eine Nachricht
	}
	
	public void getsynchronized(ArrayList<UserAgent> handlers) {
		
	}
}
