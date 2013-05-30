package gui.main;

import gui.components.LoginFrame;
import gui.components.MainFrame;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import javax.swing.UIManager;

import clients.Account;
import clients.MessageClient;
import clients.useragents.UserAgent;

import message.Attachment;
import message.EmailMessage;
import message.Message;
import message.MessageType;
import message.SMSMessage;
import devices.Computer;
import devices.FeaturePhone;
import devices.Smartphone;

public class GuiMain {

	// TODO Diese Klasse ist nur zum testen der Gui Klassen. Package und Klasse
	// wieder löschen

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		 new LoginFrame().setVisible(true);
		computer();
		smpartphone();
	}

	public static void messagesTEst() {
		Smartphone f = new Smartphone("Iphone");
		MessageClient mc = f.getMessageClient();

		ArrayList<Message> messages = new ArrayList<Message>();
		ArrayList<String> to = new ArrayList<>();
		to.add("0791112334");
		mc.addToInbox(new SMSMessage("Hallo ich wollte nur fragen...", "0781234567", "Betreff", new Date(), to, null));
		mc.addToInbox(new SMSMessage(
				"Lorem ipsum dolor sit amet, consetetur sadipscing elitr,\n sed diam nonumy eirmod tempor invidunt ut\n labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et ac\ncusam et justo duo dolores et ea rebum. Stet clita kasd gubergr\nn, no sea takimata sanct\ns est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. \n\nAt vero eos et accusam et justo duo dolores et \n\n\nea rebum. Stet clita kasd gubergren, no sea takimata \nsanctus est Lorem ipsum dolor sit amet.",
				"0781234567", "Betreff", new Date(), to, null));
		mc.addToInbox(new SMSMessage("Test Tset", "0781234567", "Betreff", new Date(), to, null));
		mc.addToInbox(new SMSMessage("Einladung zur", "0781234567", "Betreff", new Date(), to, null));
		mc.addToInbox(new SMSMessage("Hallo ich wollte nur fragen...", "0781234567", "Betreff", new Date(), to, null));
		mc.addToInbox(new SMSMessage("Einladung zur", "0781234567", "Betreff", new Date(), to, null));
		mc.addToInbox(new SMSMessage(
				"Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed \ndiam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo do\nlores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est .",
				"0781234567", "Betreff", new Date(), to, null));
		mc.addToInbox(new SMSMessage("Einladung zur", "0781234567", "Betreff", new Date(), to, null));
		mc.addToInbox(new SMSMessage("Hallo ich wollte nur fragen...", "0781234567", "Betreff", new Date(), to, null));
		mc.addToInbox(new SMSMessage("Herzliche Glückwunsch", "0781234567", "Betreff", new Date(), to, null));
		mc.addToInbox(new SMSMessage("Hallo ich wollte nur fragen...", "0781234567", "Betreff", new Date(), to, null));
		mc.addToInbox(new SMSMessage("Einladung zur", "0781234567", "Betreff", new Date(), to, null));

		EmailMessage em = new EmailMessage();
		em.setDate(new Date());
		em.setFrom("from");
		em.setMessage("");
		em.setSubject("Email");
		em.setTo(to);
		mc.addToInbox(em);

		MainFrame mainFrame = new MainFrame(MessageType.EMAIL, f);
		mainFrame.setVisible(true);
	}

	public static void smpartphone() {
		Smartphone f = new Smartphone("IPhone");
		MessageClient mc = f.getMessageClient();

		ArrayList<String> to = new ArrayList<>();
		to.add("0791112334");
		mc.addToInbox(new SMSMessage("Hallo ich wollte nur fragen...", "0781234567", "Betreff", new Date(), to, null));
		mc.addToInbox(new SMSMessage(
				"Lorem ipsum dolor sit amet, consetetur sadipscing elitr,\n sed diam nonumy eirmod tempor invidunt ut\n labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et ac\ncusam et justo duo dolores et ea rebum. Stet clita kasd gubergr\nn, no sea takimata sanct\ns est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. \n\nAt vero eos et accusam et justo duo dolores et \n\n\nea rebum. Stet clita kasd gubergren, no sea takimata \nsanctus est Lorem ipsum dolor sit amet.",
				"0781234567", "Betreff", new Date(), to, null));
		mc.addToInbox(new SMSMessage("Test Tset", "0781234567", "Betreff", new Date(), to, null));
		mc.addToInbox(new SMSMessage("Einladung zur", "0781234567", "Betreff", new Date(), to, null));
		mc.saveDraft(new SMSMessage("Hallo Enwurf", "0781234567", "Betreff", new Date(), to, null));
		mc.addToInbox(new SMSMessage("Einladung zur", "0781234567", "Betreff", new Date(), to, null));
		mc.addToInbox(new SMSMessage(
				"Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed \ndiam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo do\nlores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est .",
				"0781234567", "Betreff", new Date(), to, null));
		mc.saveDraft(new SMSMessage("Entwurf: Einladung zur", "0781234567", "Betreff", new Date(), to, null));
		mc.addToInbox(new SMSMessage("Hallo ich wollte nur fragen...", "0781234567", "Betreff", new Date(), to, null));
		mc.addToInbox(new SMSMessage("Herzliche Glückwunsch", "0781234567", "Betreff", new Date(), to, null));
		mc.addToInbox(new SMSMessage("Hallo ich wollte nur fragen...", "0781234567", "Betreff", new Date(), to, null));
		mc.addToInbox(new SMSMessage("Einladung zur", "0781234567", "Betreff", new Date(), to, null));

		EmailMessage em = new EmailMessage();
		em.setDate(new Date());
		em.setFrom("from");
		em.setMessage("Message");
		em.setSubject("Email");
		em.setTo(to);
		mc.addToInbox(em);
		
		UserAgent ua1 = mc.getUserAgentFor(MessageType.SMS);
		UserAgent ua2 = mc.getUserAgentFor(MessageType.EMAIL);
		
		ua1.setAccount(new Account());
		ua2.setAccount(new Account());
		
		MainFrame mainFrame = new MainFrame(MessageType.EMAIL, f);
		mainFrame.setVisible(true);
	}

	public static void computer() {
		Computer c = new Computer("Mac Book");
		MessageClient mc = c.getMessageClient();

		ArrayList<String> to = new ArrayList<>();
		to.add("to@to.to");

		EmailMessage em = new EmailMessage();
		em.setDate(new Date());
		em.setFrom("from");
		em.setMessage("");
		em.setSubject("Email");
		em.setTo(to);
		mc.addToInbox(em);

		EmailMessage em2 = new EmailMessage();
		em2.setDate(new Date());
		em2.setFrom("from");
		em2.setMessage("Entwurf");
		em2.setSubject("Email");
		em2.setTo(to);
//		try {
//			em2.addAttachment(new Attachment("D:\\example.txt"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		mc.saveDraft(em2);


		UserAgent ua2 = mc.getUserAgentFor(MessageType.EMAIL);
		
		ua2.setAccount(new Account());
		
		MainFrame mainFrame = new MainFrame(MessageType.SMS, c);
		mainFrame.setVisible(true);
	}
}
