package clients;

import server.ServerProxy;

public interface ClientProxy {
    void newMessages(ServerProxy server);
}
