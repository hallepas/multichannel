package clients;

import message.Message;
import message.PrintJobMessage;
import message.Status;
import exceptions.NoAccountException;


/**
 * Diese Klasse ist etwas spezieller. Der Account führt direkt zu
 * einer Printer Instanz. ReceiveMessages macht hier keinen Sinn.
 * 
 */

public class PrintJobUserAgent extends UserAgent{

	@Override
	public Message newMessage() {
		return new PrintJobMessage();
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
		return null;
	}
}
