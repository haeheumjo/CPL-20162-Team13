import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread{
    Socket socket = null;
    public ServerThread(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run(){
        InputStream is = socket.getInputStream();// 字节输入流
             // 将字节流转换为字符流
             InputStreamReader isr = new InputStreamReader(is);
             // 为输入流添加缓冲
             BufferedReader br = new BufferedReader(isr);

             String info = null;
             if((info = br.readLine())!=null){
                 System.out.println("我是服务器，读取客户端发过来的信息："+info);
             }
             OutputStream os = socket.getOutputStream();
             PrintWriter pw = new PrintWriter(os);
             pw.println("用户名:admin 密码:123");
             pw.flush();
             pw.println("Y");
             pw.flush();
             
             pw.println("3");
             pw.flush();
             for(int i = 0; i < 3; i++){
                pw.println("student "+i);
                pw.flush();
             }
             
             
             String strNum = null;
             if((strNum = br.readLine())!=null){
            	 int numOfAdc = Integer.valueOf(strNum);
       	      for(int i = 0; i < numOfAdc; i++)
       	         System.out.println(br.readLine());   
             }
             //System.out.println(br.readLine()); 
             socket.shutdownInput();//关闭输出流 
             // 关闭资源
             br.close();
             isr.close();
             is.close();
             socket.close();
    }
}
