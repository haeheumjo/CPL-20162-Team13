package com.example.meihao.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

public class RequestPermission extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_permission);
        checkPermission();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
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
    private void checkPermission() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
                ) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_NETWORK_STATE,Manifest.permission.INTERNET,Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},1);
        }
    }
}
