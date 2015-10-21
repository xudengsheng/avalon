package com.zero.chat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.JEditorPane;

import com.avalon.api.internal.IoMessagePackage;
import com.avalon.io.MessagePackImpl;
import com.example.protocol.MessageKey;
import com.example.protocol.javabean.CS_HallMessageJavaBean;

public class ClientHall extends JPanel {
	final DefaultListModel listModel = new DefaultListModel();
	JEditorPane editorPane;
	JTextArea textArea;
	/**
	 * Create the panel.
	 */
	public ClientHall(List<String> names) {
		setLayout(null);

		JScrollPane scrollPane_Right = new JScrollPane();
		scrollPane_Right.setBounds(129, 6, 397, 256);
		add(scrollPane_Right);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane_Right.setViewportView(scrollPane);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

		for (String string : names) {
			listModel.addElement(string);
		}
		JList list = new JList(listModel);
		list.setValueIsAdjusting(true);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane_left = new JScrollPane(list);
		scrollPane_left.setBounds(10, 6, 114, 256);
		add(scrollPane_left);

		editorPane = new JEditorPane();
		editorPane.setBounds(10, 274, 338, 56);
		add(editorPane);

		JButton btnNewButton = new JButton("Send");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String string = editorPane.getText().toString();
				CS_HallMessageJavaBean bean = new CS_HallMessageJavaBean();
				bean.setUsersName("e");
				bean.setContext(string);

				IoMessagePackage ioMessagePackage = new MessagePackImpl(MessageKey.CS_HallMessage, bean.getByteArray());
				ChatMain.sendMessage(ioMessagePackage);
			}
		});
		btnNewButton.setBounds(373, 274, 117, 29);
		add(btnNewButton);

	}

	public void addNewMember(String name) {
		listModel.addElement(name);

	}

	public void removeMember(String name) {
		listModel.removeElement(name);

	}

	public void addMessage(String context) {
		textArea.append(context);
	}
}
