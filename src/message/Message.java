package message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Die Message-Hauptklasse
 * 
 * 
 */
public abstract class Message implements Comparable<Message>, Serializable {
	private static final long serialVersionUID = 1L;
	private String from;
	private ArrayList<String> to = new ArrayList<String>();
	private String message;
	private Date date;
	private Date reminder;

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

	public ArrayList<String> getTo() {
		return to;
	}

	public void setTo(ArrayList<String> to) {
		this.to = to;
	}

	public void addRecipient(String recipient) {
		this.to.add(recipient);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getReminder() {
		return reminder;
	}

	@Override
	public int compareTo(Message other) {
		return this.date.compareTo(other.getDate());
	}

}