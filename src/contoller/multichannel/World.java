package contoller.multichannel;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import message.*;
import clients.Account;
import clients.MessageClient;
import clients.credentials.IMEI;
import clients.credentials.UsernamePassword;
import devices.Computer;
import devices.Device;
import devices.FeaturePhone;
import devices.Printer;
import devices.Smartphone;
import server.EmailServer;
import server.MessageServer;
import server.MobileMessageServer;
import server.TheInternet;


/**
 * Diese Klasse erstellt eine Umgebung für die Präsentation
 * Annas Computer (mit Drucker), ihr Nokia und Charlies Black Berry.
 *
 */
public class World {
    private static final TheInternet internet = TheInternet.goOnline();
    private static final Random random = new Random();
    private MessageServer gmail;
    private MessageServer gmx;
    private MessageServer swisscom;
    private MessageServer sunrise;
    
    private Computer annasComputer;
    private Printer annasDrucker;
    private FeaturePhone annasNokia;
    private Smartphone charliesBlackberry;
    
    private final String annasEmail = "anna@gmail.com";
    private final String charliesEmail = "charlie@gmx.ch";
    private final String annasTelNr = "0791234567";
    private final String charliesTelNr = "0763332220";
    private Account annasEmailAccount;
    private Account charliesEmailAccount;
    private Account annasSwisscomAbo;
    private Account charliesSunriseAbo;

    public World() {
        internet.startOver(); // empty registry
        // Create servers
        gmail = new EmailServer("GMail", "gmail.com");
        gmx = new EmailServer("GMX", "gmx.ch");
        swisscom = new MobileMessageServer("Swisscom", "4179");
        sunrise = new MobileMessageServer("Sunrise", "4176");
        // Create clients
        annasComputer = new Computer("Annas Computer");
        annasDrucker = new Printer("Annas Drucker");
        annasComputer.connectPrinter(annasDrucker);
        annasNokia = new FeaturePhone("Annas Nokia");
        charliesBlackberry = new Smartphone("Charlies BlackBerry");
     
        // Create accounts     
        annasEmailAccount = new Account();
        annasEmailAccount.setAddress(annasEmail);
        annasEmailAccount.setServer("gmail.com");
        annasEmailAccount.setLoginCredentials(new UsernamePassword(annasEmail, "a99a"));
        annasComputer.openMailProgram().setAccountFor(MessageType.EMAIL, annasEmailAccount);
        gmail.register(annasEmail, annasEmailAccount.getLoginCredentials());
        annasComputer.openMailProgram().login();
        annasSwisscomAbo = new Account();
        annasSwisscomAbo.setAddress(annasTelNr);
        annasSwisscomAbo.setServer("4179");
        annasSwisscomAbo.setLoginCredentials(new IMEI(annasNokia.getImei()));
        annasNokia.getMessageClient().setAccountFor(MessageType.SMS, annasSwisscomAbo);
        annasNokia.getMessageClient().setAccountFor(MessageType.MMS, annasSwisscomAbo);
        swisscom.register(annasTelNr, new IMEI(annasNokia.getImei()));
        annasNokia.getMessageClient().login();
        
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
        MessageClient charliesBBMessanger = charliesBlackberry.getMessageClient();
        charliesBBMessanger.setAccountFor(MessageType.SMS, charliesSunriseAbo);
        charliesBBMessanger.setAccountFor(MessageType.MMS, charliesSunriseAbo);
        sunrise.register(charliesTelNr, new IMEI(charliesBlackberry.getImei()));
        charliesBlackberry.getMessageClient().login();
        assertTrue(sunrise.isUserLoggedIn(charliesTelNr));
        assertTrue(swisscom.isUserLoggedIn(annasTelNr));
        assertTrue(gmail.isUserLoggedIn(annasEmail));
        assertTrue(gmx.isUserLoggedIn(charliesEmail));
        
        // Nachrichten erstellen
        generateMessages(20, charliesEmail, charliesBBMessanger, MessageType.EMAIL);
        generateMessages(30, charliesTelNr, charliesBBMessanger, MessageType.SMS);
        generateMessages(10, charliesTelNr, charliesBBMessanger, MessageType.MMS);
        generateMessages(50, annasEmail, annasComputer.openMailProgram(), MessageType.EMAIL);
        generateMessages(40, annasTelNr, annasNokia.getMessageClient(), MessageType.SMS);
        generateMessages(10, annasTelNr, annasNokia.getMessageClient(), MessageType.MMS);
        
        // Nachricht mit Anhang erstellen
        EmailMessage message2 = (EmailMessage)annasComputer.openMailProgram().newMessage(MessageType.EMAIL);
        message2.setMessage(getText());
        message2.addRecipient(charliesEmail);
        Attachment attachment;
        String dog = "data" + File.separator + "test" + File.separator + "Woof.gif";
        try {
            attachment = new Attachment(dog);
            message2.addAttachment(attachment);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        message2.setSubject("Message mit Attachment");
        annasComputer.openMailProgram().submit(message2);
        
        
        
        // Nachrichten mit Remindern erstellen
        Message message = annasComputer.openMailProgram().newMessage(MessageType.EMAIL);
        message.setMessage(getText());
        message.addRecipient(getEmail(annasEmail));
        // 5s Reminder
        message.setReminder(new Date(new Date().getTime() + 1000*5));
        annasComputer.openMailProgram().saveDraft(message);
    }
    
    private void generateMessages(int count, String who, MessageClient client,
                                MessageType type) {
        for(int i=0; i<count; i++) {
            Message message = client.newMessage(type);
            message.setDate(getDate());
            message.addRecipient(who);
            if (type == MessageType.EMAIL || type == MessageType.MMS) {
                ((MessageWithSubjectAndAttachment) message).setSubject(getSubject());
            }
            if (type == MessageType.EMAIL){
                message.setFrom(getEmail(who));
            } else if (type == MessageType.SMS || type == MessageType.MMS) {
                message.setFrom(getPhoneNumber(who));
            }
            if(type != MessageType.SMS) {
                message.setMessage(getText());
            } else {
                message.setMessage(getText().substring(0, 150));
            }
            client.addToInbox(message);
        }
    }
    
    public List<Device> getDevices(){
        ArrayList<Device> devices = new ArrayList<Device>();
        devices.add(annasComputer);
        devices.add(annasNokia);
        devices.add(charliesBlackberry);
        return devices;
    }
    
    private String getSubject(){
        List<String> subjects = new ArrayList<String>();
        
        subjects.add("Sie haben gewonnen!");
        subjects.add("Nehmen Sie an unserer Umfrage teil!");
        subjects.add("Wartungsfenster von heute Donnerstag");
        subjects.add("Ihre Anfrage");
        subjects.add("Multichannel");
        subjects.add("Newsflash");
        subjects.add("Vielen Dank für Ihre Bestellung");
        subjects.add("Fotos vom letzten Wochenende");
        subjects.add("Einladung zum Mitarbeiteressen");
        subjects.add("Deine Deals des Tages");
        subjects.add("Unsere Aktionen der Woche");
        subjects.add("Payment Rejected");
        subjects.add("Make huge money today");
        
        return subjects.get(random.nextInt(subjects.size()));
    }
    
    private String getText() {
        List<String> text = new ArrayList<String>();
        
        text.add("Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet..");
        text.add("Sanctus sea sed takimata ut vero voluptua. Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis.");
        text.add("Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.");
        text.add("Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. At vero eos et accusam et justo duo dolores et ea rebum. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.");
        text.add("Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. At vero eos et accusam et justo duo dolores et ea rebum. Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua.");
        
        return text.get(random.nextInt(text.size()));
    }
    
    /**
     * Erzeugt ein zufälliges Datum
     * @return
     */
    private Date getDate(){
        long offset = Timestamp.valueOf("2011-01-01 00:00:00").getTime();
        long end = new Date().getTime();
        long diff = end - offset + 1;
        return new Date(offset + (long)(random.nextFloat() * diff));
    }
    
    private String getEmail(String without) {
        List<String> text = new ArrayList<String>();
        text.add(annasEmail);
        text.add(charliesEmail);
        text.add("baechsim@students.zhaw.ch");
        text.add("tomicmil@students.zhaw.ch");
        text.add("daniela@deindeal.ch");
        text.add("bert@gmail.com");
        text.add("test@domain.tld");       
        
        if(text.contains(without)) {
            text.remove(without);
        }
        return text.get(random.nextInt(text.size()));
    }
    
    private String getPhoneNumber(String without) {
        List<String> text = new ArrayList<String>();
        text.add(annasTelNr);
        text.add(charliesTelNr);
        text.add("0555348843");
        text.add("0794652211");
        text.add("0844123486");
        text.add("0775556487");
        text.add("0762215589");
        text.add("0798441257");
        
        if(text.contains(without)) {
            text.remove(without);
        }
        return text.get(random.nextInt(text.size()));
    }
    
    

}
