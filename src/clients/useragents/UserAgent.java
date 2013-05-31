package clients.useragents;
import java.util.*;
import java.util.logging.Logger;

import message.Message;
import message.MessageType;
import message.Status;

import server.ServerProxy;
import server.ServerSocket;
import server.TheInternet;

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
    private ServerSocket server;
    private static final Logger log = Logger.getLogger( UserAgent.class.getName() );
    private static final TheInternet internet = TheInternet.goOnline();

    public ServerSocket getServer(){
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
            return new CellphoneUserAgent();
        case MMS:
            return new CellphoneUserAgent();
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
     * Login bei einem entfernten Server. Speichert den Socket im Attribut
     * server. Die zweite Methode übergibt ein Client-Socket, der Push-
     * Nachrichten unterstützt.
     * @return Status
     */
    public Status login() {
        return login(null);
    }
    public Status login(ClientProxy client) {
        checkForAccount();
        ServerProxy ip = internet.lookup(account.getServer());
        if (ip == null) {
            log.fine("Could not find " + account.getServer()+ "." );
            return new Status(404, "Server " + account.getServer() + "not found.");
        }
        ServerSocket socket = ip.login(account.getAddress(), 
                account.getLoginCredentials(), client);
        if(socket != null) {
            this.server = socket;
            log.fine("Successfully logged in at " + account.getServer()+ "." );
            return new Status(200, "Successfully logged in at " + account.getServer());
        } 
        log.fine("Could not log in at " + account.getServer()+ "." );
        return new Status(405, "Login at server " + account.getServer() + " failed");
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
        return server instanceof ServerSocket;
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
            log.fine("Message sent. Status: " + status);
            if (status.getCode() != 200) {
                message.setDate(null);
            }
            return status;
        }		
        log.info("Tried to send message " + message + ", but user is not logged in.");
        return new Status(405, "Not logged in.");
    }


    public List<Message> receiveMessages() {
        checkForAccount();
        if (isLoggedIn()){
            return getServer().poll();
        }
        log.info("Tried to receive messages, but user is not logged in.");
        return null;
    }

}
