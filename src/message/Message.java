package message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Die Message-Hauptklasse. Möglichst keine Funktionalität hier.
 * 
 */
public abstract class Message implements Comparable<Message>, Serializable {
    private static final long serialVersionUID = 1L;
    private String from;
    private ArrayList<String> to = new ArrayList<String>();
    private String message;
    private Date date;
    private Date reminder;

    public Message() {
        super();
        this.from = "";
        this.to = new ArrayList<String>();
        this.message = "";
        this.date = null;
        this.reminder = null;
    }

    public abstract MessageType getType();

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

    public void removeRecipient(String recipient) {
        this.to.remove(recipient);
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

    public void setReminder(Date reminder) {
        this.reminder = reminder;
    }

    @Override
    public int compareTo(Message other) {
        if (this.date != null && other.getDate() != null) {
            return this.date.compareTo(other.getDate());
        } else {
            return 0;
        }

    }

    @Override
    public String toString() {
        return "Message from " + from + "to " + to;
    }

}