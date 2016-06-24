package com.zero.chat;

import java.awt.Component;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;

import com.avalon.api.internal.IoMessagePackage;
import com.zero.chat.io.ConnnectionMessage;
import com.zero.chat.io.NettyClientConsole;

public class ChatMain {

	private static JFrame frame;

	private static ChatMain main;

	public static ClientHall clientHall;

	private static NettyClientConsole clientConsole;

	public static ChatMain getInstance() {
		return main;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatMain.main = new ChatMain();
					ChatMain.frame.setVisible(true);
					ChatMain.clientConsole = new NettyClientConsole();
					ConnnectionMessage connection = new ConnnectionMessage("127.0.0.1", 12345);
					clientConsole.handleMessage(connection);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChatMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		ClientLogin clientLogin = new ClientLogin();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(clientLogin);
	}

	public static void test() {
		ClientRegedit clientRegedit = new ClientRegedit();
		frame.getContentPane().removeAll();
		frame.getContentPane().add(clientRegedit);
		frame.validate();
		frame.setVisible(true);
	}

	public static void changePanel(String name) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		Class<Component> loadClass = (Class<Component>) contextClassLoader.loadClass(name);
		Component newInstance = loadClass.newInstance();
		frame.getContentPane().removeAll();
		frame.getContentPane().add(newInstance);
		frame.validate();
		frame.repaint();
	}

	public static void backToMain() {
		ClientLogin component = new ClientLogin();
		frame.getContentPane().removeAll();
		frame.getContentPane().add(component);
		frame.validate();
		frame.setVisible(true);
		frame.repaint();
	}

	public static void exit() {
		System.exit(1);
	}

	public static void sendMessage(IoMessagePackage ioMessagePackage) {
		clientConsole.handleMessage(ioMessagePackage);
	}

	public static void showDialog(int key, int state, String context) {
		JDialog dialog = new MessageDialog(key, state == 1 ? "成功" : "失败", context);
		dialog.setVisible(true);
	}

	public static void enterHallMain(Integer hallId, List<String> usersName) {
		clientHall = new ClientHall(usersName);
		frame.getContentPane().removeAll();
		frame.getContentPane().add(clientHall);
		frame.validate();
		frame.repaint();

	}
}
