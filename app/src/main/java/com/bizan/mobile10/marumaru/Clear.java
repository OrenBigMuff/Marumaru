package com.bizan.mobile10.marumaru;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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

    private int numZanmon;         //DBから取得した残問題数をセットします。（今井）

    TextView zanmonTitle;       //残問カードのタイトル 第〇問 From DB
    TextView zanmonWord;        //残問カードの問題 PLAYBOY From DB
    TextView zanmonMean;        //残問カードの解答 遊び人 From DB

    //Buttonを動的にレイアウトする為のゴニョゴニョ
//    private final static int WC = LinearLayout.LayoutParams.WRAP_CONTENT;
    private final static int MP = LinearLayout.LayoutParams.MATCH_PARENT;

    //仮初めの定数
    private final static String DB_NAME = "QA.db";
    private final static String DB_TABLE = "Question_Answer";
    private final static int DB_VERSION = 1;
    //仮初めの定数ここまで

    //仮初めの変数
    private SQLiteDatabase db;
    //仮初めの変数ここまで


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear);

        //DBオブジェクトの取得
        DBHelper dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();    //データベースオブジェクト

        //とりあえず残問数9問で作ってみるね（本番はここでDBから残問を取得してね。）←最終的に消す
        numZanmon = 9;

        //DBから残問数を取得するにはこちら
/*        try {
            numZanmon = readZanmon();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        //DBから残問数を取得する_ここまで


        Zanmon zanmon[] = new Zanmon[numZanmon];


        //残問配列にDBの値を格納していく
        for(int i=0; i<numZanmon; i++){
            zanmon[i] = new Zanmon(i, "", "", "");

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


        for (int i = 1; i <= numZanmon; i++) {
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
        DatabaseC dbC = new DatabaseC(MainActivity.getDbHelper(), MainActivity.getDB_TABLE());
        dbC.reset();
        this.finish();
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

    private class Zanmon{
        int tag;
        String title;
        String question;
        String mean;

        Zanmon(int tag, String title, String question, String mean){
            this.tag = tag;
            this.title = title;
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
