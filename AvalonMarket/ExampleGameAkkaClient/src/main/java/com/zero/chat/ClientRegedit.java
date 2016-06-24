package com.zero.chat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.avalon.api.internal.IoMessagePackage;
import com.avalon.io.MessagePackImpl;
import com.example.protocol.MessageKey;
import com.example.protocol.javabean.CS_RegeditJavaBean;

public class ClientRegedit extends JPanel {
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Create the panel.
	 */
	public ClientRegedit() {
		setLayout(null);
		
		JLabel lblAccount = new JLabel("Account");
		lblAccount.setBounds(48, 82, 61, 16);
		add(lblAccount);
		
		textField = new JTextField();
		textField.setBounds(160, 76, 134, 28);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblPassword = new JLabel("password");
		lblPassword.setBounds(48, 160, 61, 16);
		add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(160, 154, 134, 28);
		add(passwordField);
		
		JButton btnRegedit = new JButton("regedit");
		btnRegedit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String string = textField.getText().toString();
				char[] password = passwordField.getPassword();
				String passwordString = new String(password);
				CS_RegeditJavaBean bean=new CS_RegeditJavaBean();
				bean.setName(string);
				bean.setPassword(passwordString);
				IoMessagePackage ioMessagePackage=new MessagePackImpl(MessageKey.CS_Regedit,bean.getByteArray());
				ChatMain.sendMessage(ioMessagePackage);
				ChatMain.backToMain();
			}
		});
		btnRegedit.setBounds(48, 212, 117, 29);
		add(btnRegedit);
		
		JButton btnCancel = new JButton("cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChatMain.backToMain();
			}
		});
		btnCancel.setBounds(177, 212, 117, 29);
		add(btnCancel);

	}

}
