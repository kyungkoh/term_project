package org.androidtown.lbs.map;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class statis extends ActionBarActivity {

    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statis);

        Button backButton = (Button) findViewById(R.id.backButton);
        result = (TextView) findViewById(R.id.result);

        Intent intent = getIntent();
        String data = intent.getStringExtra("value");
        result.setText(data);

        // 버튼을 눌렀을 때 메인 액티비티로 돌아갑니다.
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent resultIntent = new Intent();

                finish();
            }
        });
    }
}
