package threadex;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import main.MainClass;

public class RoomServerThread extends Thread {

	Socket roomSocket;
	List<Socket> roomlist;

	public RoomServerThread(Socket roomSocket, List<Socket> roomlist) {
		this.roomSocket = roomSocket;
		this.roomlist = roomlist;
	}

	@Override
	public void run() {
		super.run();

		// 수신(recv)
		try {
			while (true) {
				Thread.sleep(300);

				// 방목록 송신(send) 구분자 '-'
				BufferedReader roomreader = new BufferedReader(new InputStreamReader(roomSocket.getInputStream()));
				String roomstr = null;
				if ((roomstr = roomreader.readLine()) != null) {
					MainClass.room += roomstr + "-";
					MainClass.room = MainClass.room.replace("--", "-");

					// 송신(send)
					for (Socket s : roomlist) {
						PrintWriter writer = new PrintWriter(s.getOutputStream());
						writer.println(MainClass.room);
						writer.flush();
					}
				}
				Thread.sleep(300);
			}
		} catch (Exception e) {
		}
	}
}
