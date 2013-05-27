package server;

import java.util.List;

import message.Message;
import message.Status;

public interface ServerProxy {
    List<Message> poll();
    Status put(Message message);
    String getServerName();
    Status logout();
}
