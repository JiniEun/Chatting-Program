package threadex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import main.MainClass;

public class LoginThread extends Thread {

	Socket loginSocket;
	List<Socket> loginlist;

	public LoginThread(Socket loginSocket, List<Socket> loginlist) {
		this.loginSocket = loginSocket;
		this.loginlist = loginlist;
	}

	@Override
	public void run() {
		super.run();
		// 수신(recv)
		try {
			while (true) {
				// 로그인 명단 송신(send) 구분자 '-'
				BufferedReader loginreader = new BufferedReader(new InputStreamReader(loginSocket.getInputStream()));
				String loginstr = loginreader.readLine();
				MainClass.login += loginstr + "-";
				System.out.println("로그인 = " + loginstr);
				
				// 송신(send)
				for (Socket s : loginlist) {
						PrintWriter writer = new PrintWriter(s.getOutputStream());
						writer.println(MainClass.login);
						writer.flush();
				}
				Thread.sleep(300);
			}
		} catch (Exception e) {
			loginlist.remove(loginSocket);
			try {
				loginSocket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
