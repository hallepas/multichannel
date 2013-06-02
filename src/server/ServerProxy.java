package server;

import message.Message;
import message.Status;
import clients.ClientProxy;
import clients.credentials.Credentials;

public interface ServerProxy {
    Status register(String name, Credentials credentials);
    ServerSocket login(String name, Credentials credentials, ClientProxy client);
    Status deliver(String name, Message message);
    void whosyourdaddy();
}
