package sam.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServerThread implements Runnable{
    Socket socket = null;
    private boolean flag = false;
	public ServerThread(Socket socket){
        this.socket = socket;
//		mDT = new MDateClass();
    }
	@Override
    public void run(){
			try {
             // 为输入流添加缓冲
				System.out.println("客户端的数量：");
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String info = null;
				String matchedList = null;
				List<String> stdList = null;
				PrintWriter pw = new PrintWriter(socket.getOutputStream());
				System.out.println("reading...");
				if((info = br.readLine())!=null){
					stdList = Arrays.asList(info.split("\\+"));
					System.out.println(info);
				}
//				System.out.println(String.format("received msg [%s] from client IP:%s",
//				info,
//				socket.getRemoteSocketAddress().toString()));
				
				System.out.println("Scanning the BLE devices");
				//模拟比对蓝牙
				int i = 5;
				while(i < 15){
					String innerStudent = String.format("201401503%s",i);
					if(stdList.contains(innerStudent))
						if(matchedList == null)
							matchedList = innerStudent;
						else
							matchedList = matchedList +"+"+innerStudent;
					i++;
				}
				pw.println(matchedList);
				pw.flush();
				//OK end match
				socket.shutdownInput();//关闭输出流 
                        // 关闭资源
				br.close();
				socket.close();
             //System.out.println(br.readLine()); 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}// 字节输入流
			return;
    }
    public int mCommand(String cmd){
        //RPI connect;
        if(cmd.compareTo("RPI_STATE") == 0){
            return 1;
        }
        else if(cmd.compareTo("RPI_STDLIST") == 0){
            return 2;
        }
        else if(cmd.compareTo("RPI_ATDLIST") == 0){
            return 3;
        }
		else if(cmd.compareTo("RPI_CLOSE") == 0){
			return 4;
		}else
			return 0;
    }
}
