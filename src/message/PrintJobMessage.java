package message;

public class PrintJobMessage<Host> extends Message {
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
