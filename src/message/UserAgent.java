package message;
import java.util.*;

import clients.Account;
import clients.ClientProxy;
import clients.MessageHandler;
import clients.ServerProxy;

import exceptions.NoAccountException;
 
/**
 * Der User Agent kümmert sich um das Einloggen am Server sowie das schicken 
 * und Empfangen der Nachrichten.
 * Vorläufig auch um das Erstellen und Validieren.
 *
 */
public abstract class UserAgent implements MessageHandler {
	private Account account;

	
	public abstract Status sendMessages(List<Message> messages) throws NoAccountException;
	
	// Maybe iplement this here.
	public abstract List<Message> receiveMessages() throws NoAccountException;
	
	public static UserAgent getUserAgentForType(MessageType type) {
		switch(type) {
		case SMS:
			return new SMSUserAgent();
		case MMS:
			return new MMSUserAgent();
		case EMAIL:
			return new EmailUserAgent();
		case PRINT:
			return new PrintJobUserAgent();
		}
		return null;
	}
	
	protected void checkForAccount() throws NoAccountException {
		if (!(account instanceof Account)){
			throw new NoAccountException("Class " + this + "has no account.");
		}
	}
	public String getFromAddress() {
		return (account != null) ? account.getAddress() : "";
	}
	
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public ServerProxy login() throws NoAccountException {
		checkForAccount();
		return account.getServer().login(account.getAddress(), account.getLoginCredentials());
	}
	public ServerProxy login(ClientProxy client) throws NoAccountException {
		checkForAccount();
		return account.getServer().login(account.getAddress(), account.getLoginCredentials(), client);
	}
	
}
