package com.bizan.mobile10.marumaru;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by user on 2016/01/28.
 */
public class QA extends AppCompatActivity implements View.OnClickListener{
    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.qa);

        //ボタンをウィジェットに登録
        Button qaAnsButton1 = (Button) findViewById(R.id.qaBtnAnswer1);
        qaAnsButton1.setOnClickListener(this);
        Button qaAnsButton2 = (Button) findViewById(R.id.qaBtnAnswer2);
        qaAnsButton2.setOnClickListener(this);
        Button qaAnsButton3 = (Button) findViewById(R.id.qaBtnAnswer3);
        qaAnsButton3.setOnClickListener(this);
        Button qaAnsButton4 = (Button) findViewById(R.id.qaBtnAnswer4);
        qaAnsButton4.setOnClickListener(this);

        //アンサーボタンの外枠を隠す
        ImageView qaImgStroke1 = (ImageView) findViewById(R.id.qaImgStroke1);
        qaImgStroke1.setVisibility(View.GONE);
        ImageView qaImgStroke2 = (ImageView) findViewById(R.id.qaImgStroke2);
        qaImgStroke2.setVisibility(View.GONE);
        ImageView qaImgStroke3 = (ImageView) findViewById(R.id.qaImgStroke3);
        qaImgStroke3.setVisibility(View.GONE);
        ImageView qaImgStroke4 = (ImageView) findViewById(R.id.qaImgStroke4);
        qaImgStroke4.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.qaBtnAnswer1:
                ImageView qaImgStroke1 = (ImageView) findViewById(R.id.qaImgStroke1);
                qaImgStroke1.setVisibility(View.VISIBLE);
                break;

            case R.id.qaBtnAnswer2:
                ImageView qaImgStroke2 = (ImageView) findViewById(R.id.qaImgStroke2);
                qaImgStroke2.setVisibility(View.VISIBLE);
                break;

            case R.id.qaBtnAnswer3:
                ImageView qaImgStroke3 = (ImageView) findViewById(R.id.qaImgStroke3);
                qaImgStroke3.setVisibility(View.VISIBLE);
                break;

            case R.id.qaBtnAnswer4:
                ImageView qaImgStroke4 = (ImageView) findViewById(R.id.qaImgStroke4);
                qaImgStroke4.setVisibility(View.VISIBLE);
                break;
        }
    }
}
