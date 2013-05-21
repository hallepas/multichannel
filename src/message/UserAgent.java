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
	
	public abstract List<Message> receiveMessages() throws NoAccountException;
		
	protected void checkForAccount() throws NoAccountException {
		if (!(account instanceof Account)){
			throw new NoAccountException("Class " + this + "has no account.");
		}
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
