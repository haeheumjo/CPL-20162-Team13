package com.example.meihao.myapplication;

import android.Manifest;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.le.ScanRecord;
import android.content.DialogInterface;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.ParcelUuid;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import static android.bluetooth.BluetoothGattCharacteristic.PERMISSION_WRITE;
import static android.bluetooth.BluetoothGattCharacteristic.PROPERTY_WRITE;
import static android.content.ContentValues.TAG;
@TargetApi(21)
public class MainActivity extends Activity {
    private TextView statusText;
    private BluetoothGatt mGatt;
    private BluetoothAdapter mBluetoothAdapter;
    private boolean mScanning = true;
    private Handler mHandler;
    private ArrayList<BluetoothDevice> mBleDevicesList;
    private static final int REQUEST_ENABLE_BT = 1;
    private List<ScanFilter> bleScanFilters;
    private List<String> stdListFromServer;
    // Stops scanning after 10 seconds;
    private static final long SCAN_PERIOD = 25000;
    private long scan_period = 15000;
    private BluetoothLeScanner mBluetoothLeScanner;
    private ParcelUuid[] serviceUUid;
    private ScanSettings.Builder mBleScanSettingsBuild;
    private ScanCallback  mLeScanCallback;
    private ScanSettings bleScanSettings;
    private boolean state;
    private static final boolean STATE_FALSE = false;
    private static final boolean STATE_TRUE = true;
    private static final UUID INFO_UUID = UUID.fromString("00001235-0000-1000-8000-00805f9b34fb");
    private static final UUID SERVICE_UUID = UUID.fromString("00001234-0000-1000-8000-00805f9b34fb");
    private String attendanceList = null;
    ServerSocket rpi_serverSocket;
    ServerSocket end_serverSocket;
    private BufferedReader br;
    private PrintWriter pw;
    private void stopScanning() {
        if (mBluetoothLeScanner != null) {
            Log.d(TAG, "Stop scanning.");
            mBluetoothLeScanner.stopScan(mLeScanCallback);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler();
        checkPermission();
        myBLEinit();
        ServerManager sm = new ServerManager();
        sm.start();
        EndServerThread es = new EndServerThread();
        es.start();
    }
    private class EndServerThread extends Thread{
        Socket socket = null;
        private static final int END_PORT = 12341;
        @Override
        public void run() {
            try {
                end_serverSocket = new ServerSocket(END_PORT);
                while(true) {
                    socket = end_serverSocket.accept();
                    mBluetoothLeScanner.stopScan(mLeScanCallback);
                    Log.d(TAG,"scanLeDevice() stop BLE scan");
                    mBleDevicesList = new ArrayList<BluetoothDevice>();
                    stdListFromServer = new ArrayList<String>();
                    Log.d(TAG,"cleared list.....");
                    if(mGatt!=null) {
                        Log.d(TAG,"scanLeDevice() stop BLE scan");
                        mGatt.disconnect();
                        Log.d(TAG,"scanLeDevice() stop BLE scan");
                        mGatt.close();
                        Log.d(TAG,"scanLeDevice() stop BLE scan");
                        mGatt = null;
                    }
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private class StartServerThread extends Thread{
        Socket socket = null;
        public StartServerThread(Socket socket) {
            this.socket = socket;
        }
        @Override
        public void run() {
            try {
                while(true) {
                    br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                    String info = null;
                    if ((info = br.readLine()) != null) {
                        System.out.println("我是服务器，读取客户端发过来的 command：" + info);
                        stdListFromServer = new ArrayList<String>();
                        stdListFromServer.addAll(Arrays.asList(info.split("\\+")));
                        System.out.println("我是服务器，读取客户端发过来的 command：" +stdListFromServer.toString());
                        //20170513: LESCAN;
                        Log.d(TAG,"scanning ...");
                        mBluetoothLeScanner.startScan(bleScanFilters,bleScanSettings,mLeScanCallback);
                        while(true){
                            if(mScanning == false){
                                sleep(5000);
                                break;
                            }
                            sleep(5000);
                        }
                        Log.d(TAG,"stoped.....");
                        socket.shutdownInput();//关闭输出流
                        Log.d(TAG,"stoped shutdownInput.....");

                        // 关闭资源
                        br.close();
                        Log.d(TAG,"closed br.....");

                        socket.close();
                        Log.d(TAG,"colsed socket.....");
                        mScanning = true;

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private boolean myBLEinit(){
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Log.d(TAG, getResources().getString(R.string.error_bluetooth_not_supported));
            finish();
        }
        final BluetoothManager mBluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        if(!checkBluetoothSupport(mBluetoothAdapter)){
            return false;
        }
        if(mBluetoothAdapter ==  null){
            Log.d(TAG,getResources().getString(R.string.error_bluetooth_not_supported));
            return false;
        }
        if (!mBluetoothAdapter.isEnabled()) {
            Log.d(TAG, "Bluetooth is currently disabled...enabling");
            mBluetoothAdapter.enable();
        }
        serviceUUid = new ParcelUuid[1];
        serviceUUid[0] = ParcelUuid.fromString("00001234-0000-1000-8000-00805f9b34fb");

        bleScanFilters = scanFilters();
        mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
        if(mBluetoothLeScanner == null){
            Log.d("create()","mBluetoothLeScan is null");
            //finish();
            return false;
        }
        mBleScanSettingsBuild = new ScanSettings.Builder();
        bleScanSettings = mBleScanSettingsBuild.build();

        Log.d(TAG, "Starting scanning with settings:" + bleScanSettings + " and filters:" + bleScanFilters);
        mLeScanCallback = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);
                BluetoothDevice device = result.getDevice();
                Log.d(TAG, "Device name: " + device.getName());
                Log.d(TAG, "Device address: " + device.getAddress());
                Log.d(TAG, "Device service UUIDs: " + device.getUuids());

                ScanRecord record = result.getScanRecord();
                Log.d(TAG, "Record advertise flags: 0x" + Integer.toHexString(record.getAdvertiseFlags()));
                Log.d(TAG, "Record Tx power level: " + record.getTxPowerLevel());
                Log.d(TAG, "Record device name: " + record.getDeviceName());
                Log.d(TAG, "Record service UUIDs: " + record.getServiceUuids());
                Log.d(TAG, "Record service data: " + record.getServiceData().toString());
                addDevice(device);
            }
        };
        mBleDevicesList = new ArrayList<BluetoothDevice>();
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    private boolean checkBluetoothSupport(BluetoothAdapter bluetoothAdapter) {

        if (bluetoothAdapter == null) {
            Log.w(TAG, "Bluetooth is not supported");
            return false;
        }
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Log.w(TAG, "Bluetooth LE is not supported");
            return false;
        }

        return true;
    }
    private void checkPermission() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
                ) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_NETWORK_STATE,Manifest.permission.INTERNET,Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    builder.setMessage("이 앱은 권한없이는 사용할 수 없습니다.\n권한을 요청합니다")
                            .setCancelable(false)
                            .setPositiveButton("권한 허용", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    checkPermission();
                                }
                            })
                            .setNegativeButton("거부(종료)", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    System.exit(0);
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                return;
            }
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothLeScanner.stopScan(mLeScanCallback);
                    Log.d(TAG,"scanLeDevice() stop BLE scan");
                    for(BluetoothDevice device : mBleDevicesList)
                        connectToDevice(device);
                    //invalidateOptionsMenu();
                }
            }, scan_period);
            //mScanning = true;
            mBluetoothLeScanner.startScan(bleScanFilters, bleScanSettings, mLeScanCallback);
            Log.i(TAG,"scanLeDevice() start BLE scan");
        } else {
            //mScanning = false;
            Log.d(TAG,"scanLeDevice() start BLE scan");
            mBluetoothLeScanner.stopScan(mLeScanCallback);
        }
        //invalidateOptionsMenu();
    }
    public void addDevice(BluetoothDevice device) {
        if(!mBleDevicesList.contains(device)) {
            mBleDevicesList.add(device);
            //connectToDevice(device);
        }
        connectToDevice(device);
    }
    private List<ScanFilter> scanFilters() {
        ScanFilter stidFilter = new ScanFilter.Builder().setServiceUuid(serviceUUid[0]).build();
        List<ScanFilter> list = new ArrayList<ScanFilter>(1);
        list.add(stidFilter);
        return list;
    }
    public BluetoothDevice getDevice(int position) {
        return mBleDevicesList.get(position);
    }
    public void clear() {
        mBleDevicesList.clear();
    }
    public int getCount() {
        return mBleDevicesList.size();
    }
    public Object getItem(int i) {
        return mBleDevicesList.get(i);
    }


//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
//            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
//        } else {
//            if (Build.VERSION.SDK_INT >= 21) {
//                mLEScanner = mBluetoothAdapter.getBluetoothLeScanner();
//                settings = new ScanSettings.Builder()
//                        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
//                        .build();
//                filters = new ArrayList<ScanFilter>();
//            }
//            scanLeDevice(true);
//        }
//    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
//            scanLeDevice(false);
//        }
//    }
//

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_ENABLE_BT) {
//            if (resultCode == Activity.RESULT_CANCELED) {
//                //Bluetooth not enabled.
//                finish();
//                return;
//            }
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//

    public void connectToDevice(BluetoothDevice device) {
        Log.i(TAG,"GATT connecting ...");
        mGatt = device.connectGatt(this, false, gattCallback);
    }
    private final BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
        private int count = 0;
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            Log.i("onConnectionStateChange", "Status: " + status);
            switch (newState) {
                case BluetoothProfile.STATE_CONNECTED:
                    Log.i("gattCallback", "STATE_CONNECTED");
                    gatt.discoverServices();
                    break;
                case BluetoothProfile.STATE_DISCONNECTED:
                    Log.e("gattCallback", "STATE_DISCONNECTED");
                    break;
                default:
                    Log.e("gattCallback", "STATE_OTHER");
                    break;
            }

        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            List<BluetoothGattService> services = gatt.getServices();
            Log.i("onServicesDiscovered", services.toString());
            BluetoothGattService mGattService=  gatt.getService(SERVICE_UUID);
            BluetoothGattCharacteristic info = mGattService.getCharacteristic(INFO_UUID);
            if(info==null) {
                Log.i("Characteristic:", "null");
                gatt.disconnect();
            }
            else {
                gatt.readCharacteristic(info);
                Log.i("Characteristic: ", "onServiceDiscover()");
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic
                                                 characteristic, int status) {
            String gattMsg = characteristic.getStringValue(0);
            Log.i("onCharacteristicRead", gattMsg);
            if(stdListFromServer.contains(gattMsg)) {
                pw.println(gattMsg);
                pw.flush();
            }
            gatt.disconnect();
//            if(characteristic.getUuid().compareTo(INFO_UUID)==0) {
//                if (stdListFromServer.contains(gattMsg)) {
//                    pw.println(gattMsg);
//                    pw.flush();
//            }
//            else
//                gatt.disconnect();
        }
    };
    private class ServerManager extends Thread {
        private static final int RPI_PORT = 12340;
        private final int POOL_SIZE = 20;

        @Override
        public void run() {
            try {
                rpi_serverSocket = new ServerSocket(RPI_PORT);

                final TextView ipText = (TextView) findViewById(R.id.ip);
                Log.i(TAG, rpi_serverSocket.getInetAddress().getHostAddress());
                System.out.println("Started service");
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ipText.setText(getIpAddress());
                    }
                });
                int count = 0;
                // 2、调用()方法开始监听，等待客户端的连接

                while (true) {
                    Log.i(TAG, "***服务器即将启动，等待客户端的连接***");
                    Socket socket = rpi_serverSocket.accept();
                    myBLEinit();
                    Log.i(TAG, "accepted...");
                    StartServerThread startServerThread = new StartServerThread(socket);
                    startServerThread.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip += "SiteLocalAddress: "
                                + inetAddress.getHostAddress() + "\n";
                    }

                }

            }
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }

        return ip;
    }

    public void onClick (View view){
        switch(view.getId()){
            case R.id.stopButton:
                moveTaskToBack(false);
                stopScanning();
                // this.finish();
                break;
            default:
                break;
        }
        // System.exit(0);
    }
    @Override
    public void onDestroy(){
        if (rpi_serverSocket != null) {
            try {
                rpi_serverSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        Log.e(TAG,"onDestroy");
        super.onDestroy();
    }
}
