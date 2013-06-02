package clients;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

// TODO: remove
import javax.swing.JOptionPane;

import server.MessageServer;

import clients.handlers.MessageHandler;
import clients.useragents.UserAgent;
import exceptions.NoAccountException;
import exceptions.NotRequiredException;
import exceptions.ValidationError;
import gui.dialog.ReminderRememberDialog;

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


    /**
     * Dieser Konstruktor akzeptiert ein Array von Typen und 
     * erstellt einen MessageClient, der diese Typen verarbeiten
     * kann.
     * @param types Array aus MessageType
     */
    public MessageClient(MessageType[] types) {
        handlers = new HashMap<MessageType, MessageHandler>();
	agents = new HashMap<MessageType, UserAgent>();
	for(MessageType type : types) {
	    agents.put(type, UserAgent.newUserAgentForType(type));
	    handlers.put(type, MessageHandler.getHandlerForType(type));
	}
	// Observer hinzufügen, der Nachrichten weiter schickt.
	outbox.addObserver(new OutboundListener());
	ScheduledExecutorService scheduler = Executors.newScheduledThreadPool( 1 );
	scheduler.scheduleAtFixedRate(new ReminderChecker(this),
	          5 /* Startverzögerung */,
	          5 /* Dauer */,
	          TimeUnit.SECONDS );
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
            } catch (NotRequiredException e) {} 
            catch (NoAccountException e) {
                if (type != MessageType.PRINT) {
                    displayModal("No account for " + type.getTypeName() + "s.");
                }
            }
        }
    }
    public void logout(){
        for (MessageType type:agents.keySet()) {
            try {
                agents.get(type).logout();
            } catch (Exception e) { }
        }
    }
    
    /**
     * Diese Methode schaut, ob neue Nachrichten auf dem Server liegen.
     * Sie wird beim Login automatisch aufgerufen, danach über einen Timer
     * oder einen Push-Aufruf.
     * @param type
     */
    public void checkForNewMessages(MessageType type){
        log.fine("Checking for new " + type);
        List<Message> messages = agents.get(type).receiveMessages();
        if(messages != null) {
            for(Message message : messages) {
                log.fine("Received new " + type);
                inbox.add(message);
            } 
        } else {
            log.fine("No new " + type);
        }
    }
    public void checkForNewMessages() {
        for (MessageType type:getSupportedMessageFormats()) {
            checkForNewMessages(type);
        }
    }
    public boolean canPrint(){
        return getSupportedMessageFormats().contains(MessageType.PRINT);
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
	this.drafts.put(message);
    }
    
    /**
     * Die Methode wird aufgerufen, wenn man auf den Submit Knopf drückt.
     * @param message Nachricht
     */
    public void submit(Message message) {
        // Hat es einen Reminder?
        if(message.getReminder() != null 
                && message.getReminder().after(new Date())) {
            this.saveDraft(message);
            log.fine("Message has a reminder. saved as draft");
        } else {
            if(this.validateMessage(message)){
                this.outbox.put(message);
                this.drafts.deleteMessage(message);  
            }
        }
    }
    private void checkReminder(){
        for (Message message: drafts.getMessages()) {
            if(message.getReminder() != null 
               && message.getReminder().before(new Date())) {
               log.info("Reminder für Nachricht " + message + "anzeigen");
               ReminderRememberDialog.createDialog(message, this);
               // Reminder um eine Minute nach vorne stellen
               Date inOneMinute = new Date(message.getReminder().getTime() + (1000*60));
               message.setReminder(inOneMinute);
            }
        }
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
    	//JOptionPane.showConfirmDialog(null, message, "Fehler", JOptionPane.PLAIN_MESSAGE);
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
    public boolean validateMessage (Message message) {
        try {
            this.handlers.get(message.getType()).validateMessage(message);
            return true;
        } catch (ValidationError e) {
            displayModal(e.getMessage());
            return false;
        }
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
                log.fine("Message sent");
            }
        }
    }
    
    public class MessageProxy implements ClientProxy {

        @Override
        public void newMessages(MessageType type, String server) {
            // TODO: Filter nach dem richtigen Server.
            log.fine("Callback from " + server +" checking messages...");
            checkForNewMessages(type);
        }
    }
    
    public class ReminderChecker implements Runnable {
        private MessageClient client;
        
        public ReminderChecker(MessageClient client) {
            this.client = client;
        }
        
        @Override 
        public void run() {
              client.checkReminder();
           }

    }


        

}
