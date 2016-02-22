package com.bizan.mobile10.marumaru;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.design.widget.CoordinatorLayout;
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

import java.io.IOException;

public class Clear extends AppCompatActivity
        implements OnClickListener {

    MediaPlayer mp;
    private PreferenceC pref;               //プリファレンス

    Snackbar snackbar;              //スナックバー
    private int numZanmon;         //DBから取得した残問題数をセットします。（今井）

    TextView zanmonTitle;       //残問カードのタイトル 第〇問 From DB
    TextView zanmonWord;        //残問カードの問題 PLAYBOY From DB
//    TextView zanmonMean;        //残問カードの解答 遊び人 From DB

    DatabaseC dbC = new DatabaseC(MainActivity.getDbHelper(), MainActivity.getDB_TABLE());

    //残問の配列（受け）
    String zamWord[] = MainActivity.getQuestion();
    String zamMean[] = MainActivity.getCorrectAnswer();

    //Buttonを動的にレイアウトする為のゴニョゴニョ
//    private final static int WC = LinearLayout.LayoutParams.WRAP_CONTENT;
    private final static int MP = LinearLayout.LayoutParams.MATCH_PARENT;

    private Button volButton;               //サウンドボタン
    private Button btnInit;                 //Initボタン

    private CoordinatorLayout mCoodinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear);

        mCoodinatorLayout = (CoordinatorLayout)findViewById(R.id.cdL);

        //コンフィグ使う準備
        pref = new PreferenceC(this);
        pref.writeConfig("clear", false);

//BGM読込
        try
        {
            mp = MediaPlayer.create(this, R.raw.closeyoureyes);
            mp.setLooping(true);

        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }

        volButton = (Button) this.findViewById(R.id.btnmavol_cl);
        volButton.setOnClickListener(this);
        btnInit = (Button) this.findViewById(R.id.btnInit);
        btnInit.setOnClickListener(this);


        if(!pref.readConfig("soundON", true)){
            volButton.setBackgroundResource(R.drawable.marumaru_sound_off);
            mp.pause();
        }
        mp.start();




        //DBから残問数を取得するにはこちら
        numZanmon = dbC.countZanmon();
        //DBから残問数を取得する_ここまで

        if(numZanmon == 0){

            LinearLayout cardLinear = (LinearLayout) this.findViewById(R.id.cardLinear);
            LinearLayout cardLinearZanmon = (LinearLayout) this.findViewById(R.id.cardLinearZanmon);
            cardLinear.removeAllViews();
            cardLinearZanmon.removeAllViews();

            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.card_clear, null);
            TextView clearTitle = (TextView) linearLayout.findViewById(R.id.textBox2);
            clearTitle.setText(R.string.clear_text2);

            cardLinear.addView(linearLayout, 0);

            //苦肉の策でカードビューの尻にボタンをくっつけてお茶を濁します、、、
//            cardLinearZanmon.addView(makeButton("アプリ初期化ボタン", 1));

        }else{

            final Zanmon zanmon[] = new Zanmon[numZanmon];

            //残問配列にDBの値を格納していく・・・tagいらんかったね(笑)
            for(int i=0; i<numZanmon; i++){
                zanmon[i] = new Zanmon(i, zamWord[i], zamMean[i]);
            }



            LinearLayout cardLinear = (LinearLayout) this.findViewById(R.id.cardLinear);
            LinearLayout cardLinearZanmon = (LinearLayout) this.findViewById(R.id.cardLinearZanmon);
            cardLinear.removeAllViews();
            cardLinearZanmon.removeAllViews();

            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.card_clear, null);
            CardView cardView = (CardView) linearLayout.findViewById(R.id.cardView);

            cardLinear.addView(linearLayout, 0);

            Log.v("Clearろぐ", "ここまでOK");


            //for文でせっせとCardViewを作っていきます。
            for (int i = 0; i < numZanmon; i++) {
                LayoutInflater inflater2 = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                LinearLayout linearLayout2 = (LinearLayout) inflater2.inflate(R.layout.card_zanmon, null);
                CardView cardView2 = (CardView) linearLayout2.findViewById(R.id.cardView2);
                zanmonTitle = (TextView) linearLayout2.findViewById(R.id.zanmonTitle);
                zanmonWord = (TextView) linearLayout2.findViewById(R.id.zanmonWord);
//                zanmonMean = (TextView) linearLayout2.findViewById(R.id.zanmonMean);
                int j = i + 1;
                zanmonTitle.setText("憶えていない単語 その" + j);
                zanmonWord.setText(zanmon[i].question);
//                zanmonMean.setText(zanmon[i].mean);

                //スナックバーアクションを割り当てたいときは以下を追加
            cardView2.setTag(i);
            cardView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int x = (int) view.getTag();
                    String tmp = zanmon[x].mean;
                    snackbar = Snackbar.make(mCoodinatorLayout, "answer:   " + tmp , Snackbar.LENGTH_INDEFINITE);
                    TextView textView = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.rgb(236,104,0));
                    textView.setTextSize(20);
//                    snackbar.setActionTextColor(Color.rgb(236,104,0));
                    snackbar.getView().setBackgroundColor(Color.rgb(126, 206, 244));
                    snackbar.setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            snackbar.dismiss();
                        }
                    })
                            .show();
                }
            });

                cardLinearZanmon.addView(linearLayout2, i);     //i=0からにするために　i-1
            }
            LinearLayout blankLayout = new LinearLayout(this);
//            blankLayout.setBackgroundColor(Color.WHITE);
            blankLayout.setLayoutParams(new LinearLayout.LayoutParams(MP, 150));
            cardLinearZanmon.addView(blankLayout);
        }

    }

    @Override
    protected void onResume(){
        super.onResume();
        if(!pref.readConfig("soundON", true)){
            volButton.setBackgroundResource(R.drawable.marumaru_sound_off);
            mp.pause();
        }
        mp.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        if (mp.isPlaying()) {
//            mp.pause();
//            pref.writeConfig("soundON", false);
//        } else {
//            pref.writeConfig("soundON", true);
//        }
    }

    public void onDestroy() {
        super.onDestroy();
        //dbC.closeDB();
        mp.release();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnInit:
            //ここにイニシャライズの呪文(メソッド)を書き込む
            Log.v("Button_clr", "onClick");
            DatabaseC dbC = new DatabaseC(MainActivity.getDbHelper(), MainActivity.getDB_TABLE());
            dbC.reset();
            mp.release();
                pref.writeConfig("clear", true);
            Clear.this.finish();
                break;
            case R.id.btnmavol_cl:
                if (mp.isPlaying()) {
                    volButton.setBackgroundResource(R.drawable.marumaru_sound_off);
                    mp.pause();
                    pref.writeConfig("soundON", false);
                } else {
                    volButton.setBackgroundResource(R.drawable.marumaru_sound_on);
                    mp.start();
                    pref.writeConfig("soundON", true);
                }
                break;
        }
    }

    /**
     * 苦肉の策でボタンを動的に追加する為のメソッドです とりあえずボタンの height = 150dp でご機嫌をうかがってます
     * @param text
     * @param id
     * @return
     */
    private Button makeButton(String text, int id) {
        Button button = new Button(this);
        button.setText(text);
        button.setId(id);
        button.setOnClickListener(this);
        button.setLayoutParams(new LinearLayout.LayoutParams(MP, 150));
        return button;
    }

    private class Zanmon{
        int tag;
        String question;
        String mean;

        Zanmon(int tag, String question, String mean){
            this.tag = tag;
            this.question = question;
            this.mean = mean;
        }
    }

/*    //DBからの読み込みメソッド
    private String readDB() throws Exception{
        Cursor c = db.query(DB_TABLE, new String[]{"question", "correct_answer", "question_flag"}, "question_flag='0'", null, null, null, null);
        if(c.getCount() == 0)throw new Exception();
        c.moveToFirst();
        String str = c.getString(1);
        c.close();
        return str;
    }

    private int readZanmon() throws Exception{
        Cursor c = db.query(DB_TABLE, new String[]{"question", "correct_answer", "question_flag"}, "question_flag='0'", null, null, null, null);
        if(c.getCount() == 0)throw new Exception();
        c.moveToFirst();
        int num_zan = c.getCount();
        c.close();
        return num_zan;
    }*/

/*
    *//**
     * 仮初めのDBHelper
     *//*
    private class DBHelper extends SQLiteOpenHelper{
        //コンストラクタ
        public DBHelper(Context context){
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }*/
}
