package main;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import net.LoginThread;
import net.ReadThread;
import net.RoomThread;
import view.ClientFrame;

public class MainClass {
	final static String ipaddress = "192.168.1.32";

	public static void main(String[] args) {

		try {
			Socket socket = new Socket(ipaddress, 9000);
			Socket loginsocket = new Socket(ipaddress, 9001);
			Socket roomsocket = new Socket(ipaddress, 9002);
			System.out.println("connection success!!");

			ClientFrame cf = new ClientFrame(socket, loginsocket, roomsocket);

			new ReadThread(socket, cf).start();
			new LoginThread(loginsocket, cf).start();
			new RoomThread(roomsocket, cf).start();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
