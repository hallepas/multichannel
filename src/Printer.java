import useragent.PrintJobUserAgent;
import useragent.UserAgent;


public class Printer extends UserAgent<Object> {
	PrintJobUserAgent computer = new PrintJobUserAgent();
	
	public void printStatus() {
		// es wird gedruckt und der Druckstatus ausgegeben
	}
	
}
