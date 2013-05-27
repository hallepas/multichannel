package gui.main;

import gui.components.MainFrame;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import javax.swing.UIManager;

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

		// new LoginFrame().setVisible(true);
		showEMailTab();
		showSMSTab();
	}

	public static void messagesTEst(){
		Smartphone f = new Smartphone("Iphone");

		ArrayList<Message> messages = new ArrayList<Message>();
		ArrayList<String> to = new ArrayList<>();
		to.add("to@to.to");
		messages.add(new SMSMessage("Hallo ich wollte nur fragen...", "from@from.from", "Betreff", new Date(), to, null));
		messages.add(new SMSMessage(
				"Lorem ipsum dolor sit amet, consetetur sadipscing elitr,\n sed diam nonumy eirmod tempor invidunt ut\n labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et ac\ncusam et justo duo dolores et ea rebum. Stet clita kasd gubergr\nn, no sea takimata sanct\ns est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. \n\nAt vero eos et accusam et justo duo dolores et \n\n\nea rebum. Stet clita kasd gubergren, no sea takimata \nsanctus est Lorem ipsum dolor sit amet.",
				"from@from.fro2m", "Betreff", new Date(), to, null));
		messages.add(new SMSMessage("Test Tset", "from@from.from", "Betreff", new Date(), to, null));
		messages.add(new SMSMessage("Einladung zur", "from@from.fro2m", "Betreff", new Date(), to, null));
		messages.add(new SMSMessage("Hallo ich wollte nur fragen...", "from@from.from", "Betreff", new Date(), to, null));
		messages.add(new SMSMessage("Einladung zur", "from@from.fro2m", "Betreff", new Date(), to, null));
		messages.add(new SMSMessage(
				"Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed \ndiam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo do\nlores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est .",
				"from@from.from", "Betreff", new Date(), to, null));
		messages.add(new SMSMessage("Einladung zur", "from@from.fro2m", "Betreff", new Date(), to, null));
		messages.add(new SMSMessage("Hallo ich wollte nur fragen...", "from@from.from", "Betreff", new Date(), to, null));
		messages.add(new SMSMessage("Herzliche Glückwunsch", "from@from.fro2m", "Betreff", new Date(), to, null));
		messages.add(new SMSMessage("Hallo ich wollte nur fragen...", "from@from.from", "Betreff", new Date(), to, null));
		messages.add(new SMSMessage("Einladung zur", "from@from.fro2m", "Betreff", new Date(), to, null));

		EmailMessage em = new EmailMessage();
		em.setDate(new Date());
		em.setFrom("from");
		em.setMessage("");
		em.setSubject("Email");
		em.setTo(to);
		messages.add(em);
		
		MainFrame mainFrame = new MainFrame(MessageType.EMAIL, messages, f);
		mainFrame.setVisible(true);
	}
	
	public static void showSMSTab() { 
		Smartphone f = new Smartphone("IPhone");
		
		ArrayList<Message> messages = new ArrayList<Message>();
		ArrayList<String> to = new ArrayList<>();
		to.add("to@to.to");
		messages.add(new SMSMessage("Hallo ich wollte nur fragen...", "from@from.from", "Betreff", new Date(), to, null));
		messages.add(new SMSMessage(
				"Lorem ipsum dolor sit amet, consetetur sadipscing elitr,\n sed diam nonumy eirmod tempor invidunt ut\n labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et ac\ncusam et justo duo dolores et ea rebum. Stet clita kasd gubergr\nn, no sea takimata sanct\ns est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. \n\nAt vero eos et accusam et justo duo dolores et \n\n\nea rebum. Stet clita kasd gubergren, no sea takimata \nsanctus est Lorem ipsum dolor sit amet.",
				"from@from.fro2m", "Betreff", new Date(), to, null));
		messages.add(new SMSMessage("Test Tset", "from@from.from", "Betreff", new Date(), to, null));
		messages.add(new SMSMessage("Einladung zur", "from@from.fro2m", "Betreff", new Date(), to, null));
		messages.add(new SMSMessage("Hallo ich wollte nur fragen...", "from@from.from", "Betreff", new Date(), to, null));
		messages.add(new SMSMessage("Einladung zur", "from@from.fro2m", "Betreff", new Date(), to, null));
		messages.add(new SMSMessage(
				"Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed \ndiam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo do\nlores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est .",
				"from@from.from", "Betreff", new Date(), to, null));
		messages.add(new SMSMessage("Einladung zur", "from@from.fro2m", "Betreff", new Date(), to, null));
		messages.add(new SMSMessage("Hallo ich wollte nur fragen...", "from@from.from", "Betreff", new Date(), to, null));
		messages.add(new SMSMessage("Herzliche Glückwunsch", "from@from.fro2m", "Betreff", new Date(), to, null));
		messages.add(new SMSMessage("Hallo ich wollte nur fragen...", "from@from.from", "Betreff", new Date(), to, null));
		messages.add(new SMSMessage("Einladung zur", "from@from.fro2m", "Betreff", new Date(), to, null));

		EmailMessage em = new EmailMessage();
		em.setDate(new Date());
		em.setFrom("from");
		em.setMessage("Message");
		em.setSubject("Email");
		em.setTo(to);
		messages.add(em);
		
		MainFrame mainFrame = new MainFrame(MessageType.EMAIL, messages, f);
		mainFrame.setVisible(true);
	}

	public static void showEMailTab() { 
		Computer c = new Computer("Mac Book");
		
		ArrayList<Message> messages = new ArrayList<Message>();
		ArrayList<String> to = new ArrayList<>();
		to.add("to@to.to");

		EmailMessage em = new EmailMessage();
		em.setDate(new Date());
		em.setFrom("from");
		em.setMessage("");
		em.setSubject("Email");
		em.setTo(to);
		messages.add(em);

		MainFrame mainFrame = new MainFrame(MessageType.SMS, messages, c);
		mainFrame.setVisible(true);
	}
}
