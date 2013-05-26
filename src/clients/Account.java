package clients;

import server.MessageServer;
import client.credentials.Credentials;


public class Account {
    private String address; 
    private MessageServer server; 
    private Credentials loginCredentials;

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
    public Credentials getLoginCredentials() {
	return loginCredentials;
    }
    public void setLoginCredentials(Credentials loginCredentials) {
	this.loginCredentials = loginCredentials;
    }
}
