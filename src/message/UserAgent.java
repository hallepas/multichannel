package message;
import java.util.*;

import clients.Account;
import clients.MessageHandler;

import exceptions.NoAccountException;
 
/**
 * Der User Agent kümmert sich um das schicken und Empfangen
 * der Nachrichten.
 * Vorläufig auch um das Erstellen und Validieren.
 *
 */
public abstract class UserAgent implements MessageHandler {
	private Account account;
	
	ArrayList<Message> recieveMessage = new ArrayList<Message>(); 
	ArrayList<Message> syncMessage = new ArrayList<Message>();
	

	public abstract Status sendMessages() throws NoAccountException;
	
	public abstract Status receiveMessages() throws NoAccountException;
		
	protected void checkForAccount() throws NoAccountException{
		if (!(account instanceof Account)){
			throw new NoAccountException("Class " + this + "has no account.");
		}
	}
	
	public List<Status> syncMessages() throws NoAccountException {
		List<Status> status = new ArrayList<Status>();
		status.add(this.sendMessages());
		status.add(this.receiveMessages());
		return status;
	}
	
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	
}
