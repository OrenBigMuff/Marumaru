package com.bizan.mobile10.marumaru;

import android.util.Log;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.v("main", "onCreate");
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.btnTest);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("Button_main", "onClick");
                // Intentの生成
                Intent intent = new Intent(MainActivity.this, Clear.class);
                // 次のアクティビィ起動
                startActivity(intent);
            }
        });
    }
}
