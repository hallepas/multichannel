package clients.useragents;
import java.util.*;

import message.Message;
import message.MessageType;
import message.Status;

import server.ServerProxy;

import clients.Account;
import clients.ClientProxy;

import exceptions.NoAccountException;

/**
 * Der User Agent kümmert sich um das Einloggen am Server sowie das schicken 
 * und Empfangen der Nachrichten.
 *
 */
public abstract class UserAgent {
    private Account account;
    private ServerProxy server;

    public ServerProxy getServer(){
        // No magic here.
        return server;
    }

    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * Factory Methode um konkrete User Agents für die entsprechenden
     * Message-Typen zu erstellen.
     * @param type
     * @return Konkrete UserAgent Instanz für den entsprechenden Typ.
     */
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

    /**
     * Die Benutzer-Adresse als Absender-Adresse zurückgeben 
     * @return Username
     */
    public String getFromAddress() {
        return (account != null) ? account.getAddress() : "";
    }


    /**
     * Login bei einem entfernten Server. Speichert den Proxy im Attribut
     * server. Die zweite Methode übergibt ein Client-Proxy, der Push-
     * Nachrichten unterstützt.
     * @return Status
     */
    public Status login() {
        checkForAccount();
        server = account.getServer().login(account.getAddress(), 
                                           account.getLoginCredentials());
        return new Status(200, "Login at " + server.getServerName() + ".");
    }
    public Status login(ClientProxy client) {
        checkForAccount();
        server = account.getServer().login(account.getAddress(), 
                                         account.getLoginCredentials(), client);
        return new Status(200, "Login at " + server.getServerName() + ".");
    }

    public Status logout() {
        if (isLoggedIn()){
            Status status = server.logout();
            server = null;
            return status;
        } else {
            return new Status(200, "Was not even logged in.");
        }
    }

    public boolean isLoggedIn(){
        return server instanceof ServerProxy;
    }

    /**
     * Die Hauptmethoden um Nachrichten zu senden und zu empfangen.
     * @param message
     * @return Status
     */
    public Status sendMessage(Message message) {
        checkForAccount();
        if (isLoggedIn()){
            message.setDate(new Date());
            Status status = getServer().put(message);
            if (status.getCode() != 200) {
                message.setDate(null);
            }
            return status;
        }		
        return new Status(400, "Not online");
    }


    public List<Message> receiveMessages() {
        checkForAccount();
        if (isLoggedIn()){
            return getServer().poll();
        }
        return null;
    }

}
