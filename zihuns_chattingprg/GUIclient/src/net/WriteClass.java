package net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import view.ClientFrame;
import view.IDFrame;

public class WriteClass {

	Socket socket;
	Socket loginsocket;
	Socket roomsocket;
	ClientFrame cf;

	public WriteClass(Socket socket, Socket loginsocket, Socket roomsocket, ClientFrame cf) {
		super();
		this.socket = socket;
		this.loginsocket = loginsocket;
		this.roomsocket = roomsocket;
		this.cf = cf;
	}

	public void sendMessage() {

		System.out.println("sendMessage()");
		try {

			String msg = "";
			String id = IDFrame.tf.getText();
			PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

			// 첫번째 전송
			if (cf.isFirst) {
				InetAddress iaddr = socket.getLocalAddress();
				String ip = iaddr.getHostAddress(); // xxx.xxx.xxx.xxx

				msg = "[" + id + "]님 로그인(" + ip + ")";
				pw.println(msg);
				pw.flush();

				// 로그인 전송
				PrintWriter loginpw = new PrintWriter(loginsocket.getOutputStream(), true);
				String loginmsg = id + "(" + ip + ")\n";

				loginpw.println(loginmsg);
				loginpw.flush();

				// 방리스트 전송
				PrintWriter roompw = new PrintWriter(roomsocket.getOutputStream(), true);
				String roomnumber = cf.textR.getText();

				roompw.println(roomnumber);
				roompw.flush();
				cf.isRoom = false;

			}
			// 그외 전송
			else {
				if (cf.isRoom) {
					String roomnumber = cf.textR.getText();

					if (roomnumber != null) {
						// server로 전송
						PrintWriter roompw = new PrintWriter(roomsocket.getOutputStream(), true);
						roompw.println(roomnumber);
						roompw.flush();
					}
					cf.isRoom = false;
				} else {
					InetAddress iaddr = socket.getLocalAddress();
					String ip = iaddr.getHostAddress(); // xxx.xxx.xxx.xxx

					// 어떤 방 + 메시지 전송
					String selected = (String) cf.roomlist.getSelectedValue();
					if (selected == null) {
						selected = "0";
					}

					msg = selected + "room--[" + id + "]" + cf.textF.getText();
					pw.println(msg);
					pw.flush();
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
