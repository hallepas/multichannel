package clients;

import clients.credentials.Credentials;


public class Account {
    private String address; 
    private String server; 
    private Credentials loginCredentials;

    public String getAddress() {
	return address;
    }
    public void setAddress(String address) {
	this.address = address;
    }
    public String getServer() {
	return server;
    }
    public void setServer(String server) {
	this.server = server;
    }
    public Credentials getLoginCredentials() {
	return loginCredentials;
    }
    public void setLoginCredentials(Credentials loginCredentials) {
	this.loginCredentials = loginCredentials;
    }
}
