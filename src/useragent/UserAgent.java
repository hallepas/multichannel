package useragent;

import java.util.ArrayList;

public class UserAgent<Message> {
	private ArrayList<Message> recieveMessages = new ArrayList<Message>();
	private ArrayList<Message> syncMessages = new ArrayList<Message>();

	public ArrayList<Message> getRecieveMessages() {
		return recieveMessages;
	}

	public void setRecieveMessages(ArrayList<Message> recieveMessages) {
		this.recieveMessages = recieveMessages;
	}

	public ArrayList<Message> getSyncMessages() {
		return syncMessages;
	}

	public void setSyncMessages(ArrayList<Message> syncMessages) {
		this.syncMessages = syncMessages;
	}
	public void sendMessages() {
		
	}

}
