package clients;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import message.*;

import org.junit.Before;
import org.junit.Test;

import devices.Printer;

public class ClientTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testMessageFilter() {
        EmailMessage email = new EmailMessage();
        email.setSubject("Test email");
        MMSMessage mms = new MMSMessage();
        mms.setSubject("Test mms");
        List<Message> messages = new ArrayList<Message>();
        messages.add(email);
        messages.add(mms);
        assertEquals(messages.size(), 2);
        List<Message> filtered = MessageClient.getOnlyType(messages,
                MessageType.EMAIL);
        assertTrue(filtered.contains(email));
        assertFalse(filtered.contains(mms));
    }

    @Test
    public void testMessageCreation() {
        MessageClient client = new MessageClient(new MessageType[] {
                MessageType.EMAIL, MessageType.PRINT });
        Message message = client.newMessage(MessageType.EMAIL);
        assertTrue("Email Message", message instanceof EmailMessage);
        message = client.newMessage(MessageType.PRINT);
        assertTrue("Print Job message", message instanceof PrintJobMessage);
        assertTrue(client.canPrint());
    }

    @Test
    public void testValidator() {
        MessageClient client = new MessageClient(new MessageType[] {
                MessageType.EMAIL, MessageType.PRINT, MessageType.SMS,
                MessageType.MMS });
        Message sms = client.newMessage(MessageType.SMS);
        sms.setFrom("0781234567");
        sms.addRecipient("0792221122");
        sms.setMessage("Wo bisch?");
        assertTrue(client.validateMessage(sms));
        sms.setFrom("1234567");
        assertFalse(client.validateMessage(sms));
        sms.setFrom("0781234567");
        sms.addRecipient("079222112233");
        assertFalse(client.validateMessage(sms));
        sms.removeRecipient("079222112233");
        sms.addRecipient("+41791234433");
        assertTrue(client.validateMessage(sms));
        sms.setMessage("Eine sehr lange Nachricht, die länger als die erlaubten 160 Zeichen ist."
                + "Darum sollte bei der Validierung ein Fehler geschmissen werden."
                + "Duis autem vel eum iriure dolor in hendrerit in vulputate velit "
                + "esse molestie consequat, vel illum dolore eu feugiat nulla facilisis "
                + "at vero eros et accumsan et iusto odio dignissim qui blandit praesent "
                + "luptatum zzril delenit augue duis dolore te feugait nulla facilisi.");
        assertTrue(sms.getMessage().length() > 160);
        assertFalse(client.validateMessage(sms));

        Message email = client.newMessage(MessageType.EMAIL);
        email.setFrom("test@gmail.com");
        email.addRecipient("baechsim@students.zhaw.ch");
        email.setMessage("Email test");
        assertTrue(client.validateMessage(email));
        email.setFrom("test");
        assertFalse(client.validateMessage(email));
        email.setFrom("@test");
        assertFalse(client.validateMessage(email));
        email.setFrom("test@aa");
        assertFalse(client.validateMessage(email));

    }

}
