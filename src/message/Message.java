package message;

import java.util.ArrayList;
import java.util.Date;
import org.omg.IOP.Encoding;

public abstract class Message<Host> {
	private String from; 
	
	private Date datetime; 
	private String message; 
	private Encoding encoding; 
	ArrayList<Host> forwardPath = new ArrayList<Host>(); 
	ArrayList<Host> reversePath = new ArrayList<Host>();
	
	
	ArrayList<String> list = new ArrayList<String>();
	
	public String getFrom() {
		return from;
	}
	
	public void setFrom(String from) {
		this.from = from;
	}
	
	public Date getDatetime() {
		return datetime;
	}
	
	public void setDate(Date datetime) {
		this.datetime = datetime;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public void deliverStatus() {
		// der Zustellstatus wird kontrolliert
	}
	
	public Encoding getEncoding() {
		return encoding;
	}
	
	public void setEncoding(Encoding encoding) {
		this.encoding = encoding;
	}
	
}