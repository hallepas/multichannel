package devices;
import clients.MessageClient;
import clients.useragents.PrintJobUserAgent;
import message.*;


/**
 * Ein Computer kann Emails verarbeiten und drucken.
 *
 */
public class Computer extends Device {
    /**
     * Konstruktor weist Email und Print Funktion zu.
     */
    public Computer(String deviceName) {
	super(new MessageClient(new MessageType[]{MessageType.EMAIL,
		MessageType.PRINT}), deviceName);
    }

    public EmailMessage newEmail(){
	return (EmailMessage) newMessage(MessageType.EMAIL);
    }
    
    public PrintJobMessage newPrintJob(){
	return (PrintJobMessage) newMessage(MessageType.PRINT);
    }
    public Status connectPrinter(Printer printer) {
	return ((PrintJobUserAgent) getUserAgentFor(MessageType.PRINT))
		.connect(printer);
    }
    public void disconnectPrinter() {
	((PrintJobUserAgent) getUserAgentFor(MessageType.PRINT))
	.setPrinter(null);
    }
    public Status print(PrintJobMessage message) {
	return getUserAgentFor(MessageType.PRINT).sendMessage(message);
    }

}
