package clients;

import message.MessageType;

public interface ClientProxy {
    // Vorläufig mal eines pro Message
    void newMessages(MessageType type, String server);
}
