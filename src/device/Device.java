package device;

public abstract class Device<MessageClient> {
	private MessageClient messageclient; 
	
	public void newSMS() {
		
	}
	
	public void newMMS() {
		
	}
	
	public void openMailbox() {
		
	}

	public MessageClient getMessageclient() {
		return messageclient;
	}

	public void setMessageclient(MessageClient messageclient) {
		this.messageclient = messageclient;
	}
	
}


