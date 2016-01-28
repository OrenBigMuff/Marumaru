package com.bizan.mobile10.marumaru;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Clear extends AppCompatActivity {

    private int zanmon;         //DBから取得した残問題数をセットします。（今井）

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear);

        //とりあえず残問数9問で作ってみるね（本番はここでDBから残問を取得してね。）
        zanmon = 9;

        LinearLayout cardLinear = (LinearLayout) this.findViewById(R.id.cardLinear);
        cardLinear.removeAllViews();

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.card_clear, null);
        CardView cardView = (CardView) linearLayout.findViewById(R.id.cardView);

        for (int i = 0; i < zanmon; i++) {
            LayoutInflater inflater2 = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            LinearLayout linearLayout2 = (LinearLayout) inflater.inflate(R.layout.card_clear, null);
            CardView cardView2 = (CardView) linearLayout.findViewById(R.id.cardView);
//            TextView textBox = (TextView) linearLayout.findViewById(R.id.textBox);
//            textBox.setText("CardView" + i);
            cardView.setTag(i);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Don't touch me!!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
            cardLinear.addView(linearLayout, i);
        }
    }
}
