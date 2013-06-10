package clients;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import message.Message;

/**
 * Die Mailbox enthält Nachrichten. Sie wird sowohl im MessageClient als auch im
 * MessageServer verwendet. Ausserdem ist sie serialisierbar, so dass
 * Nachrichten persistent sind.
 * 
 */
public class Mailbox extends Observable implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Message> messages = new CopyOnWriteArrayList<Message>();

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void add(Message message) {
        setChanged();
        messages.add(message);
        notifyObservers("added");
    }

    /**
     * Überschreibt eine Nachricht, falls bereits vorhanden.
     * 
     * @param message
     *            Nachricht
     */
    public void put(Message message) {
        if (this.messages.contains(message)) {
            this.messages.remove(message);
            setChanged();
            this.messages.add(message);
            notifyObservers("replaced");
        } else {
            this.add(message);
        }
    }

    public void clear() {
        this.messages.clear();
    }

    public void sort() {
        setChanged();
        Collections.sort(messages);
        notifyObservers("sorted");
    }

    public long messageCount() {
        return this.messages.size();
    }

    public void deleteMessage(Message message) {
        try {
            messages.remove(message);
            setChanged();
            notifyObservers("deleted");
        } catch (NullPointerException e) {
        }
    }

}
