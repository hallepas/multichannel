package clients;

import server.ServerSocket;

public interface ClientProxy {
    void newMessages(ServerSocket server);
}
