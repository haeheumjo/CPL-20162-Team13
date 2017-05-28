package com.example.meihao.myapplication;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import static android.content.ContentValues.TAG;

/**
 * Created by meihao on 2017-05-12.
 */


public class SocketClient {
    private Socket socket;
    private Handler mHandler;
    private BufferedReader in;
    //    private InputStream in;
    private PrintWriter out;
    Thread clientThread = null;
    private static final String RPI_STATE = "RPI_STATE";
    private static final String RPI_STDLIST = "RPI_STDLIST";
    private static final String RPI_ATDLIST = "RPI_ATDLIST";
    public SocketClient(){
        ;
    }
    public boolean connect(String ipAddress, int port){
        try {
            Log.d(TAG,"connecting ...");
            socket = new Socket(ipAddress,port);
            Log.d(TAG,"connected!");
            in = new BufferedReader(new InputStreamReader(socket
                    .getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //    class ClientThread implements Runnable {
//        @Override
//        public void run() {
//
//        }
//    }
    public boolean sendMsg(String msg){
        try {
            Log.d(TAG,"writing ...");
            out.println(msg);
            out.flush();
            Log.d(TAG,"wrote msg");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public List<String> readStdList(){
        List<String> stdIdList = new ArrayList<String>();
        int numOfStd = 0;
        try {
            sendMsg(RPI_STDLIST);
            String stdNumOfStd = in.readLine();
            //get the number of student from server
            Log.d(TAG,"strNumber:" + stdNumOfStd);
            numOfStd = Integer.valueOf(stdNumOfStd);
            Log.i(TAG,"get the number of student from server");
            for(int i = 0; i < numOfStd; i++)
                stdIdList.add(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return stdIdList;
    }

    public boolean writeAttendance(List<String> para){
        sendMsg(RPI_ATDLIST);
        int num = para.size();
        //Log.d(TAG,"sending the result of attendance... :"+num);
        out.println(Integer.toString(num));
        out.flush();
        Log.d(TAG,"The number of attendance : " + String.valueOf(num));
        for(String temp : para){
            out.println(temp);
            out.flush();
            //Log.i(TAG,"write attendance: " + temp);
        }
        return true;
    }
    public boolean readSTATE(){
        String msg = " ";
        sendMsg(RPI_STATE);
        sendMsg("Hello");
        try {
            Log.d(TAG,"reading ...");
            msg = in.readLine();
            Log.d(TAG,"read: " + msg + "compare with Y : " + msg.compareTo("Y"));

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        if(msg.compareTo("Y")==0 || msg.compareTo("y") == 0) {
            Log.d(TAG,"STATE TRUE");
            return true;
        }
        else
            return false;
    }
    public boolean closeSocket(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"close error");
            return false;
        }
        Log.d(TAG,"close socket success");
        return true;
    }
}
