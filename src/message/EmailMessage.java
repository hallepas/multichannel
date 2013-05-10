package message;
import java.util.*;

public class EmailMessage<Host> extends Message{
	ArrayList<String> to = new ArrayList<String>(); 
	ArrayList<Host> forwardPath = new ArrayList<Host>(); 
	ArrayList<Host> reversePath = new ArrayList<Host>();

}
