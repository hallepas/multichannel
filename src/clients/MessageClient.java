package clients;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import server.MessageServer;
import server.ServerProxy;

import clients.handlers.MessageHandler;
import clients.useragents.UserAgent;
import exceptions.NoAccountException;

import message.Message;
import message.MessageType;
import message.Status;


/**
 * Der MessageClient verwaltet und speichert die Nachrichten.
 * Dafür hat er drei Mailboxen: Inbox, Outbox und Entwürfe.
 * Er kann Nachrichten in der Inbox als Gelesen markieren.
 * Der Typ der verwalteten Nachrichten wird dynamisch bei
 * der initialisierung festgelegt.
 */
public class MessageClient {
    // Der Handler kümmert sich um den Versand und Empfang
    private HashMap<MessageType, MessageHandler> handlers;
    // Der User Agent kümmert sich um die Erstellung und Validierung
    private HashMap<MessageType, UserAgent> agents;

    private MarkerMailbox inbox = new MarkerMailbox(); 
    private Mailbox outbox = new Mailbox(); 
    private Mailbox drafts = new Mailbox(); 
    private static final Logger log = Logger.getLogger( MessageServer.class.getName() );
    private boolean canPrint;


    /**
     * Dieser Konstruktor akzeptiert ein Array von Typen und 
     * erstellt einen MessageClient, der diese Typen verarbeiten
     * kann.
     * @param types Array aus MessageType
     */
    public MessageClient(MessageType[] types, boolean canPrint) {
    this.canPrint = canPrint;
    handlers = new HashMap<MessageType, MessageHandler>();
	agents = new HashMap<MessageType, UserAgent>();
	for(MessageType type : types) {
	    agents.put(type, UserAgent.getUserAgentForType(type));
	    handlers.put(type, MessageHandler.getHandlerForType(type));
	}
	// Observer hinzufügen, der Nachrichten weiter schickt.
	outbox.addObserver(new OutboundListener());
    }
    public UserAgent getUserAgentFor(MessageType type) {
	return agents.get(type);
    }
    public void setAccountFor(MessageType type, Account account){
        agents.get(type).setAccount(account);
    }
    public void login(){
        for (MessageType type:agents.keySet()) {
            try {
                Status status = agents.get(type).login(new MessageProxy());
                if (status.getCode() != 200) {
                    displayModal(status.getDescription());
                } else {
                    checkForNewMessages(type);
                }
            } catch (NoAccountException e) {
                if (type != MessageType.PRINT) {
                    displayModal("No account for " + type.getTypeName() + "s.");
                }
            }
        }
    }
    
    /**
     * Diese Methode schaut, ob neue Nachrichten auf dem Server liegen.
     * Sie wird beim Login automatisch aufgerufen, danach über einen Timer
     * oder einen Push-Aufruf.
     * @param type
     */
    public void checkForNewMessages(MessageType type){
        for(Message message : agents.get(type).receiveMessages()) {
            inbox.add(message);
        }
    }
    public void checkForNewMessages() {
        for (MessageType type:getSupportedMessageFormats()) {
            checkForNewMessages(type);
        }
    }


    /**
     * Um zu überprüfen, welche Nachrichtenformate unterstützt werden:
     * @return Set mit den Typen der unterstützten Formate.
     */
    public Set<MessageType> getSupportedMessageFormats(){
	return handlers.keySet();
    }

    /**
     * Factory für Nachrichten. Setzt gleich das From-Feld.
     * @param type Nachrichtentyp
     * @return Nachricht im entsprechenden Typ.
     */
    public Message newMessage(MessageType type) {
	if (!handlers.containsKey(type)) {
	    throw new IllegalArgumentException(
		    "Kann keine " + type + "-Nachricht erstellen.");
	}
	Message message = handlers.get(type).newMessage();
	message.setFrom(agents.get(type).getFromAddress());
	return message;
    }


    public List<Message> getMessagesFromInbox() {
	return inbox.getMessages();
    }
    public List<Message> getMessagesFromOutbox() {
	return outbox.getMessages();
    }
    public List<Message> getDrafts() {
	return drafts.getMessages();
    }
    public List<Message> getUnreadMessages() {
	return this.inbox.getUnreadMessages();
    }
    public void deleteMessageFromInbox(Message message) {
	this.inbox.deleteMessage(message);
    }
    public void deleteDraft(Message message) {
	this.drafts.deleteMessage(message);
    }
    public void addToInbox(Message message) {
	this.inbox.add(message);
    }
    public void saveDraft(Message message) {
	this.drafts.add(message);
    }
    public void submit(Message message) {
    // TODO: Message aus drafts löschen.
	this.outbox.add(message);
    }
    
    /*
     * Die Methode schickt alle Nachrichten aus der Outbox an
     * den Server. Falls der Versand fehlschlägt, bleiben sie in
     * der Outbox liegen.
     */
    public void sendMessagesToServer(){
        for (Message message:outbox.getMessages()) {
            UserAgent agent = getUserAgentFor(message.getType());
            Status status = agent.sendMessage(message);
            if (status.getCode() == 200) {
                outbox.deleteMessage(message);
            } else {
                displayModal("Fehler: " + status.getDescription());
            }
        }
    }
    
    /**
     * Bei Fehlern usw. wird dem Benutzer eine Meldung angezeigt.
     * @param message
     */
    public void displayModal(String message){
    	JOptionPane.showConfirmDialog(null, message, "Fehler", JOptionPane.PLAIN_MESSAGE);
        log.info(message);
    }

    /**
     * Diese Funktion filtert die Nachrichten nach Typ.
     * @param messages Liste von Messages
     * @param type MessageType. Z.B. MessageType.EMAIL
     * @return Liste von Messages eines bestimmten Typs.
     */
    public static List<Message> getOnlyType(List<Message> messages, MessageType type){
	List<Message> messageList = new ArrayList<Message>();
	for (Message message : messages){
	    if(message.getType() == type){
		messageList.add(message);
	    }
	}
	return messageList;
    }
    
    public boolean canPrint(){
    	return canPrint;
    }
    
    /**
     * Sobald eine Nachricht der Outbox hinzugefügt wurde, 
     * wird sie an den Server geschickt, falls online.
     *
     */
    public class OutboundListener implements Observer {
        @Override public void update(Observable o, Object arg) {
            if(((String) arg).equals("added")) {
                sendMessagesToServer();
                log.fine("Message added to outbox. Sending...");
            }
        }
    }
    
    public class MessageProxy implements ClientProxy {

        @Override
        public void newMessages(ServerProxy server) {
            // TODO: Filter nach dem richtigen Server.
            log.fine("Callback from " + server +" checking messages...");
            checkForNewMessages();
        }
        
    }


}
