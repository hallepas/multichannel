package message;

import java.util.List;

import clients.ValidationError;
import exceptions.NoAccountException;

public class MMSUserAgent extends UserAgent {

	@Override
	public Message newMessage() {
		return new MMSMessage();
	}

	@Override
	public void validateMessage(Message message) throws ValidationError {
		// TODO Auto-generated method stub

	}

	@Override
	public Status sendMessages(List<Message> messages) throws NoAccountException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> receiveMessages() throws NoAccountException {
		// TODO Auto-generated method stub
		return null;
	}

}
