package gui.frame;

import gui.helper.GridBagManager;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private GridBagManager guiManager;
	
	private JTextField tfLoginName;
	private JTextField tfLoginPw;
	private JTextField tfLoginServer;
	private JTextField tfLoginEmail;

	private JButton btRegister;
	private JButton btCancel;
	
	public LoginFrame(){
		this.guiManager = new GridBagManager(this);
		this.tfLoginName = new JTextField();
		this.tfLoginPw = new JTextField();
		this.tfLoginServer = new JTextField();
		this.tfLoginEmail = new JTextField();
		this.btRegister = new JButton("Register");
		this.btCancel = new JButton("Cancel");
		configureFrame();
	}

	private void configureFrame() {
		btCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//"Killt" das Programm
				System.exit(0);
			}
		});
		

		guiManager.setX(0).setY(0).setWidth(1).setComp(new JLabel("*Loginname"));
		guiManager.setX(1).setY(0).setWidth(7).setWeightX(7).setFill(GridBagConstraints.HORIZONTAL).setComp(tfLoginName);

		guiManager.setX(0).setY(1).setWidth(1).setComp(new JLabel("*Password"));
		guiManager.setX(1).setY(1).setWidth(7).setWeightX(7).setFill(GridBagConstraints.HORIZONTAL).setComp(tfLoginPw);
		
		guiManager.setX(0).setY(2).setWidth(1).setComp(new JLabel("*Email"));
		guiManager.setX(1).setY(2).setWidth(7).setWeightX(7).setFill(GridBagConstraints.HORIZONTAL).setComp(tfLoginEmail);
		
		guiManager.setX(0).setY(3).setWidth(1).setComp(new JLabel("*Server"));
		guiManager.setX(1).setY(3).setWidth(7).setWeightX(7).setFill(GridBagConstraints.HORIZONTAL).setComp(tfLoginServer);

		guiManager.setX(0).setY(4).setWidth(1).setFill(GridBagConstraints.HORIZONTAL).setComp(btRegister);
		guiManager.setX(1).setY(4).setWidth(7).setFill(GridBagConstraints.HORIZONTAL).setComp(btCancel);

		setTitle("Login");
		setSize(300, 250);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}	
}
