import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import message.EmailMessage;
import message.Message;
import message.MessageType;
import message.PrintJobMessage;
import message.SMSMessage;
import message.Status;

import org.junit.Test;
import org.junit.Before;

import server.EmailServer;
import server.MessageServer;
import server.MobileMessageServer;
import server.ServerProxy;
import server.ServerSocket;
import server.TheInternet;

import clients.*;
import clients.credentials.IMEI;
import clients.credentials.UsernamePassword;
import clients.useragents.CellphoneUserAgent;
import clients.useragents.EmailUserAgent;
import clients.useragents.UserAgent;

import devices.*;


public class IntegrationTest {
   
    private final String annasEmail = "anna@gmail.com";
    private final String bertsEmail = "bert@gmail.com";
    private final String charliesEmail = "charlie@gmx.ch";
    private final String annasTelNr = "0791234567";
    private final String charliesTelNr = "0763332220";

    private static final Logger log = Logger.getLogger( IntegrationTest.class.getName() );
    private static final TheInternet internet = TheInternet.goOnline();
    
    private static MessageServer gmail;
    private static MessageServer gmx;
    private static MessageServer swisscom;
    private static MessageServer sunrise;
    
    private static final Computer annasComputer = new Computer("Annas Computer");
    private static final Smartphone annasIPhone = new Smartphone("Annas iPhone");
    private static final Printer annasDrucker = new Printer("Annas Drucker");
    private static final Computer bertsComputer = new Computer("Berts Computer");
    private static final FeaturePhone bertsNokia = new FeaturePhone("Berts Nokia 6150");
    private static final Smartphone charliesBlackberry = new Smartphone("Charlies BlackBerry");

    private Account annasEmailAccount;
    private Account annasSwisscomAbo;
    private Account bertsEmailAccount;
    private Account charliesEmailAccount;
    private Account charliesSunriseAbo;

    public IntegrationTest() {
        super();
        System.setProperty( "java.util.logging.config.file", "logging.properties" );
        try { LogManager.getLogManager().readConfiguration(); }
        catch ( Exception e ) { e.printStackTrace(); }
        internet.startOver();
        gmail = new EmailServer("GMail", "gmail.com");
        gmx = new EmailServer("GMX", "gmx.ch");
        swisscom = new MobileMessageServer("Swisscom", "4179");
        sunrise = new MobileMessageServer("Sunrise", "4176");
        
        annasEmailAccount = new Account();
        annasEmailAccount.setAddress(annasEmail);
        annasEmailAccount.setServer("gmail.com");
        annasEmailAccount.setLoginCredentials(new UsernamePassword(annasEmail, "a99a"));
        annasComputer.openMailProgram().setAccountFor(MessageType.EMAIL, annasEmailAccount);
        annasIPhone.openMailProgram().setAccountFor(MessageType.EMAIL, annasEmailAccount);
        gmail.register(annasEmail, annasEmailAccount.getLoginCredentials());
        
        annasSwisscomAbo = new Account();
        annasSwisscomAbo.setAddress(annasTelNr);
        annasSwisscomAbo.setServer("4179");
        annasSwisscomAbo.setLoginCredentials(new IMEI(annasIPhone.getImei()));
        annasIPhone.getMessageClient().setAccountFor(MessageType.SMS, annasSwisscomAbo);
        annasIPhone.getMessageClient().setAccountFor(MessageType.MMS, annasSwisscomAbo);
        swisscom.register(annasTelNr, new IMEI(annasIPhone.getImei()));

        
        bertsEmailAccount = new Account();
        bertsEmailAccount.setAddress(bertsEmail);
        bertsEmailAccount.setServer("gmail.com");
        bertsEmailAccount.setLoginCredentials(new UsernamePassword(bertsEmail, "b11b"));
        bertsComputer.openMailProgram().setAccountFor(MessageType.EMAIL, bertsEmailAccount);
        gmail.register(bertsEmail, bertsEmailAccount.getLoginCredentials());
        
        charliesEmailAccount = new Account();
        charliesEmailAccount.setAddress(charliesEmail);
        charliesEmailAccount.setServer("gmx.ch");
        charliesEmailAccount.setLoginCredentials(new UsernamePassword(charliesEmail, "bb10"));
        charliesBlackberry.openMailProgram().setAccountFor(MessageType.EMAIL, charliesEmailAccount);
        gmx.register(charliesEmail,  charliesEmailAccount.getLoginCredentials());
        
        charliesSunriseAbo = new Account();
        charliesSunriseAbo.setAddress(charliesTelNr);
        charliesSunriseAbo.setServer("4176");
        charliesSunriseAbo.setLoginCredentials(new IMEI(charliesBlackberry.getImei()));
        charliesBlackberry.getMessageClient().setAccountFor(MessageType.SMS, charliesSunriseAbo);
        charliesBlackberry.getMessageClient().setAccountFor(MessageType.MMS, charliesSunriseAbo);
        sunrise.register(charliesTelNr, new IMEI(charliesBlackberry.getImei()));
        
    }

    //@Rule 
    //public JUnitRuleMockery context = new JUnitRuleMockery();

    @Before
    public void setUp() throws Exception {
	
    }
    
//    @Test
//    public void TestLogging(){
//        log.fine("Test Logging");
//        log.warning("Warning Test");
//        log.info("Log Info");
//    }

    @Test
    public synchronized void testRegisterAtServer(){
	Status status = gmail.register("dave@gmail.com", new UsernamePassword("dave@gmail.com", "a99a"));
	assertEquals("Register Dave", status.getCode(), 200);
	assertTrue("Dave is registered at Google", gmail.doesAccountExist("dave@gmail.com"));
	
	assertTrue("Anna ist bei Gmail", gmail.doesAccountExist(annasEmail));
	assertTrue("Charlie ist bei GMX", gmx.doesAccountExist(charliesEmail));
	assertTrue("Anna ist bei Swisscom", swisscom.doesAccountExist(annasTelNr));
	assertTrue("Charlie ist bei Sunrise", sunrise.doesAccountExist(charliesTelNr));
    }

    @Test
    public synchronized void testLogin(){
	UserAgent ua = new EmailUserAgent();
	ua.setAccount(annasEmailAccount);
	Status status = ua.login();
	assertEquals("Login funktioniert", status.getCode(), 200);
	assertTrue(gmail instanceof MessageServer);
	assertTrue("Benutzer ist eingeloggt", gmail.isUserLoggedIn(annasEmail));
	
	status = ua.logout();
	assertEquals("Logout Anna", status.getCode(), 200);
	assertFalse("Benutzer ist nicht mehr eingeloggt", gmail.isUserLoggedIn(annasEmail));
	List<Message> poll = ua.receiveMessages();
	assertNull("Ausgeloggte Benutzer dürfen nicht mehr Nachrichten empfangen", poll);
	status = ua.sendMessage(new EmailMessage());
	assertTrue("Ausgeloggte Benutzer dürfen nicht mehr Nachrichten senden", status.getCode() != 200);

	// wrong password
	ServerProxy proxy = internet.lookup("gmail.com");
	ServerSocket socket = proxy.login(annasEmail, new UsernamePassword(annasEmail, "a11a"), null);
	assertNull("Login funktioniert", socket);
	
	// Cellphone

	ua = new CellphoneUserAgent();
	ua.setAccount(charliesSunriseAbo);
	status = ua.login();
	assertEquals("Login funktioniert", status.getCode(), 200);
	assertTrue("Benutzer ist eingeloggt", sunrise.isUserLoggedIn(charliesTelNr));
	ua.logout();
    }

    @Test
    public synchronized void testSendMessageWithoutReminder() {
        Date date = new Date();
        MessageClient outlook = annasComputer.openMailProgram();
        outlook.login();
        EmailMessage email = annasComputer.newEmail();
        assertEquals("Email von ist gesetzt", email.getFrom(), annasEmailAccount.getAddress());
        email.addRecipient(bertsEmail);
        email.setSubject("Email Test");
        email.setMessage("Dies ist ein Test");
        outlook.submit(email);
        
        assertTrue("Message ist auf dem Server", gmail.getMessagesForUser(bertsEmail).contains(email));
    
        MessageClient thunderbird = bertsComputer.openMailProgram();
        thunderbird.login();
        
        List<Message> messages = thunderbird.getMessagesFromInbox();
        assertTrue("Mail ist angekommen", messages.contains(email));
        messages = thunderbird.getUnreadMessages();
        assertTrue("Mail ist noch nicht gelesen", messages.contains(email));
        assertTrue("Mail hat Datum", email.getDate() != null);
        assertTrue("Mail Datum ist korrekt", email.getDate().after(date));
        date = new Date(new Date().getTime() + 10);
        assertTrue(email.getDate().before(date));
        outlook.logout();
        thunderbird.logout();
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
    
    @Test
    public synchronized void testSendEmailAcrossDomains() {
        MessageClient outlook = annasComputer.openMailProgram();
        MessageClient bbMail = charliesBlackberry.openMailProgram();
        bbMail.logout();
        outlook.login();
        EmailMessage email = annasComputer.newEmail();
        assertEquals("Email von ist gesetzt", email.getFrom(), annasEmailAccount.getAddress());
        email.addRecipient(charliesEmail);
        email.setSubject("Email Test");
        email.setMessage("Dies ist ein Test");
        outlook.submit(email);
        assertTrue("Message ist auf dem Server", gmx.getMessagesForUser(charliesEmail).contains(email));
        bbMail.login();
        List<Message> messages = bbMail.getMessagesFromInbox();
        assertTrue("Mail ist angekommen", messages.contains(email));
        messages = bbMail.getUnreadMessages();
        assertTrue("Mail ist noch nicht gelesen", messages.contains(email));
        outlook.logout();
    }
    
    @Test
    public synchronized void sendSMSMessage() {
        MessageClient iChat = annasIPhone.openMailProgram();
        iChat.login();
        MessageClient bbMail = charliesBlackberry.openMailProgram();
        bbMail.login();
        
        SMSMessage sms = annasIPhone.newSMS();
        sms.addRecipient(charliesTelNr);
        sms.setMessage("Wo bisch?");
        iChat.submit(sms);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //assertTrue("Message ist auf dem Server", sunrise.getMessagesForUser(charliesTelNr).contains(sms));
        // SMS ist push
        List<Message> messages = bbMail.getMessagesFromInbox();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue("SMS ist angekommen", messages.contains(sms));
        iChat.logout();
        bbMail.logout();
    }

}
