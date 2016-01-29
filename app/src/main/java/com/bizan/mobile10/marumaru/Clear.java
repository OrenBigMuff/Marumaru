package com.bizan.mobile10.marumaru;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Clear extends AppCompatActivity
implements OnClickListener{

    private int zanmon;         //DBから取得した残問題数をセットします。（今井）
    Button btnInit;             //初期化を実行するメガンテボタン

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear);

        //とりあえず残問数9問で作ってみるね（本番はここでDBから残問を取得してね。）
        zanmon = 9;


        btnInit = (Button)findViewById(R.id.btnInit);
        btnInit.setOnClickListener(this);

        LinearLayout cardLinearZanmon = (LinearLayout) this.findViewById(R.id.cardLinearZanmon);
        cardLinearZanmon.removeAllViews();

/*
        LinearLayout cardLinear = (LinearLayout) this.findViewById(R.id.cardLinear);
        LinearLayout cardLinearZanmon = (LinearLayout) this.findViewById(R.id.cardLinearZanmon);
        cardLinear.removeAllViews();
        cardLinearZanmon.removeAllViews();

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.card_clear, null);
        CardView cardView = (CardView) linearLayout.findViewById(R.id.cardView);

        cardLinear.addView(linearLayout);
*/

        Log.v("Clearろぐ","ここまでOK");

  /*      LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.card_clear, null);
        CardView cardView = (CardView) linearLayout.findViewById(R.id.cardView);
        cardView.setTag(1);
        cardLinear.addView(linearLayout, 1);*/


        for (int i = 1; i <= zanmon; i++) {
            LayoutInflater inflater2 = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            LinearLayout linearLayout2 = (LinearLayout) inflater2.inflate(R.layout.card_zanmon, null);
//            LinearLayout linearLayout2 = (LinearLayout) inflater2.inflate(R.layout.card_zanmon, null);
            CardView cardView2 = (CardView) linearLayout2.findViewById(R.id.cardView);
            TextView zanmonTitle = (TextView) linearLayout2.findViewById(R.id.zanmonTitle);
            zanmonTitle.setText("Not Remember " + i);
            /*cardView2.setTag(i);
            cardView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Don't touch me!!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });*/

            cardLinearZanmon.addView(linearLayout2, i-1);
        }
    }

    @Override
    public void onClick(View v) {
        if(v == btnInit){
            //ここにイニシャライズの呪文(メソッド)を書き込む
            Log.v("Button_clr","onClick");
        }
        return;
    }

    public void initApp(){

    }

}
