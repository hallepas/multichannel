package handlers.validators;

import java.util.Date;

import message.Message;
import exceptions.ValidationError;

public abstract class MessageValidator implements Validator {

    /**
     * Diese Methode stellt sicher, dass die Nachricht den Standards entspricht
     * und versendet werden kann.
     * 
     * @param message
     * @throws ValidationError
     */
    public void validateMessage(Message message) throws ValidationError {
        if (message.getDate() instanceof Date) {
            throw new ValidationError("Message has already been sent.");
        }
        if (message.getReminder() != null
                && message.getReminder().after(new Date())) {
            throw new ValidationError(
                    "Reminder is in the future Cannot send message.");
        }
    }
}
