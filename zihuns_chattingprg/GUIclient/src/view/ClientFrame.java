package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.WriteClass;

// 채팅 Form readthread,writeclass required
public class ClientFrame extends JFrame implements WindowListener, ActionListener {

	public Socket socket;
	public Socket loginsocket;
	public Socket roomsocket;
	WriteClass wc;

	// textF: 채팅, textR: 방이름
	public JTextField textF = new JTextField(15);
	public JTextField textR = new JTextField(17);

	// textA~4: 방 갯수(5개), textAN: 로그인 명단
	public JTextArea textA = new JTextArea();
	public JTextArea textA1 = new JTextArea();
	public JTextArea textA2 = new JTextArea();
	public JTextArea textA3 = new JTextArea();
	public JTextArea textA4 = new JTextArea();
	public JTextArea textAN = new JTextArea();

	// scrPane~4: 방
	public JScrollPane scrPane = new JScrollPane(textA);
	public JScrollPane scrPane1 = new JScrollPane(textA1);
	public JScrollPane scrPane2 = new JScrollPane(textA2);
	public JScrollPane scrPane3 = new JScrollPane(textA3);
	public JScrollPane scrPane4 = new JScrollPane(textA4);

	// demoList: 방이름목록, roomlist: 방이름목록출력용 리스트, hMap: <방이름, 방(JScrollPane)>
	public DefaultListModel<String> demoList = new DefaultListModel<String>();
	public JList<String> roomlist = new JList<String>(demoList);
	public Map<String, JScrollPane> hMap = new HashMap<String, JScrollPane>();

	JLabel roomlabel = new JLabel("Room List(입장할 방 선택)");
	JButton btnTransfer = new JButton("send");
	JButton btnExit = new JButton("exit");
	JButton btnMake = new JButton("make");
	JButton btnEnt = new JButton("enter");

	// panel: 좌측, panelR: 우측, etc: 무시해도 됨
	JPanel panel = new JPanel();
	JPanel panelR = new JPanel();
	JPanel etc = new JPanel();

	public boolean isFirst = true; // 첫번째 전송
	public boolean isRoom = false; // 방 생성
	public String EntRoom = "0"; // 입장해 있는 방이름

	public ClientFrame(Socket socket, Socket loginsocket, Socket roomsocket) {
		super("chatting");

		this.socket = socket;
		this.loginsocket = loginsocket;
		this.roomsocket = roomsocket;

		wc = new WriteClass(socket, loginsocket, roomsocket, this);

		new IDFrame(wc, this);

		demoList.addElement("0");

		textA.append("[Lobby]\n");
		textA1.append("[Room1]\n");
		textA2.append("[Room2]\n");
		textA3.append("[Room3]\n");
		textA4.append("[Room4]\n");
		textA.setEditable(false);
		textA1.setEditable(false);
		textA2.setEditable(false);
		textA3.setEditable(false);
		textA4.setEditable(false);

		scrPane.setPreferredSize(new Dimension(200, 120));
		scrPane.setBounds(20, 20, 300, 400);
		add(scrPane);

		hMap.put("0", scrPane);

		setPanel(scrPane1);
		setPanel(scrPane2);
		setPanel(scrPane3);
		setPanel(scrPane4);

		JScrollPane scrPaneN = new JScrollPane(textAN);
		scrPaneN.setPreferredSize(new Dimension(200, 50));
		scrPaneN.setBounds(350, 20, 200, 150);
		add(scrPaneN);

		roomlabel.setBounds(350, 180, 200, 20);
		add(roomlabel);

		roomlist.setBorder(BorderFactory.createLineBorder(Color.gray));
		roomlist.setBounds(350, 200, 200, 190);
		add(roomlist);

		panel.setBounds(20, 430, 300, 100);
		panel.add(textF);
		panel.add(btnTransfer);
		add(panel);

		panelR.setBounds(350, 400, 200, 100);
		panelR.add(textR);
		panelR.add(btnMake);
		panelR.add(btnEnt);
		panelR.add(btnExit);
		add(panelR);

		add(etc);

		btnTransfer.addActionListener(this);
		btnExit.addActionListener(this);
		btnMake.addActionListener(this);
		btnEnt.addActionListener(this);

		setBounds(200, 200, 600, 510);

		setVisible(false);

	}

	public void setPanel(JScrollPane scrPane) {
		scrPane.setPreferredSize(new Dimension(200, 120));
		scrPane.setBounds(20, 20, 300, 400);
		add(scrPane);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		String id = IDFrame.tf.getText();

		if (obj == btnTransfer || obj == textF) {
			if (textF.getText().trim().equals(""))
				return;
			else {
				// server 전송
				wc.sendMessage();
				textF.setText("");
			}
		}

		else if (obj == btnExit) {
			this.dispose();
		}

		else if (obj == btnMake) {
			isRoom = true;
			wc.sendMessage();
			textR.setText("");

		} else if (obj == btnEnt) {
			// 선택된 방번호를 찾아서 입장
			String selected = (String) roomlist.getSelectedValue();
			EntRoom = selected;

			if (selected == null) {
				selected = "0";
			}
			Iterator<String> it = hMap.keySet().iterator();
			int i = 0;
			while (it.hasNext()) {
				String key = it.next();
				JScrollPane value = hMap.get(key);

				if (key == selected) {
					value.setVisible(true);
				} else {
					value.setVisible(false);
				}
			}
		}

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
