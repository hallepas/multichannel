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
    
    public Status print(PrintJobMessage message) {
        return newUserAgentFor(MessageType.PRINT).sendMessage(message);
    }
    
    public Status printMessage(Message message){
        PrintJobMessage printJob = newPrintJob();
        printJob.setMessage(message.getMessage());
        printJob.setFrom(message.getFrom());
        printJob.setTo(message.getTo());
        printJob.setDate(message.getDate());
        return print(printJob);
    }
    
    public Status connectPrinter(Printer printer) {
	return ((PrintJobUserAgent) newUserAgentFor(MessageType.PRINT))
		.connect(printer);
    }
    public void disconnectPrinter() {
	((PrintJobUserAgent) newUserAgentFor(MessageType.PRINT))
	.setPrinter(null);
    }


}
