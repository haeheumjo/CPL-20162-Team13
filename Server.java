package com.heyang;

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
