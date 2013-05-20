package clients;

import exceptions.NoAccountException;
import message.Message;
import message.Status;
import message.UserAgent;


public class SMSUserAgent extends UserAgent{

	@Override
	public Message newMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validateMessage(Message message) throws ValidationError {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Status sendMessages() throws NoAccountException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Status receiveMessages() throws NoAccountException {
		// TODO Auto-generated method stub
		return null;
	}
}
	