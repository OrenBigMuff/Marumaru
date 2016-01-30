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
        implements OnClickListener {

    private int zanmon;         //DBから取得した残問題数をセットします。（今井）

    TextView zanmonTitle;       //残問カードのタイトル 第〇問 From DB
    TextView zanmonWord;        //残問カードの問題 PLAYBOY From DB
    TextView zanmonMean;        //残問カードの解答 遊び人 From DB

    //Buttonを動的にレイアウトする為のゴニョゴニョ
    private final static int WC = LinearLayout.LayoutParams.WRAP_CONTENT;
    private final static int MP = LinearLayout.LayoutParams.MATCH_PARENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear);

        //とりあえず残問数9問で作ってみるね（本番はここでDBから残問を取得してね。）
        zanmon = 9;


        LinearLayout cardLinear = (LinearLayout) this.findViewById(R.id.cardLinear);
        LinearLayout cardLinearZanmon = (LinearLayout) this.findViewById(R.id.cardLinearZanmon);
        cardLinear.removeAllViews();
        cardLinearZanmon.removeAllViews();

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.card_clear, null);
        CardView cardView = (CardView) linearLayout.findViewById(R.id.cardView);

        cardLinear.addView(linearLayout, 0);

        Log.v("Clearろぐ", "ここまでOK");


        for (int i = 1; i <= zanmon; i++) {
            LayoutInflater inflater2 = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            LinearLayout linearLayout2 = (LinearLayout) inflater2.inflate(R.layout.card_zanmon, null);
            CardView cardView2 = (CardView) linearLayout2.findViewById(R.id.cardView2);
            zanmonTitle = (TextView) linearLayout2.findViewById(R.id.zanmonTitle);
            zanmonWord = (TextView) linearLayout2.findViewById(R.id.zanmonWord);
            zanmonMean = (TextView) linearLayout2.findViewById(R.id.zanmonMean);
            zanmonTitle.setText("憶えていない単語 " + i);
            zanmonWord.setText("Not Remember Words " + i);
            zanmonMean.setText("Meaning of Words " + i);
            /*cardView2.setTag(i);
            cardView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Don't touch me!!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });*/

            cardLinearZanmon.addView(linearLayout2, i - 1);     //i=0からにするために　i-1
        }
        cardLinearZanmon.addView(makeButton("アプリ初期化ボタン", "Init"));
    }

    @Override
    public void onClick(View v) {
        //ここにイニシャライズの呪文(メソッド)を書き込む
        Log.v("Button_clr", "onClick");

    }

    public void initApp() {
        //イニシャライズ
    }

    /**
     * 苦肉の策でボタンを動的に追加する為のメソッドです とりあえずボタンの height = 150dp でご機嫌をうかがってます
     * @param text
     * @param tag
     * @return
     */
    private Button makeButton(String text, String tag) {
        Button button = new Button(this);
        button.setText(text);
        button.setTag(tag);
        button.setOnClickListener(this);
        button.setLayoutParams(new LinearLayout.LayoutParams(MP, 150));
        return button;
    }

    

}
