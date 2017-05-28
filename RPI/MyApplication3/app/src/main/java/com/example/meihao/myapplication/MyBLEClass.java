package com.example.meihao.myapplication;

import java.util.UUID;

/**
 * Created by meihao on 2017-05-24.
 */

public class MyBLEClass {
    private static final UUID RD_UUID = UUID.fromString("00001235-0000-1000-8000-00805f9b34fb");
    private static final UUID WR_UUID = UUID.fromString("00001235-0000-1000-8000-00805f9b34fb");
    private static final UUID SERVICE_UUID = UUID.fromString("00001234-0000-1000-8000-00805f9b34fb");
    private final static String TAG = MyBLEClass.class.getSimpleName();
    private static final String WD_CHAR = "FALSE";
}