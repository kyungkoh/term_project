package com.example.koh.myapplication;



import android.app.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    Button btnStopService;

    Intent intentMyService;

    BroadcastReceiver receiver;

    boolean flag = true;

    Toast toast;

    TextView CountText;

    String serviceData;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        intentMyService = new Intent(this, MyServiceIntent.class);
        // 실행되기 원하는 서비스 등록

        receiver = new MyMainLocalRecever();

        CountText = (TextView) findViewById(R.id.textView01);

        btnStopService = (Button) findViewById(R.id.btnStopService);
        // 서비스 중지

        btnStopService.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (flag) {

                    btnStopService.setText("Stop !!");

                    // TODO Auto-generated method stub
                    try {

                        IntentFilter mainFilter = new IntentFilter(
                                "com.androday.test.step");

                        registerReceiver(receiver, mainFilter);

                        startService(intentMyService);
                        // txtMsg.setText("After stoping Service:\n"+service.getClassName());
                        Toast.makeText(getApplicationContext(), "서비스 시작", 1)
                                .show();
                    } catch (Exception e) {
                        // TODO: handle exception
                        Toast.makeText(getApplicationContext(), e.getMessage(),
                                1).show();
                    }
                }

                else {

                    btnStopService.setText("Go !!");

                    // TODO Auto-generated method stub
                    try {

                        unregisterReceiver(receiver);

                        stopService(intentMyService);

                        Toast.makeText(getApplicationContext(), "서비스 중지", 1)
                                .show();
                        // txtMsg.setText("After stoping Service:\n"+service.getClassName());
                    } catch (Exception e) {
                        // TODO: handle exception
                        Toast.makeText(getApplicationContext(), e.getMessage(),
                                1).show();
                    }
                }

                flag = !flag;

            }
        });

    }

    class MyMainLocalRecever extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub

            serviceData = intent.getStringExtra("serviceData");

            CountText.setText(serviceData);

            Toast.makeText(getApplicationContext(), "Walking . . . ", 1).show();

        }

    }

}
