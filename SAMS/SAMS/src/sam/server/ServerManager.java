package sam.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import socketThread.LogServerThread;
import socketThread.StdServerThread;
  
 /*
  * multiThread socket
  * 服务器端
  */
public class ServerManager extends Thread{
	private ServerSocket rpi_serverSocket;
    private ServerSocket std_serverSocket;
    private ServerSocket web_serverSocket;
    private ServerSocket log_serverSocket;
    private static final int RPI_PORT = 12340;
    private static final int END_PORT = 12341;
    private static final int STD_PORT = 12342;
    private static final int WEB_PORT = 12343;
    private static final int LOG_PORT = 12344;
    private final int POOL_SIZE = 20;
    public ServerManager() {
    	try {
			rpi_serverSocket = new ServerSocket(RPI_PORT);
			std_serverSocket = new ServerSocket(STD_PORT);
			web_serverSocket = new ServerSocket(WEB_PORT);
			log_serverSocket = new ServerSocket(LOG_PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println("Started service");
    }
    @Override
    public void run(){
//    	Thread webThread = new Thread(new WebSocketThread(web_serverSocket));
//    	webThread.start();
//    	Thread stdThread = new Thread(new StdSocketThread(std_serverSocket));
//    	stdThread.start();
//    	Thread logThread = new Thread(new LogSocketThread(log_serverSocket));
//    	logThread.start();
    	
    	/*********
    	int count = 0;
        // 2、调用()方法开始监听，等待客户端的连接
        System.out.println("***服务器即将启动，等待客户端的连接***");
        Socket socket = null;
            // 1、创建一个服务器Socket，即ServerSocket，指定绑定的端口，并监听此端口
     
        System.out.println("reading...");
        
        while(true){
          try {
			socket= serverSocket.accept();
			System.out.println("reading...");
          	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
          	}// 就会处于阻塞的状态，等待监听 
          	ServerThread sThread = new ServerThread(socket);
          	Thread td = new Thread(sThread);
          	td.start();
          	count++;
            System.out.println("客户端的数量："+count);
            InetAddress address=socket.getInetAddress();
            System.out.println("当前客户端的IP："+address.getHostAddress());
        }
        ****/
    }

    class LogSocketThread implements Runnable{
    	private ServerSocket serverSocket;
    	private int localPort;
    	public LogSocketThread(ServerSocket s){
    		serverSocket = s;
    		localPort = serverSocket.getLocalPort();
    	}
    	@Override
    	public void run(){
    		int count = 0;
	        System.out.println("***服务器即将启动，等待客户端的连接***");
	        Socket socket = null;
	        System.out.println("reading...");
	        while(true){
		          try {
					socket= serverSocket.accept();
					System.out.println("reading...");
		          	} catch (IOException e) {
					e.printStackTrace();
		          	}// 就会处于阻塞的状态，等待监听 
		          	Thread td = new Thread(new LogServerThread(socket));
		          	td.start();
		          	count++;
		            System.out.println(String.format("%d : 客户端的数量：%d", localPort,count));
		            InetAddress address=socket.getInetAddress();
//		            try {
//						socket.close();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
	        }
    	}
    }
    class StdSocketThread implements Runnable{
    	private ServerSocket serverSocket;
    	private int localPort;
    	public StdSocketThread(ServerSocket s){
    		serverSocket = s;
    		localPort = serverSocket.getLocalPort();
    	}
    	@Override
    	public void run(){
    		int count = 0;
	        System.out.println("***服务器即将启动，等待客户端的连接***");
	        Socket socket = null;
	        System.out.println("reading...");
	        while(true){
		          try {
					socket= serverSocket.accept();
					System.out.println("reading...");
		          	} catch (IOException e) {
					e.printStackTrace();
		          	}// 就会处于阻塞的状态，等待监听 
//		          	ServerThread sThread = new ServerThread(socket);
		          	Thread td = new Thread(new StdServerThread(socket));
		          	td.start();
		          	count++;
		            System.out.println(String.format("%d : 客户端的数量：%d", localPort,count));
		            InetAddress address=socket.getInetAddress();
//		            try {
//						socket.close();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
	        }
    	}
    }
    class RPISocketThread implements Runnable{
    	private Socket socket;
    	private int localPort;
    	@Override
    	public void run(){
    		int count = 0;
	        System.out.println("***服务器即将启动，等待客户端的连接***");
	        Socket socket = null;
	        System.out.println("reading...");
	        while(true){
	        	try {
	        		socket = new Socket("123.213.123.123",123);
					System.out.println("reading...");
		          	} catch (IOException e) {
					e.printStackTrace();
		          	}// 就会处于阻塞的状态，等待监听 
//		          	ServerThread sThread = new ServerThread(socket);
//		          	Thread td = new Thread(sThread);
//		          	td.start();
		          	count++;
		            System.out.println(String.format("%d : 客户端的数量：%d", localPort,count));
		            InetAddress address=socket.getInetAddress();
//		            System.out.println(String.format("%d :当前客户端的IP： %d", localPort,address.getHostAddress()));
		            try {
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
	        }
    	}
    }
    class WebSocketThread implements Runnable{
    	private ServerSocket serverSocket;
    	private int localPort;
    	public WebSocketThread(ServerSocket s){
    		serverSocket = s;
    		localPort = serverSocket.getLocalPort();
    	}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			int count = 0;
	        // 2、调用()方法开始监听，等待客户端的连接
	        System.out.println("***服务器即将启动，等待客户端的连接***");
	        Socket socket = null;
	            // 1、创建一个服务器Socket，即ServerSocket，指定绑定的端口，并监听此端口	     
	        System.out.println("reading...");
	        while(true){
	          try {
				socket= serverSocket.accept();
				System.out.println("reading...");
	          	} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
	          	}// 就会处于阻塞的状态，等待监听 
//	          	ServerThread sThread = new ServerThread(socket);
//	          	Thread td = new Thread(sThread);
//	          	td.start();
	          	count++;
	            System.out.println(String.format("%d : 客户端的数量：%d", localPort,count));
	            InetAddress address=socket.getInetAddress();
	            //System.out.println(String.format("%d :当前客户端的IP： %d", localPort,address.getHostAddress()));
	            try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
		}
    }
 }