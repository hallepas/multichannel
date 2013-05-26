package server;

import clients.credentials.Credentials;
import clients.credentials.UsernamePassword;
import message.Status;

public class EmailServer extends MessageServer {

    public EmailServer(String name, String domain) {
        super(name, domain);
    }

    public Status register(String name, Credentials credentials) {
        if (!(credentials instanceof UsernamePassword)) {
            return new Status(403, "You can only connect using username/password");
        }
        return super.register(name, credentials);
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

}
