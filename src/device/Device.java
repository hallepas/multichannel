package device;

abstract public class Device<MessageClient, Sms, Mms, Email, PrintJobMessage> {
	
	private MessageClient client; 
	private Sms sms; 
	private Mms mms; 
	private Email email; 
	private PrintJobMessage printJobMessage;
	
	public MessageClient getClient() {
		return client;
	}
	public void setClient(MessageClient client) {
		this.client = client;
	}
	public Sms getSms() {
		return sms;
	}
	public void setSms(Sms sms) {
		this.sms = sms;
	}
	public Mms getMms() {
		return mms;
	}
	public void setMms(Mms mms) {
		this.mms = mms;
	}
	public Email getEmail() {
		return email;
	}
	public void setEmail(Email email) {
		this.email = email;
	}
	public PrintJobMessage getPrintJobMessage() {
		return printJobMessage;
	}
	public void setPrintJobMessage(PrintJobMessage printJobMessage) {
		this.printJobMessage = printJobMessage;
	} 
	
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
