package sam.server;

import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import socketThread.StdServerThread;

public class StudentServer extends Thread {
	private static final int PORT = 12342;
	private ServerSocket server;

	public StudentServer() {
		try {
			server = new ServerSocket(PORT);
			System.out.println("端口已开启,占用" + PORT + "端口号(STUDENT)....");
		} catch (BindException e) {
			System.out.println("端口使用中....");
			System.out.println("请关掉相关程序并重新运行服务器！");
			System.exit(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		super.run();
		int count = 0;
		System.out.println("***服务器即将启动，等待客户端的连接***");
		Socket socket = null;
		while (true) {
			try {
				System.out.println("connecting...");
				socket = server.accept();
				System.out.println("connected...");
			} catch (IOException e) {
				e.printStackTrace();
			} // 就会处于阻塞的状态，等待监听
			Thread td = new Thread(new StdServerThread(socket));
			td.start();
			count++;
			System.out.println(String.format("%d : 客户端的数量：%d", PORT, count));
			InetAddress address = socket.getInetAddress();
		}
	}
}
