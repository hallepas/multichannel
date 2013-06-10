package handlers.validators;

import java.util.regex.Pattern;

import message.Message;
import exceptions.ValidationError;

public class EmailValidator extends MessageValidator {
    private static final Pattern EMAIL = Pattern
            .compile("^[_A-Za-z0-9-\\+]+"
                    + "(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    private void checkValidEmail(String address) throws ValidationError {
        if (!(address.indexOf('@') > 0)) {
            throw new ValidationError("Email Adresse <" + address
                    + "> hat kein @ Symbol.");
        } else if (!EMAIL.matcher(address).matches()) {
            throw new ValidationError("Email Adresse <" + address
                    + "> hat falsches Format.");
        }
    }

    @Override
    public void validateMessage(Message message) throws ValidationError {
        super.validateMessage(message);
        for (String to : message.getTo()) {
            checkValidEmail(to);
        }
        checkValidEmail(message.getFrom());
    }

}
