package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import view.ClientFrame;

public class LoginThread extends Thread {
	Socket loginsocket;
	ClientFrame cf;

	public LoginThread(Socket loginsocket, ClientFrame cf) {
		this.loginsocket = loginsocket;
		this.cf = cf;
	}

	@Override
	public void run() {
		super.run();
		try {
			while (true) {
				String loginstr = "";
				BufferedReader loginbr = new BufferedReader(new InputStreamReader(loginsocket.getInputStream()));
				loginstr = loginbr.readLine();

				System.out.println("loginstr: " + loginstr);

				if (loginstr == null) {
					System.out.println("접속끊김");
				}

				loginstr = loginstr.replace("-", "\n");
				cf.textAN.setText(loginstr);

				Thread.sleep(100);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
