package MessageServer;

public interface NetworkClient {
	
	void goOnline();
	void openConnection(NetworkClient hostTo); 
	void closeConnection(NetworkClient clientTo);

}
