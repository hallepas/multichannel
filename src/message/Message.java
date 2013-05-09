package message;

public abstract class Message {
	private encoding;
	
	public void deliverStatus() {
		// der Zustellstatus wird kontrolliert
	}
	
	public String getEncoding() {
		return this.encoding;
	}
}
