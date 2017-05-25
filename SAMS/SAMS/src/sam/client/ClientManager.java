package sam.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import database.CommandsTest;

public class ClientManager {
	// public static void main(String args[])
	// {
	//
	// ClientManager.start("p001");
	// }
	private static final int RPI_PORT = 12340;
	private static final int END_PORT = 12341;

	public static boolean stop(String ip) {
		if (ip.equals("") || ip == null) {
			System.out.println("error");
			return false;
		}
		Socket socket = null;
		try {
			socket = new Socket(ip, END_PORT);
			System.out.println("ClientManager: connected to " + socket.getInetAddress().toString());
			socket.close();
			if (socket != null) {
				System.out.println("ClientManager : start the client thread");
				// Thread mThread = new Thread(new ClientThread(socket, rid));
				// mThread.start();
				return true;
			}
			return false;
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static Boolean start(String ip, String rid) {
		CommandsTest test = new CommandsTest();
		if (ip.equals("") || ip == null) {
			System.out.println("error");
			return false;
		}
		Socket socket = null;
		try {
			socket = new Socket(ip, RPI_PORT);
			System.out.println("ClientManager: connected to " + socket.getInetAddress().toString());
			if (socket != null) {
				System.out.println("ClientManager : start the client thread");
				Thread mThread = new Thread(new ClientThread(socket, rid));
				mThread.start();
				return true;
			}return false;
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}
}
