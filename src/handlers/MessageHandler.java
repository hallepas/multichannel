package handlers;

import handlers.validators.Validator;

import message.Message;
import message.MessageType;
import exceptions.ValidationError;

/**
 * Der Message Handler kümmert sich um die Erstellung und Validierung von Nachrichten.
 */
public abstract class MessageHandler {

    private final Validator validator;

    protected MessageHandler(Validator validator) {
	this.validator = validator;
    }

    public abstract Message newMessage();

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

    /**
     * Wir können auch Strategy Pattern.
     * @param message
     * @throws ValidationError
     */
    public void validateMessage(Message message) throws ValidationError {
	this.validator.validateMessage(message);
    }

}
