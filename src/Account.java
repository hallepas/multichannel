
public class Account {
	private String address; 
	private MessageServer server; 
	private Object loginCredentials;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public MessageServer getServer() {
		return server;
	}
	public void setServer(MessageServer server) {
		this.server = server;
	}
	public Object getLoginCredentials() {
		return loginCredentials;
	}
	public void setLoginCredentials(Object loginCredentials) {
		this.loginCredentials = loginCredentials;
	} 
}
