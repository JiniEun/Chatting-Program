package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import view.ClientFrame;

public class RoomThread extends Thread {
	Socket roomsocket;
	ClientFrame cf;

	public RoomThread(Socket roomsocket, ClientFrame cf) {
		this.roomsocket = roomsocket;
		this.cf = cf;
	}

	@Override
	public void run() {
		super.run();
		try {
			while (true) {
				String roomstr = "";

				BufferedReader roombr = new BufferedReader(new InputStreamReader(roomsocket.getInputStream()));
				roomstr = roombr.readLine();

				if (roomstr == null) {
					System.out.println("접속끊김");
				}

				if (!roomstr.equals("-")) {
					String[] spstr = roomstr.split("-");
					String em = spstr[spstr.length - 1];

					if (spstr.length > cf.demoList.size() + 1) {
						cf.demoList.removeAllElements();
						cf.demoList.addElement("Lobby");

						for (int i = 1; i < spstr.length; i++) {
							cf.demoList.addElement(spstr[i]);
							switch (i + 1) {
							case 2:
								cf.hMap.put(spstr[i], cf.scrPane1);
								break;
							case 3:
								cf.hMap.put(spstr[i], cf.scrPane2);
								break;
							case 4:
								cf.hMap.put(spstr[i], cf.scrPane3);
								break;
							case 5:
								cf.hMap.put(spstr[i], cf.scrPane4);
								break;
							}
						}

					} else if (spstr.length == cf.demoList.size() + 1) {

						cf.demoList.addElement(em);

						switch (cf.demoList.size()) {
						case 2:
							cf.hMap.put(em, cf.scrPane1);
							break;
						case 3:
							cf.hMap.put(em, cf.scrPane2);
							break;
						case 4:
							cf.hMap.put(em, cf.scrPane3);
							break;
						case 5:
							cf.hMap.put(em, cf.scrPane4);
							break;
						}
					}

				}

				Thread.sleep(500);

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
