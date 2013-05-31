package server;

import java.util.List;

import clients.ClientProxy;
import clients.credentials.Credentials;

import message.Message;
import message.Status;

public interface ServerSocket {
    List<Message> poll();
    Status put(Message message);
    String getServerName();
    Status logout();
}
