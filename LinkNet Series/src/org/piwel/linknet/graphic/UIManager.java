package org.piwel.linknet.graphic;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.json.simple.JSONObject;
import org.piwel.linknet.mlp.IHM;
import org.piwel.linknet.mlp.NeuralNetwork;
import org.piwel.linknet.util.RequestPOST;
import org.piwel.linknet.util.session.Session;
import org.piwel.linknet.util.session.Session.SessionException;

public class UIManager  extends JFrame{
	DrawUIManager panel;
	JTextField mail, psw;
	JButton exitBtn, okBtn;
	Setting s;
	Session generatedSession = null;
	public UIManager(JSONObject json, Setting s) {
		
		this.s = s;
		
		setSize(s.width, s.height);
		panel = new DrawUIManager();
		panel.setBackground(Color.BLACK);
		panel.setLayout(null);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		if(!Session.WSCheckSession(s.token)) {
			initFields();
		}
		
		add(panel);
		setVisible(true);
		
	}
	public void initFields() {
		mail = new JTextField(100);
		psw = new JTextField(100);
		JLabel mailLabel = new JLabel("Mail* : ");
		JLabel pswLabel = new JLabel("Mot de passe* : ");
		exitBtn = new JButton("Fermer");
		okBtn = new JButton("Se connecter");
		
		mail.setLocation(s.width/4+s.width/8, s.height/2-20);
		mail.setSize(s.width/2-s.width/8, 20);
		
		psw.setLocation(s.width/4+s.width/8, s.height/2+20);
		psw.setSize(s.width/2-s.width/8, 20);
		
		mailLabel.setLocation(s.width/4, s.height/2-20);
		mailLabel.setSize(s.width/8, 20);
		
		pswLabel.setLocation(s.width/4, s.height/2+20);
		pswLabel.setSize(s.width/8, 20);
		
		exitBtn.setLocation(s.width/4, s.height/2+60);
		exitBtn.setSize(s.width/4-20, 20);
		
		okBtn.setLocation(s.width/2+20, s.height/2+60);
		okBtn.setSize(s.width/4-20, 20);
		okBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					generatedSession = new Session(mail.getText(), psw.getText());
					if(generatedSession.isAuthentValid()) {
						IHM.info(generatedSession.getToken());
						setVisible(false);
						
						s.token = generatedSession.getToken();
						Setting.writeSettings(s);
					}
				}catch (SessionException ex) {
					mail.setText("");
					psw.setText("");
					IHM.info(ex.getCauseMessage());
				}
			}
		});
		panel.add(mail);
		panel.add(mailLabel);
		panel.add(psw);
		panel.add(pswLabel);
		panel.add(exitBtn);
		panel.add(okBtn);
	}
	class DrawUIManager extends JPanel{
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
		}
	}
}
