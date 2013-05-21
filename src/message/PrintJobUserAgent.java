package message;

import java.util.List;

import clients.ServerProxy;
import clients.ValidationError;
import devices.Printer;
import exceptions.NoAccountException;

/**
 * Diese Klasse ist etwas spezieller. sendMessages führt direkt zu einem Printer
 * Proxy. ReceiveMessages macht hier keinen Sinn.
 * 
 */

public class PrintJobUserAgent extends UserAgent  {
	private ServerProxy printer;

	@Override
	public Message newMessage() {
		return new PrintJobMessage();
	}

	@Override
	public void validateMessage(Message message) throws ValidationError {
		if(message.getMessage() == "") {
			throw new ValidationError("Message Text empty");
		} 
		if (!(message instanceof PrintJobMessage)){
			throw new ValidationError("Message is not a print message.");
		}
	}

	@Override
	public Status sendMessages(List<Message> messages) throws NoAccountException {
		checkForAccount();
		return printer.put(messages);
	}

	@Override  // Unless this is a scanner as well.
	public List<Message> receiveMessages() throws NoAccountException {
		return null;
	}
	
	public void checkForAccount() throws NoAccountException{
		if (printer == null) {
			throw new NoAccountException("No printer connected.");
		}
	}

	public ServerProxy getPrinter() {
		return printer;
	}

	public void setPrinter(ServerProxy printer) {
		this.printer = printer;
	}
	
	public void connect(Printer printer) {
		this.printer = printer.getProxy();
	}



}