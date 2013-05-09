package message;

public class PrintJobMessage<Host> extends Message<Host> {
	private String queue; 
	private String message;
	private Host hostFrom; 
	private Host hostTo; 
	
	
	public String getQueue() {
		return queue;
	}
	
	public void setQueue(String queue) {
		this.queue = queue;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;	
	}
	
	public Host getHostFrom() {
		return hostFrom;
	}
	
	public void setHostFrom(Host hostFrom) {
		this.hostFrom = hostFrom;
	}
	
	public Host getHostTo() {
		return hostTo;
	}
	
	public void setHostTo(Host hostTo) {
		this.hostTo = hostTo;
	} 
}
