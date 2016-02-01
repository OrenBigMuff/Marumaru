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

    private Button button;
    private ImageView imv;
    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.qa);

        //ボタンをウィジェットに登録
        button = (Button) findViewById(R.id.qaBtnAnswer1);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.qaBtnAnswer2);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.qaBtnAnswer3);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.qaBtnAnswer4);
        button.setOnClickListener(this);

        //アンサーボタンの外枠を隠す
        imv = (ImageView) findViewById(R.id.qaImgStroke1);
        imv.setVisibility(View.GONE);
        imv = (ImageView) findViewById(R.id.qaImgStroke2);
        imv.setVisibility(View.GONE);
        imv = (ImageView) findViewById(R.id.qaImgStroke3);
        imv.setVisibility(View.GONE);
        imv = (ImageView) findViewById(R.id.qaImgStroke4);
        imv.setVisibility(View.GONE);

        //○✖画像非表示
        imv = (ImageView) findViewById(R.id.imgMaru1);
        imv.setVisibility(View.GONE);
        imv = (ImageView) findViewById(R.id.imaMaru2);
        imv.setVisibility(View.GONE);
        imv = (ImageView) findViewById(R.id.imgMaru3);
        imv.setVisibility(View.GONE);
        imv = (ImageView) findViewById(R.id.imgMaru4);
        imv.setVisibility(View.GONE);
        imv = (ImageView) findViewById(R.id.imgBatu1);
        imv.setVisibility(View.GONE);
        imv = (ImageView) findViewById(R.id.imgBatu2);
        imv.setVisibility(View.GONE);
        imv = (ImageView) findViewById(R.id.imgBatu3);
        imv.setVisibility(View.GONE);
        imv = (ImageView) findViewById(R.id.imgBatu4);
        imv.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.qaBtnAnswer1:
                imv = (ImageView) findViewById(R.id.qaImgStroke1);
                imv.setVisibility(View.VISIBLE);

            case R.id.qaBtnAnswer2:
                imv = (ImageView) findViewById(R.id.qaImgStroke2);
                imv.setVisibility(View.VISIBLE);

            case R.id.qaBtnAnswer3:
                imv = (ImageView) findViewById(R.id.qaImgStroke3);
                imv.setVisibility(View.VISIBLE);

            case R.id.qaBtnAnswer4:
                imv = (ImageView) findViewById(R.id.qaImgStroke4);
                imv.setVisibility(View.VISIBLE);
        }
    }
}
