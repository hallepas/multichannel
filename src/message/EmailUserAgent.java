package message;

import clients.ValidationError;
import exceptions.NoAccountException;

public class EmailUserAgent extends UserAgent {

	@Override
	public Message newMessage() {
		return new EmailMessage();
	}

	@Override
	public void validateMessage(Message message) throws ValidationError {
		// TODO Auto-generated method stub

	}

	@Override
	public Status sendMessages() throws NoAccountException {
		checkForAccount();
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Status receiveMessages() throws NoAccountException {
		checkForAccount();
		// TODO Auto-generated method stub
		return null;
	}
}