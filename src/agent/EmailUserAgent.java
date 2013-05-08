package agent;
import java.util.*;

class EmailUserAgent extends UserAgent {
	EmailUserAgent emailUserAgent = new EmailUserAgent();
	Account imap = new Account(); //imapServer
	Account smtp = new Account(); //smtpServer
	
	emailUserAgent.sendMessage(Account imap); // Nachricht wird als imap versendet
	emailUserAgent.sendMessage(Account smtp); // Nachricht wird als smtp versendet
	
}
