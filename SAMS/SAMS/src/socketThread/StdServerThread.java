package socketThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Arrays;

import database.CommandsTest;

public class StdServerThread extends Thread {
	private Socket socket = null;
	public StdServerThread(Socket socket){
		this.socket = socket;
	}
	@Override
	public void run(){
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String info = null;
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			System.out.println("reading...");
			if((info = br.readLine())!=null){
				System.out.println(info);
				CommandsTest test = new CommandsTest();
				String result = test.CheckAttendance(info);
				while(result.equals("present"))
					result = test.CheckAttendance(info);
				System.out.println(result);
				pw.println(result);
				pw.flush();
			}
			socket.close();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
}
