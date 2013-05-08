package agent;
import java.util.*;

class PrintJobUserAgent extends UserAgent {
	PrintJobUserAgent printJobAgent = new PrintJobUserAgent();
	Printer printer; 
	
	printJobAgent.sendMessage(Printer printer); //Druckauftrag wird versendet
}
