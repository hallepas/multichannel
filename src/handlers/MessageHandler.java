package handlers;

import java.util.Date;
import message.Message;
import message.MessageType;
import exceptions.ValidationError;

/**
 * Der Message Handler kümmert sich um die Erstellung und Validierung von Nachrichten.
 */
public abstract class MessageHandler {
	
	public abstract Message newMessage();
	
	/**
	 * Diese Methode stellt sicher, dass die Nachricht den Standards entspricht und
	 * versendet werden kann.
	 * @param message
	 * @throws ValidationError
	 */
	public void validateMessage(Message message) throws ValidationError {
		if (!(message.getDate() instanceof Date)) {
			throw new ValidationError("No date specified");
		}
		if (message.getReminder() != null && message.getDate().before(message.getReminder()) ){
			throw new ValidationError("Reminder cannot be set on a message that has already been sent.");
		}
	}
	/**
	 * Factory Methode um konkrete MessageHandlers für die entsprechenden
	 * Message-Typen zu erstellen.
	 * @param type
	 * @return Konkrete MessageHandler Instanz für den entsprechenden Typ.
	 */
	public static MessageHandler getHandlerForType(MessageType type){
		switch(type) {
		case SMS:
			return new SMSHandler();
		case MMS:
			return new MMSHandler();
		case EMAIL:
			return new EmailHandler();
		case PRINT:
			return new PrintJobHandler();
		}
		return null;
	}

}
