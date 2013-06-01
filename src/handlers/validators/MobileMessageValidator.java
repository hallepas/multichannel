package handlers.validators;

import java.util.regex.Pattern;

import message.Message;
import exceptions.ValidationError;

public abstract class MobileMessageValidator extends MessageValidator {
    // Wir beschränken uns mal auf zehnstellige Telefonnummern.
    private static final Pattern INTERNATIONAL = Pattern.compile("^\\+?([1-9]\\d{3})\\d{7}$");
    private static final Pattern NATIONAL = Pattern.compile("^0([1-9]\\d)\\d{7}$");

    private boolean validateNumber(String number){
        if(INTERNATIONAL.matcher(number).matches()) return true;
        else if (NATIONAL.matcher(number).matches()) return true;
        else return false;
    }
    
    @Override
    public void validateMessage(Message message) throws ValidationError {
        if (!validateNumber(message.getFrom())) {
            throw new ValidationError("Absender Nummer hat falsches Format.");
        }
        for(String number : message.getTo()){
            if(!validateNumber(number)) {
                throw new ValidationError("Empfänger " + number + " hat falsches Format.");
            }
        }
        super.validateMessage(message);    
    }   
}
