package sam.server;

import java.sql.SQLException;
import java.util.List;

import database.CommandsTest;
import database.Record;

public class ServerDriver {
	public static void main(String[] args){
		staticThread();
	}
	public static void staticThread(){
		StudentServer ss = new StudentServer();
		BindServer bs = new BindServer();
		ss.start();
		bs.start();
	}
}
