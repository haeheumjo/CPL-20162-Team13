package com.heyang;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread{
    Socket socket = null;
    private boolean flag = false;
	private MDateClass mDT;
    public ServerThread(Socket socket){
        this.socket = socket;
		mDT = new MDateClass();
    }
    @Override
    public void run(){
            InputStream is;
			try {
				is = socket.getInputStream();
		
             // 将字节流转换为字符流
            InputStreamReader isr = new InputStreamReader(is);
             // 为输入流添加缓冲
            BufferedReader br = new BufferedReader(isr);

            String info = null;
            
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            String class_id;
            while(true){
                if((info = br.readLine())!=null){
                    System.out.println("我是服务器，读取客户端发过来的 command："+info);
                }
				String[] commands= br.split("\\@");
                switch(mCommand(commands[0])){
                    case 1:{
						//commands = RPI_STATE@WHERE
                        //received attendance status from DB
						String where = commands[1];
						String[] strDate = mDT.getDate().split("\\@"); //strDate[0] = dayOfWeek, strDate[1] = HHMM;
						String[] strHHMM = strDate[1].split("\\:"); //strHHMM[0] = hourOfDay, strHHMM[1] = minute;
						String class_id; //gain from db;
						String class_type; //gain from db;
						int std_num; //gain from select sql;
						List<String>std_list = new ArrayList<String>();
						String sendMsg = class_type+"@"; //msg = class_type@class_id@std_1+std_2+std_3;
						for(String tamp : std_list)
							sendMsg = sendMsg + "+" + tamp;
                        pw.println("Y@class_id@std_1+std_2+std_3");
                        pw.flush();
                        break;
                    }
                    // RPI require the student list
                    case 2:{
                        //get the student list from DB
                        pw.println("3");
                        pw.flush();
                        //reply the student list
                        for(int i = 0; i < 3; i++){
                            pw.println("student "+i);
                            pw.flush();
                        }
                        break;
                    }
                    // received the attendance list from RPI
                    case 3:{
                        if((strNum = br.readLine())!=null){
                 	        int numOfAdc = Integer.valueOf(strNum);
       	                    for(int i = 0; i < numOfAdc; i++)
                                //save the attendance list
           	                    System.out.println(br.readLine());
                        }
                        socket.shutdownInput();//关闭输出流 
                        // 关闭资源
                        br.close();
                        isr.close();
                        is.close();
                        socket.close(); 
                        return;
                    }
                    default:
                        break;
                }
            }
             //System.out.println(br.readLine()); 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}// 字节输入流
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
