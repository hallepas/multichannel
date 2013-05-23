package clients;

import java.util.List;

import exceptions.NoAccountException;
import exceptions.ValidationError;
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
	