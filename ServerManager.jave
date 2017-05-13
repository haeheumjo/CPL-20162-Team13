package com.heyang;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
  
 /*
  * multiThread socket
  * 服务器端
  */
public class ServerManager {
    private ServerSocket serverSocket;
    private static final int PORT = 9999;
    public ServerManager() {
        try {
             // 1、创建一个服务器Socket，即ServerSocket，指定绑定的端口，并监听此端口
             serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
        }
    }
    public void start(){
        int count = 0;
        // 2、调用()方法开始监听，等待客户端的连接
        System.out.println("***服务器即将启动，等待客户端的连接***");
        Socket socket = null;
        while(true){
          try {
			socket= serverSocket.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 就会处于阻塞的状态，等待监听 
            ServerThread serverThread = new ServerThread(socket);
            serverThread.start();
            count++;
            System.out.println("客户端的数量："+count);
            InetAddress address=socket.getInetAddress();
            System.out.println("当前客户端的IP："+address.getHostAddress());
        } 
    }
 }
