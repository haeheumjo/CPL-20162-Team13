package sam.client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import database.CommandsTest;


public class ClientThread implements Runnable {
	private Socket socket;
	private BufferedReader br = null;
	private PrintWriter in = null;
	private String rid = null;
 	public ClientThread(Socket socket, String rid){
		this.socket = socket;
		this.rid = rid;
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			in = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			in = new PrintWriter(socket.getOutputStream());
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	@Override
	public void run(){
		CommandsTest commandsTest = new CommandsTest();
		try {
			String strStdList =  commandsTest.getStudentList(rid);
			
			List<String> stdList = new ArrayList<String>();
			stdList.addAll(Arrays.asList(strStdList.split("\\+")));
			if(!commandsTest.clearToDayRecord(rid))
				System.out.println("cleared record");
			for(String aStudent: stdList){
				if(!commandsTest.insertNewRecord(aStudent,rid))
					System.out.println("insertNewRecord() false");
			}
			in.println(strStdList);
			in.flush();
			String info = null;
			System.out.println("reading...");
			while((info = br.readLine())!=null){
				System.out.println(info);
				if(stdList.contains(info)){
					stdList.remove(info);
					try {
						System.out.println(stdList);
						commandsTest.updateRecordAsAttend(info,rid,"attend");
					} catch (SQLException e) {
					// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			commandsTest.updateRecordToAbsent(rid, "absent");
			commandsTest.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
