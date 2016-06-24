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
import com.example.protocol.javabean.CS_LoginJavaBean;

public class ClientLogin extends JPanel {
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Create the panel.
	 */
	public ClientLogin() {
		setLayout(null);

		JLabel lblAccount = new JLabel("Account");
		lblAccount.setBounds(52, 80, 61, 16);
		add(lblAccount);

		textField = new JTextField();
		textField.setBounds(167, 74, 134, 28);
		add(textField);
		textField.setColumns(10);

		JLabel lblPassword = new JLabel("password");
		lblPassword.setBounds(52, 141, 61, 16);
		add(lblPassword);

		passwordField = new JPasswordField();
		passwordField.setBounds(167, 135, 134, 28);
		add(passwordField);

		JButton btnLogin = new JButton("login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = textField.getText().toString();
				String string = new String(passwordField.getPassword());
				CS_LoginJavaBean bean = new CS_LoginJavaBean();
				bean.setName(name);
				bean.setPassword(string);
				IoMessagePackage ioMessagePackage = new MessagePackImpl(MessageKey.CS_Login, bean.getByteArray());
				ChatMain.sendMessage(ioMessagePackage);
				ChatMain.backToMain();
			}
		});
		btnLogin.setBounds(52, 201, 117, 29);
		add(btnLogin);

		JButton btnCancel = new JButton("exit");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 ChatMain.exit();
			}
		});
		btnCancel.setBounds(313, 201, 117, 29);
		add(btnCancel);

		JButton btnRegedit = new JButton("regedit");
		btnRegedit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ChatMain.changePanel(ClientRegedit.class.getName());
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnRegedit.setBounds(184, 201, 117, 29);
		add(btnRegedit);

	}
}
