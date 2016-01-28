package com.bizan.mobile10.marumaru;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        final CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox);
//        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked) {
//                    Toast.makeText(getApplication(), "チェックが付いた", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(getApplication(), "チェックが外れた", Toast.LENGTH_LONG).show();
//                }
//            }
//        });


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



