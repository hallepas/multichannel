package message;
import java.util.ArrayList;
import java.util.Date;

import org.omg.IOP.Encoding;

public class Message {
	private String message; 
	private String from; 
	private String subject; 
	private Date date; 
	ArrayList<String> to = new ArrayList<String>();
	private Encoding encoding;
	
	public Message(String message, String from, String subject, Date date, ArrayList<String> to, Encoding encoding) {
		this.message = message;
		this.from = from;
		this.subject = subject;
		this.date = date;
		this.to = to;
		this.encoding = encoding;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Encoding getEncoding() {
		return encoding;
	}

	public void setEncoding(Encoding encoding) {
		this.encoding = encoding;
	}
	
	public void deliverStatus() {
		
	}
	
	public void encoding() {
		
	}
	
	public void setDateTimeReminder() {
		
	}
	
	public void getdateTimeReminder() {
		
	}
}
