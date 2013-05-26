package server;
import java.util.*;

import client.credentials.Credentials;
import clients.ClientProxy;
import clients.Mailbox;

import message.Message;
import message.Status;



public abstract class MessageServer {
    private Map<String, ClientProxy> accountsOnline;
    private Map<String, Credentials> accounts;
    private Map<String, Mailbox> messages;
    private final String domain;
    private final String serverName;

    protected MessageServer(String name, String domain){
        this.domain = domain;
        this.serverName = name;
        accountsOnline = new HashMap<String, ClientProxy>();
        accounts = new HashMap<String, Credentials>();
        messages = new HashMap<String, Mailbox>();
    }

    public String getDomain(){
        return domain;
    }
    public String getName(){
        return serverName;
    }

    public Status register(String name, Credentials credentials) {
        if(!accounts.containsKey(name)) {
            accounts.put(name, credentials);
            messages.put(name, new Mailbox());
            return new Status(200, "Account " + name + 
                    " registered successfully with " + name +".");
        } else {
            if(accounts.get(name).equals(credentials)) {
                return new Status(500, "Account " + name + 
                        " already registered.");
            } else {
                return new Status(500, "Account " + name + 
                        " already in use. Use a different name.");
            }
        }
    }
    public boolean doesAccountExist(String name) {
        return accounts.containsKey(name);
    }
    public boolean isUserLoggedIn(String name) {
        return accountsOnline.containsKey(name);
    }
    /**
     * Clients that don't implement the push interface are registered here.
     */
    public ServerProxy login(String name, Credentials credentials) {
        if(!doesAccountExist(name)) return null;

        if(accounts.get(name).equals(credentials)) {
            accountsOnline.put(name, null);
            return new Proxy(name);
        }
        return null;
    }
    /**
     * If the client implements the push interface, you can call him back.
     */
    public ServerProxy login(String name, Credentials credentials, 
            ClientProxy client) {
        if(!doesAccountExist(name)) return null;
        if(accounts.get(name).equals(credentials)) {
            accountsOnline.put(name, client);
            return new Proxy(name);
        }
        return null;
    }

    public Status logout(String name){
        if(accountsOnline.containsKey(name)){
            accountsOnline.remove(name);
            return new Status(200, "User " + name + " logged out");
        } else {
            return new Status(500, "User " + name + " was not logged in.");
        }
    }

    protected abstract String getDomainForAddress(String name);

    protected ServerProxy findServerForDomain(String domain){
        String domainname = getDomainForAddress(domain);
        // TODO: a lot
        return null;
    };



    /**
     * Liefert die Nachrichten des Benutzers aus und löscht sie lokal.
     * @param name Benutzername
     * @return Liste mit Nachrichten
     */
    protected List<Message> doPoll(String name) {
        // TODO: Exception werfen.
        // Benutzer muss eingeloggt sein.
        if(!accountsOnline.containsKey(name)) return null;
        Mailbox box = messages.get(name);
        List<Message> newMessages = box.getMessages();
        // delete Messages in Inbox
        box.setMessages(new ArrayList<Message>()); 
        return newMessages;
    }

    /**
     * Nachrichten an lokale Empfänger werden in deren Mailbox verschoben.
     * Wenn der Empfänger nicht vorhanden ist, wird die Nachricht weitergeleitet.
     * @param name Empfänger
     * @param message Nachricht
     * @return Status
     */
    protected Status deliver(String name, Message message){
        // Sort messages for local and external users.
        Map<String, Message> external = new HashMap<String, Message>();
        for(String receiver : message.getTo()) {
            if(accounts.containsKey(name)) {
                messages.get(receiver).add(message);
            } else {
                external.put(getDomainForAddress(receiver), message);
            }
        }
        forwardMessages(external);
        return new Status(200, "All messages forwarded.");
    }

    /**
     * Message Forwarding läuft in seinem eigenen Thread.
     * @param forwards Map aus Domain und Nachricht
     */
    protected void forwardMessages(Map<String,Message> forwards) {
        Forwarder forwarder = new Forwarder(forwards);
        forwarder.run();
    }

    /**
     * Innere Klasse, die sich um das Forwarding von Messages kümmert.
     * Erst werden die externen Server gesucht, danach die entsprechenden
     * Nachrichten an sie weitergeleitet.
     */
    public class Forwarder implements Runnable {
        private final Map<String,Message> forwards;
        private Map<String, ServerProxy> servers = 
                new HashMap<String, ServerProxy>();

        protected Forwarder(Map<String, Message> forwards) {
            this.forwards = forwards;
        }
        public void run() {
            for (String domain : forwards.keySet()) {
                try {
                    servers.put(domain, findServerForDomain(domain));
                } catch (Exception e) { 
                    // TODO: Notify sender;
                }
            }
            for (String domain: servers.keySet()) {
                servers.get(domain).put(forwards.get(domain));
            }
        }
    }


    public class Proxy implements ServerProxy {
        private final String name;
        private Proxy(String name) {
            this.name = name;
        }

        @Override
        public List<Message> poll() {
            return doPoll(name);
        }

        @Override
        public Status put(Message message) {
            if(!accountsOnline.containsKey(name)) {
                return new Status(403, "User " + name + "is not logged in.");
            } else {
                return deliver(name, message);
            }
        }
        @Override
        public String getServerName(){
            return serverName;
        }

        @Override
        public Status logout() {
            return MessageServer.this.logout(name);
        }


    }

}
