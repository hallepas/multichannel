package message;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.junit.Test;

public class MessageTest {

    @Test
    public void test() {
        Message email = MessageType.EMAIL.instance();
        assertTrue(email instanceof EmailMessage);
        Message sms = MessageType.SMS.instance();
        assertTrue(sms instanceof SMSMessage);
        Message mms = MessageType.MMS.instance();
        assertTrue(mms instanceof MMSMessage);
        Message druck = MessageType.PRINT.instance();
        assertTrue(druck instanceof PrintJobMessage);
    }

    @Test
    public void testFileAttachment() {
        String path = "data" + File.separator + "test" + File.separator
                + "Attachment.dat";
        String dog = "data" + File.separator + "test" + File.separator
                + "Woof.gif";
        Writer fw = null;
        Attachment attachment = null;
        try {
            fw = new FileWriter(path);
            fw.write("Zwei Jäger treffen sich...");
            fw.append(System.getProperty("line.separator")); // e.g. "\n"
        } catch (IOException e) {
            System.err.println("Konnte Datei nicht erstellen");
        } finally {
            if (fw != null)
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        EmailMessage message = new EmailMessage();
        try {
            attachment = new Attachment(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        message.addAttachment(attachment);
        assertTrue("Message hat Attachment", message.hasAttachment());

        assertEquals("Attachment hat Namen", attachment.getFileName(),
                "Attachment.dat");
        // Datei löschen.
        new File(path).delete();

        assertTrue("Message hat Attachment", message.hasAttachment());
        File file = new File(path);
        // Die Datei existiert nicht mehr.
        assertFalse(file.exists());
        try {
            attachment.saveAttachment(file.getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertTrue(file.exists());
        FileReader fileReader;
        try {
            fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            assertEquals("Dateiinhalt überprüfen", reader.readLine(),
                    "Zwei Jäger treffen sich...");
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            fail("Datei konnte nicht gelesen werden");
        }
        file.delete();
        // Check with Images
        try {
            attachment = new Attachment(dog);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(attachment.getFileName(), "Woof.gif");
        File dogFile = new File(dog);
        assertEquals(dogFile.getPath(), dog);
        String dogPath = dogFile.getParent();
        dogFile.delete();
        assertFalse(dogFile.exists());
        try {
            attachment.saveAttachment(dogPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertTrue(dogFile.exists());

    }

}
