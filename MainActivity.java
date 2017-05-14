package honeycoconut.sam;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.content.Intent;
import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.UUID;

import static android.bluetooth.BluetoothGattCharacteristic.PERMISSION_READ;
import static android.bluetooth.BluetoothGattCharacteristic.PERMISSION_WRITE;

public class Main2Activity extends AppCompatActivity {
    private static final int REQUEST_ENABLE_BT = 1;
    private static final String TAG = "HoneyCoconut";

    private static final UUID ATTENDANCE_SERVICE_UUID = UUID
            .fromString("00001234-0000-1000-8000-00805F9B34FB");
    private static final UUID STUDENT_INFO_UUID = UUID
            .fromString("00001235-0000-1000-8000-00805f9b34fb");
    private static final UUID ATTENDANCE_CONFIRM_UUID = UUID
            .fromString("00001236-0000-1000-8000-00805f9b34fb");


    private int student_number=2014105089;
    private TextView mAdvStatus;
    private TextView mConnectionStatus;
    private Button mButton;
    private BluetoothGattService mBluetoothGattService;
    private BluetoothGattCharacteristic mBluetoothGattCharacteristic;
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private AdvertiseData mAdvData;
    private AdvertiseData mAdvScanResponse;
    private AdvertiseSettings mAdvSettings;
    private BluetoothLeAdvertiser mAdvertiser;

    private final AdvertiseCallback mAdvCallback = new AdvertiseCallback() {
        @Override
        public void onStartFailure(int errorCode) {
            super.onStartFailure(errorCode);
            Log.e(TAG, "Not broadcasting: " + errorCode);
            String statusText;
            statusText = "오류";
            Log.wtf(TAG, "error: " + errorCode);
            mAdvStatus.setText(statusText);
        }

        @Override
        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            super.onStartSuccess(settingsInEffect);
            Log.v(TAG, "Broadcasting");
            mAdvStatus.setText("Broadcasting");
        }
    };

    private BluetoothGattServer mGattServer;
    private final BluetoothGattServerCallback mGattServerCallback = new BluetoothGattServerCallback() {
        @Override
        public void onConnectionStateChange(BluetoothDevice device, final int status, int newState) {
            super.onConnectionStateChange(device, status, newState);
            if (newState == BluetoothGatt.STATE_CONNECTED) {
                updateConnectedDevicesStatus();
                Log.v(TAG, "Connected to device: " + device.getAddress());
            } else if (newState == BluetoothGatt.STATE_DISCONNECTED) {
                updateConnectedDevicesStatus();
                Log.v(TAG, "Disconnected from device");
            }
        }

        @Override
        public void onCharacteristicReadRequest(BluetoothDevice device, int requestId, int offset,
                                                BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicReadRequest(device, requestId, offset, characteristic);
            Log.d(TAG, "Device tried to read characteristic: " + characteristic.getUuid());
            Log.d(TAG, "Value: " + Arrays.toString(characteristic.getValue()));
            if (offset != 0) {
                mGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_INVALID_OFFSET, offset, null);
                return;
            }
            mGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, characteristic.getValue());
            Log.d(TAG, "Cancel connection with Device: " + device.getAddress() + " " + device.getName());
            mGattServer.cancelConnection(device);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();

        mButton = (Button)findViewById(R.id.button_advertise);
        mButton.setText("Stop");
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mButton.getText()=="Stop"){
                    mButton.setText("Advertise");
                    mAdvertiser.stopAdvertising(mAdvCallback);
                    disconnectFromDevices();
                    resetStatusViews();
                }else{
                    mAdvertiser.startAdvertising(mAdvSettings, mAdvData, mAdvScanResponse, mAdvCallback);
                    mButton.setText("Stop");

                }
            }
        });
        mAdvStatus = (TextView) findViewById(R.id.textView_advertisingStatus);
        mConnectionStatus = (TextView) findViewById(R.id.textView_connectionStatus);

        mBluetoothGattService = new BluetoothGattService(ATTENDANCE_SERVICE_UUID,
                BluetoothGattService.SERVICE_TYPE_PRIMARY);
        mBluetoothGattCharacteristic = new BluetoothGattCharacteristic(STUDENT_INFO_UUID,
                BluetoothGattCharacteristic.PROPERTY_READ, PERMISSION_READ);
        mBluetoothGattCharacteristic.setValue("2014105089");
        mBluetoothGattService.addCharacteristic(mBluetoothGattCharacteristic);

//        mBluetoothGattCharacteristic2 = new BluetoothGattCharacteristic(ATTENDANCE_CONFIRM_UUID,
//                BluetoothGattCharacteristic.PROPERTY_WRITE, PERMISSION_WRITE);
//        mBluetoothGattCharacteristic2.setValue("0");
//        mBluetoothGattService.addCharacteristic(mBluetoothGattCharacteristic);

        mAdvSettings = new AdvertiseSettings.Builder()
                .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_BALANCED)
                .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_MEDIUM)
                .setConnectable(true)
                .build();
        mAdvData = new AdvertiseData.Builder()
                .setIncludeTxPowerLevel(true)
                .addServiceUuid(new ParcelUuid(ATTENDANCE_SERVICE_UUID))
                .build();
        mAdvScanResponse = new AdvertiseData.Builder()
                .setIncludeDeviceName(false)
                .build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                if (!mBluetoothAdapter.isMultipleAdvertisementSupported()) {
                    Toast.makeText(this, "Advertising not supported", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "Advertising not supported");
                }
                onStart();
            } else {
                Toast.makeText(this, "Bluetooth not enabled", Toast.LENGTH_LONG).show();
                Log.e(TAG, "Bluetooth not enabled");
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetStatusViews();
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        mGattServer = mBluetoothManager.openGattServer(this, mGattServerCallback);
        mGattServer.addService(mBluetoothGattService);

        if (mButton.getText()=="Stop") {
            mAdvertiser = mBluetoothAdapter.getBluetoothLeAdvertiser();
            mAdvertiser.startAdvertising(mAdvSettings, mAdvData, mAdvScanResponse, mAdvCallback);
        } else {
            mAdvStatus.setText("Not Broadcasting");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGattServer != null) {
            mGattServer.close();
        }
        if (mBluetoothAdapter.isEnabled() && mAdvertiser != null) {
            //mAdvertiser.stopAdvertising(mAdvCallback);
        }
        resetStatusViews();
    }

    @Override
    protected void onStop() {
        super.onStop();
        disconnectFromDevices();
    }

    private void resetStatusViews() {
        mAdvStatus.setText("Not Advertising");
        updateConnectedDevicesStatus();
}

    private void disconnectFromDevices() {
        Log.d(TAG, "Disconnecting devices...");
        for (BluetoothDevice device : mBluetoothManager.getConnectedDevices(
                BluetoothGattServer.GATT)) {
            Log.d(TAG, "Devices: " + device.getAddress() + " " + device.getName());

            mGattServer.cancelConnection(device);
        }
    }
    private void updateConnectedDevicesStatus() {
        final String message = "Number of devices connected: "
        + mBluetoothManager.getConnectedDevices(BluetoothGattServer.GATT).size();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mConnectionStatus.setText(message);
            }
        });
    }
}

