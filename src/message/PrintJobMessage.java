package message;

import java.util.ArrayList;
import java.util.Date;

import org.omg.IOP.Encoding;

public class PrintJobMessage<Host> extends Message {
	public PrintJobMessage(String message, String from, String subject, Date date, ArrayList<String> to, Encoding encoding) {
		super(message, from, subject, date, to, encoding);
		// TODO Auto-generated constructor stub
	}
	private Host fromPrinter; 
	private Host toPrinter; 
	private String queue; 
	
	public String getQueue() {
		return queue;
	}
	public void setQueue(String queue) {
		this.queue = queue;
	}
	
	public Host getToPrinter() {
		return toPrinter;
	}
	public void setToPrinter(Host toPrinter) {
		this.toPrinter = toPrinter;
	}
	public Host getFromPrinter() {
		return fromPrinter;
	}
	public void setFromPrinter(Host fromPrinter) {
		this.fromPrinter = fromPrinter;
	} 
}
