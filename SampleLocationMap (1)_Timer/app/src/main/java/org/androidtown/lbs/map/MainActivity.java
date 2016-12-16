package org.androidtown.lbs.map;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

/**
 * 현재 위치의 지도를 보여주는 방법에 대해 알 수 있습니다.
 *
 * Google Play Services 라이브러리를 링크하여 사용합니다.
 * 구글맵 v2를 사용하기 위한 여러 가지 권한이 있어야 합니다.
 * 매니페스트 파일 안에 있는 키 값을 PC에 맞는 것으로 새로 발급받아서 넣어야 합니다.
 *
 * @author Mike
 */
public class MainActivity extends ActionBarActivity {

    private GoogleMap map;
    static Double latitude;
    static Double longitude;
    static DBHelper dbHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

                                      // 지도 객체 참조
        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        // 위치 확인하여 위치 표시 시작
        startLocationService();

        //MYLOGGER.db 생성
         dbHelper = new DBHelper(getApplicationContext(), "MYLOGGER.db", null, 1);

        //데이터베이스 인풋영역
        final EditText title =  (EditText) findViewById(R.id.databaseNameInput);
        final EditText content =  (EditText) findViewById(R.id.databaseNameInput2);
        final EditText categori =  (EditText) findViewById(R.id.databaseNameInput3);


        // DB에 데이터 추가
        Button save = (Button) findViewById(R.id.button3);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String input_title = title.getText().toString();
                String input_content = content.getText().toString();
                String input_categori = categori.getText().toString();

                dbHelper.insert(input_title, input_content, input_categori, latitude, longitude);
                Toast.makeText(getApplicationContext(), "데이터가 추가 되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });


    }

    //통계창으로 넘어가는 메소드
    public void onclick2(View v)
    {
        // 인텐트 객체를 만듭니다.
        Intent intent = new Intent(getApplicationContext(), statis.class);
        intent.putExtra("value", dbHelper.getResult());
        startActivity(intent);
    }
    //timer로 넘어감
    public void Timer(View v)
    {
        // 인텐트 객체를 만듭니다.
        Intent intent = new Intent(getApplicationContext(), Timer.class);
       // intent.putExtra("value", dbHelper.getResult());
        startActivity(intent);
    }

    /**
     * 현재 위치 확인을 위해 정의한 메소드
     */
    private void startLocationService() {
        // 위치 관리자 객체 참조
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // 리스너 객체 생성
        GPSListener gpsListener = new GPSListener();
        //메소드가 호출되는 최소시간 10초, 최소거리0
        long minTime = 10000;
        float minDistance = 0;

        // GPS 기반 위치 요청
        manager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTime,
                minDistance,
                gpsListener);

        // 네트워크 기반 위치 요청
        manager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                minTime,
                minDistance,
                gpsListener);

    }

    /**
     * 리스너 정의
     */
    private class GPSListener implements LocationListener {
        /**
         * 위치 정보가 확인되었을 때 호출되는 메소드
         */
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            String msg = "Latitude : "+ latitude + "\nLongitude:"+ longitude;
            Log.i("GPSLocationService", msg);

            // 현재 위치의 지도를 보여주기 위해 정의한 메소드 호출
            showCurrentLocation(latitude, longitude);
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

    }

    /**
     * 현재 위치의 지도를 보여주기 위해 정의한 메소드
     *
     * @param latitude
     * @param longitude
     */
    private void showCurrentLocation(Double latitude, Double longitude) {
        // 현재 위치를 이용해 LatLon 객체 생성
        LatLng curPoint = new LatLng(latitude, longitude);

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));

        // 지도 유형 설정. 지형도인 경우에는 GoogleMap.MAP_TYPE_TERRAIN, 위성 지도인 경우에는 GoogleMap.MAP_TYPE_SATELLITE
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
