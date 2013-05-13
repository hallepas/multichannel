package devices;

public abstract class Device {
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


