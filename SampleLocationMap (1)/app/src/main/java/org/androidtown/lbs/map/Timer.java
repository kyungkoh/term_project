package org.androidtown.lbs.map;

/**
 * Created by KOH on 2016-12-06.
 */
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.os.Build.ID;

public class Timer extends Activity {
    TextView mEllapse;
    TextView msplit;
    Button mBtnStart;
    Button mBtnSplit;

    final static int IDLE = 0;
    final static int RUNNING = 1;
    final static int PAUSE =2;

    int mStatus = IDLE;
    long mBaseTime;
    long mPauseTime;
    int msplitCount;

    protected  void onCrate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);

        mEllapse=(TextView)findViewById(R.id.ellapse);
        msplit= (TextView)findViewById(R.id.split);
        mBtnStart=(Button)findViewById(R.id.btnstart);
        mBtnSplit=(Button)findViewById(R.id.btnsplit);

    }

    Handler mTimer = new Handler(){
        public void handleMessage(android.os.Message msg){
            mEllapse.setText(getEllapse());
            mTimer.sendEmptyMessage(0);
        };
    };
    protected void onDestroy() {
        mTimer.removeMessages(0);
        super.onDestroy();
    }
    public void monClick(View v) {
        switch (v.getId()) {
            case R.id.btnstart:
                switch (mStatus) {
                    case IDLE:
                        mBaseTime = SystemClock.elapsedRealtime();
                        mTimer.sendEmptyMessage(0);
                        mBtnStart.setText("중지");
                        mBtnSplit.setEnabled(true);
                        mStatus = RUNNING;
                        break;


                    case RUNNING:
                        mTimer.removeMessages(0);
                        mPauseTime = SystemClock.elapsedRealtime();

                        mBtnStart.setText("시작");
                        mBtnSplit.setText("초기화");
                        mStatus = PAUSE;

                        break;
                    case PAUSE:
                        long now = SystemClock.elapsedRealtime();
                        mBaseTime += (now - mPauseTime);

                        mTimer.sendEmptyMessage(0);
                        mBtnStart.setText("중지");
                        mBtnSplit.setText("기록");
                        mStatus = RUNNING;
                        break;
                }
                break;
        }
    }
    String getEllapse(){
        long now = SystemClock.elapsedRealtime();
        long ell = (now - mBaseTime);
        String sEll =String.format("%02d:%02d:%02d",ell/1000/60/(ell/1000)%60,(ell%1000)/10);
        return sEll;

        }
    }

