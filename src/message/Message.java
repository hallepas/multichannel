package message;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import org.omg.IOP.Encoding;

/**
 * Die Message-Hauptklasse
 * 
 *
 */

public abstract class Message implements Comparable<Message>, Serializable {
	private String message; 
	private String from; 
	private String subject; 
	private Date date; 
	private Date reminder;
	private ArrayList<String> to = new ArrayList<String>();
	private Encoding encoding;

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
	
	
	public Date getReminder() {
		return reminder;
	}
	
	@Override
	public int compareTo(Message other) {
		return this.date.compareTo(other.getDate());
	}

	
}
