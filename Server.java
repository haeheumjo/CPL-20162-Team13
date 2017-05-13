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
        ServerManager mServerManager = new ServerManager();
        mServerManager.start();
     }
 }
