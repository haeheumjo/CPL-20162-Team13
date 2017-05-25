package socketThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Arrays;

import database.CommandsTest;

public class LogServerThread extends Thread {
	private Socket socket = null;
	public LogServerThread(Socket socket){
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
				String[] sidAndSname = info.split("\\+");
				CommandsTest test = new CommandsTest();
				String sname = test.getSName(sidAndSname[0]);
				String result = "N";
				if(sname.equals(sidAndSname[1]))
					result = "Y";
				System.out.println(result);
				pw.println(result);
				pw.flush();
			}
			socket.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
}

