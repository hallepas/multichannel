import static org.junit.Assert.*;

import java.util.List;

import message.EmailMessage;
import message.Message;
import message.MessageType;
import message.PrintJobMessage;
import message.Status;

import org.junit.Test;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import server.EmailServer;
import server.MessageServer;
import server.ServerProxy;

import clients.*;
import clients.credentials.Credentials;
import clients.credentials.UsernamePassword;

import devices.*;
import exceptions.NoAccountException;


public class IntegrationTest {
    private Computer annasComputer;
    private Smartphone annasIPhone;
    private Printer annasDrucker;
    private Computer bertsComputer;
    private FeaturePhone bertsNokia;
    private MessageServer gmail;
    private final String annasEmail = "anna@gmail.com";
    private final String bertsEmail = "bert@gmail.com";
    private Account annasEmailAccount;
    private Account bertsEmailAccount;


    //@Rule 
    //public JUnitRuleMockery context = new JUnitRuleMockery();

    @Before
    public void setUp() throws Exception {
	annasComputer = new Computer("Annas Computer");
	annasIPhone = new Smartphone("Annas iPhone");
	annasDrucker = new Printer("Annas Drucker");
	bertsComputer = new Computer("Berts Computer");
	bertsNokia = new FeaturePhone("Berts Nokia 6150");
	gmail = new EmailServer("GMail", "gmail.com");

	annasEmailAccount = new Account();
	annasEmailAccount.setAddress(annasEmail);
	annasEmailAccount.setServer(gmail);
	annasEmailAccount.setLoginCredentials(new UsernamePassword(annasEmail, "a99a"));
	annasComputer.openMailProgram().setAccountFor(MessageType.EMAIL, annasEmailAccount);
	annasIPhone.openMailProgram().setAccountFor(MessageType.EMAIL, annasEmailAccount);
	gmail.register(annasEmail, annasEmailAccount.getLoginCredentials());
	
	bertsEmailAccount = new Account();
	bertsEmailAccount.setAddress(bertsEmail);
	bertsEmailAccount.setServer(gmail);
	bertsEmailAccount.setLoginCredentials(new UsernamePassword(bertsEmail, "b11b"));
	bertsComputer.openMailProgram().setAccountFor(MessageType.EMAIL, bertsEmailAccount);
	gmail.register(bertsEmail, bertsEmailAccount.getLoginCredentials());


    }

    @Test
    public void testRegisterAtServer(){
        gmail.unregister(annasEmail, annasEmailAccount.getLoginCredentials());
	Status status = gmail.register(annasEmail, new UsernamePassword(annasEmail, "a99a"));
	assertEquals("Register Anna", status.getCode(), 200);
	assertTrue("Anna is registered at Google", gmail.doesAccountExist(annasEmail));
    }

    @Test
    public void testLogin(){
	Credentials credentials = new UsernamePassword(annasEmail, "a99a");
	// Actually use the same object
	assertTrue(gmail instanceof MessageServer);
	gmail.register(annasEmail, credentials);
	ServerProxy proxy = gmail.login(annasEmail, credentials);
	assertNotNull("Login funktioniert", proxy);
	assertTrue("Benutzer ist eingeloggt", gmail.isUserLoggedIn(annasEmail));
	Status status = gmail.logout(annasEmail);
	assertEquals("Logout Anna", status.getCode(), 200);
	assertFalse("Benutzer ist nicht mehr eingeloggt", gmail.isUserLoggedIn(annasEmail));
	List<Message> poll = proxy.poll();
	assertNull("Ausgeloggte Benutzer dürfen nicht mehr Nachrichten empfangen", poll);
	status = proxy.put(new EmailMessage());
	assertTrue("Ausgeloggte Benutzer dürfen nicht mehr Nachrichten senden", status.getCode() != 200);
	// Test using a different credentials object
	proxy = gmail.login(annasEmail, new UsernamePassword(annasEmail, "a99a"));
	assertNotNull("Login funktioniert", proxy);
	assertTrue("Benutzer ist eingeloggt", gmail.isUserLoggedIn(annasEmail));
	gmail.logout(annasEmail);
	// wrong password
	proxy = gmail.login(annasEmail, new UsernamePassword(annasEmail, "a11a"));
	assertNull("Login funktioniert", proxy);
    }

    @Test
    public void testSendMessageWithoutReminder() {
        MessageClient outlook = annasComputer.openMailProgram();
        EmailMessage email = annasComputer.newEmail();
        assertEquals("Email von ist gesetzt", email.getFrom(), annasEmailAccount.getAddress());
        email.addRecipient(bertsEmail);
        email.setSubject("Email Test");
        email.setMessage("Dies ist ein Test");
        outlook.submit(email);
        assertTrue("Message ist auf dem Server", gmail.getMessagesForUser(bertsEmail).contains(email));
//        MessageClient thunderbird = bertsComputer.openMailProgram();
//        List<Message> messages = thunderbird.getMessagesFromInbox();
//        assertTrue("Mail ist angekommen", messages.contains(email));
//        messages = thunderbird.getUnreadMessages();
//        assertTrue("Mail ist noch nicht gelesen", messages.contains(email));
        // fail();  // TODO: Rest
    }

    @Test
    public void testPrintMessage() {
	assertTrue(annasDrucker instanceof Printer);
	assertTrue(annasComputer instanceof Computer);

	Status status = annasComputer.connectPrinter(annasDrucker);
	assertEquals("Drucker angeschlossen", status.getCode(), 200);
	PrintJobMessage message = annasComputer.newPrintJob();
	message.setMessage("Dies ist ein Print Test");
	status = annasComputer.print(message);
	assertEquals("Nachricht ausgedruckt", status.getCode(), 200);
	assertEquals(status.getDescription(), "printed");
    }

}
