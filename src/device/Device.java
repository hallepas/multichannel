package device;

abstract public class Device {
	
	MessageClient client = new MessageClient();
	Sms sms = new Sms(); 
	Mms mms = new Mms();
	Email email = new Email();
	PrintJobMessage printJobMessage = new PrintJobMessage(); 
	
	
	public void openMailbox() {
		// die Mailbox wird geöffnet
	}
	
	public void printJobMessage() {
		// die Nachricht wird vom Drucker gedruckt
	}
	
	public void recieveNewSMSMessage() {
		// sms wird empfangen
	}
	
	public void recieveNewMMSMesssage() {
		//mms wird empfangen
	}
	
	public void recieveNewEmail() {
		//neue email wird empfangen
	}
	
}
