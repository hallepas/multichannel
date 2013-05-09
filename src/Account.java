 
public class Account extends MessageServer {
	private String address;
	private Object logincredentials; 
	private MessageServer server; 

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Object getLogincredentials() {
		return logincredentials;
	}

	public void setLogincredentials(Object logincredentials) {
		this.logincredentials = logincredentials;
	}

	public MessageServer getServer() {
		return server;
	}

	public void setServer(MessageServer server) {
		this.server = server;
	} 
	
}
