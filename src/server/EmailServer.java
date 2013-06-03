package server;

import clients.credentials.Credentials;
import clients.credentials.UsernamePassword;
import message.EmailMessage;
import message.Message;
import message.Status;

public class EmailServer extends MessageServer {

    public EmailServer(String name, String domain) {
        super(name, domain);
        internet.registerDomain(domain, new ServerInfo());
    }

    public Status register(String name, Credentials credentials) {
        if (!(credentials instanceof UsernamePassword)) {
            return new Status(403, "You can only connect using username/password");
        }
        return super.register(name, credentials);
    }
    @Override
    protected Message createSenderNotificationMessage(Message message, String reason) {
    EmailMessage note = (EmailMessage)super.createSenderNotificationMessage(message, reason);
	note.setMessage(note.getMessage() + "\n\n Inhalt der Nachricht:\n\n");
	note.setSubject("Fehler beim Versand der Email");
	note.setFrom("root@" + this.getDomain());
	return note;
    }

    @Override
    protected String getDomainForAddress(String name) {
        try{
            return name.split("@")[1];
        }
        catch(NullPointerException e) {
            return "";
        }
    }
    @Override
    protected ServerSocket getSocket(String name) {
        return new Socket(name);
    }

}
