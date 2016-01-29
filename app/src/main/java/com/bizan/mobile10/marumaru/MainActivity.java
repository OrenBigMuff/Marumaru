package com.bizan.mobile10.marumaru;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        final CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
//        checkBox.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                if (checkBox.isChecked() == true) {
//                    // チェックされた状態の時の処理を記述
//                } else {
//                    // チェックされていない状態の時の処理を記述
//                }
//            }
//        });



        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            //音を消す
            @Override
            public void onClick(View view) {

            }
        });


        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            // TOP画面を表示
            @Override
            public void onClick(View view) {

            }
        });


        LinearLayout cardLinear = (LinearLayout) this.findViewById(R.id.cardLinear);
        cardLinear.removeAllViews();

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.test_card, null);
        CardView cardView = (CardView) linearLayout.findViewById(R.id.cardView);
        TextView textBox = (TextView) linearLayout.findViewById(R.id.textBox);
        textBox.setText("このアプリを使う上での注意点。なんとかかんとか" +
                "なんとかかんとかでなんとかである。以上！");
        cardView.setTag(0);
        cardLinear.addView(linearLayout, 0);


    }

}



