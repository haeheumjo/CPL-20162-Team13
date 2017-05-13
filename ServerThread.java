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
    public ServerThread(Socket socket){
        this.socket = socket;
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
            
            while(true){
                if((info = br.readLine())!=null){
                    System.out.println("我是服务器，读取客户端发过来的 command："+info);
                }
                switch(mCommand(info)){
                    case 1:{
                        if((info = br.readLine())!=null){
                            System.out.println("我是服务器，读取客户端发过来的信息："+info);
                        }
                        //received attendance status from DB
                        pw.println("Y");
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
                        String strNum = null;
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
        if(cmd.compareTo("RPI_STDLIST") == 0){
            return 2;
        }
        if(cmd.compareTo("RPI_ATDLIST") == 0){
            return 3;
        }
		return 0;
    }
}
