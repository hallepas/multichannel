package message;

import java.util.List;

import exceptions.NoAccountException;
import exceptions.ValidationError;

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
	public Status sendMessage(Message message) throws NoAccountException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> receiveMessages() throws NoAccountException {
		// TODO Auto-generated method stub
		return null;
	}
}