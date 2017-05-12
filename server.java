package com.heyang;
  
  import java.io.BufferedReader;
  import java.io.IOException;
  import java.io.InputStream;
  import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
  import java.net.Socket;
  
 /*
  * 基于TCP协议的Socket通信，实现用户登录
  * 服务器端
  */
 public class Server {
     public static void main(String[] args) {
         try {
             // 1、创建一个服务器Socket，即ServerSocket，指定绑定的端口，并监听此端口
             ServerSocket serverSocket = new ServerSocket(9999);
             // 2、调用()方法开始监听，等待客户端的连接
             System.out.println("***服务器即将启动，等待客户端的连接***");
             Socket socket = serverSocket.accept();// 就会处于阻塞的状态，等待监听 

             // 3、获取输入流，病读取客户端信息
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
             socket.shutdownInput();//关闭输出流
              
             // 关闭资源
             br.close();
             isr.close();
             is.close();
             socket.close();
             serverSocket.close();
             
         } catch (IOException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }
     }
 }
