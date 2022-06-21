package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Iterator;

import view.ClientFrame;

public class ReadThread extends Thread {

	Socket socket;
	ClientFrame cf;

	public ReadThread(Socket socket, ClientFrame cf) {
		this.socket = socket;
		this.cf = cf;
	}

	@Override
	public void run() {
		super.run();
		try {
			while (true) {

				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String str = br.readLine();

				if (str == null) {
					System.out.println("접속끊김");
				}

				Thread.sleep(300);

				// 방번호 구분자 room--
				if (!str.equals("room--")) {
					String wstr = "0";
					String[] strs = str.split("room--");

					wstr = strs[0] + "";

					if (wstr.equals("null"))
						wstr = "0";

					if (strs.length > 1) {
						str = strs[1] + "";

						if (str.equals("null"))
							str = "";
					}

					Iterator<String> it = cf.hMap.keySet().iterator();
					int i = 0, idx = 0;
					while (it.hasNext()) {
						String key = it.next();

						if (key.equals(wstr)) {
							idx = i;
						}
						i++;
					}

					// 같은 방에 입장해 있으면 대화출력
					if (cf.EntRoom.equals(wstr)) {

						switch (idx) {
						case 0:
							cf.textA.append(str + "\n");
							break;
						case 1:
							cf.textA1.append(str + "\n");
							break;
						case 2:
							cf.textA2.append(str + "\n");
							break;
						case 3:
							cf.textA3.append(str + "\n");
							break;
						case 4:
							cf.textA4.append(str + "\n");
							break;
						default:
							cf.textA.append(str + "\n");
							break;
						}
					}
				}
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
