package clients;

import exceptions.NoAccountException;
import message.EmailMessage;
import message.Message;
import message.Status;


public class EmailUserAgent extends UserAgent{

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
