package message;

import java.util.List;

import clients.ServerProxy;
import devices.Printer;
import exceptions.NoAccountException;
import exceptions.NoDeviceException;
import exceptions.ValidationError;

/**
 * Diese Klasse ist etwas spezieller. sendMessages führt direkt zu einem Printer
 * Proxy. ReceiveMessages macht hier keinen Sinn.
 * 
 */

public class PrintJobUserAgent extends UserAgent  {
	private ServerProxy printer;

	@Override
	public Message newMessage() {
		if(printer != null) {
			return new PrintJobMessage();
		} else {
			throw new NoDeviceException("Kein Drucker angeschlossen");
		}
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
	public Status sendMessage(Message message) {
		checkForAccount();
		return printer.put(message);
	}

	@Override  // Unless this is a scanner as well.
	public List<Message> receiveMessages() {
		return null;
	}
	
	public void checkForAccount() throws NoAccountException {
		if (printer == null) {
			throw new NoDeviceException("No printer connected.");
		}
	}

	public ServerProxy getPrinter() {
		return printer;
	}

	public void setPrinter(ServerProxy printer) {
		this.printer = printer;
	}
	
	public Status connect(Printer printer) {
		this.printer = printer.getProxy();
		return new Status(200, "printer connected");
	}
	
}