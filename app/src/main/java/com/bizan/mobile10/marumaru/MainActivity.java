package com.bizan.mobile10.marumaru;

import android.view.View;
import android.util.Log;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.os.Bundle;

import android.database.sqlite.SQLiteDatabase;
import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    //ブランチ
    private Button startButton;
    private Sound sound;
    private Button volButton;
    private DatabaseHelper dbHelper;
    private static SQLiteDatabase db;

    private PreferenceC pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.v("main", "onCreate");
        setContentView(R.layout.activity_main);


        startButton = (Button) findViewById(R.id.btnmastart);
        startButton.setOnClickListener(this);

        volButton = (Button) findViewById(R.id.btnmavol);
        volButton.setOnClickListener(this);

        /**
         * データベースの準備
         * this,DB_NAME,DB_VERSION,DB_TABLE,
         */

        String[] dbColTable = {
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " Question TEXT NOT NULL," +
                        " CorrectAnswer TEXT NOT NULL," +
                        " PhoneticSymbol TEXT NOT NULL," +
                        " PartOfSpeech TEXT NOT NULL," +
                        " IncorrectAnswer1 TEXT NOT NULL," +
                        " IncorrectAnswer2 TEXT NOT NULL," +
                        " IncorrectAnswer3 TEXT NOT NULL," +
                        " Level INTEGER NOT NULL," +
                        " QuestionFlag INTEGER NOT NULL," +
                        " CreateDate TEXT NOT NULL," +
                        " UpdateDate TEXT NOT NULL)"};
//        DatabaseHelper dbHelper = new DatabaseHelper(this, DB_NAME, DB_VERSION, DB_TABLE, dbColTable);
//        db = dbHelper.getWritableDatabase();

/*
        //登録が一軒もなければサンプルを登録バージョンで管理されるべき
        if(!isCheckDBQA()){
            //writeDBStore("eneos");
        }
*/
        pref = new PreferenceC(this);

        //サウンド設定
        sound = new Sound(this,R.raw.sample);
        sound.setSoundON(pref.readConfig("soundON", true));

    }

    @Override
    public void onResume(){
        super.onResume();

    }

    @Override
    public void onClick(View v) {
        if(v == startButton){
            Intent intent = new Intent(this, QA.class);
            startActivity(intent);
        }
        if (v == volButton) {
            if(sound.isSoundON()){
                Sound.setSoundON(false);
                volButton.setBackgroundResource(R.drawable.mute);
                pref.writeConfig("soundON", false);
            }else{
                Sound.setSoundON(true);
                volButton.setBackgroundResource(R.drawable.volume);
                pref.writeConfig("soundON", true);
            }
            sound.playSE();
        }
    }



//    public String[] getQu(){
//        return ;
//    }
}
