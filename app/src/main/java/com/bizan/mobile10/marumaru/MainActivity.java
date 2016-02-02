package com.bizan.mobile10.marumaru;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //hhideyyuki//
    //ブランチ
    private static final int QNUM = 10;

    private Button startButton;
    private Sound sound;
    private Button volButton;
    private PreferenceC pref;

    private DatabaseC dbC;

    private final String DB_NAME = "QA.db";
    private final int DB_VERSION = 1;

    private static final String[] DB_TABLE = {"Question_Answer"};

    public static String getDB_TABLE() {
        return DB_TABLE[0];
    }

    private static DatabaseHelper dbHelper;

    public static DatabaseHelper getDbHelper() {
        return dbHelper;
    }

    private static int[] id = new int[QNUM];
    private static String[] question = new String[QNUM];
    private static String[] correctAnswer = new String[QNUM];
    private static String[][] incorrectAnswer = new String[QNUM][3];

    /**
     * 出題される_id
     *
     * @return int[]={11,45,8,,,}
     */
    public static int[] getId() {
        return id;
    }

    /**
     * 問題文
     *
     * @return String[]={java,android,PHP,,,}
     */
    public static String[] getQuestion() {
        return question;
    }

    /**
     * 正解
     * Stirng[] str = ManiActivity.getCorrectAnswer();
     * @return String[]={ジャバ,アンドロイド,ピーエイチピー,,,}
     */
    public static String[] getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * 誤答セット
     *
     * @return String[][]={{しゃば,ざば,じょば},
     * {アンド,ロドイ,ロイド}
     * {デオキシリボ核酸,ドコサヘキサエン酸,世界保健機関}}
     */
    public static String[][] getIncorrectAnswer() {
        return incorrectAnswer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        long startTime = System.currentTimeMillis();

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
        dbHelper = new DatabaseHelper(this, DB_NAME, DB_VERSION, DB_TABLE, dbColTable);
        dbC = new DatabaseC(MainActivity.getDbHelper(), MainActivity.getDB_TABLE());

        //登録が一軒もなければサンプルを登録バージョンで管理されるべき
        if (!dbC.isCheckDBQA()) {
            toast("DB登録");
            if (dbC.firstInsert(this) == -1) {
                toast("DB登録できませんでした。");
            }
        }

        //コンフィグ使う準備
        pref = new PreferenceC(this);

        //サウンド設定
        sound = new Sound(this, R.raw.see);
        sound.setSoundON(pref.readConfig("soundON", true));

        //初回起動であればここでCommentary
//        if(pref.readConfig("comm", false)){
//            Intent intent = new Intent(this, QA.class);
//            startActivity(intent);
//        }

        long endTime = System.currentTimeMillis();
        Log.e("time", (endTime - startTime)+"");
    }

    @Override
    public void onResume() {
        super.onResume();
        //問題セット10問未満ならクリア画面を表示する
        if (!qset()) {
            Intent intent = new Intent(this, QA.class);
            startActivity(intent);
        }

        //boolean a = dbC.updateQuestionFlag(2, 1);
        //dbC.reset();
        Log.e("countAllRow", dbC.countAllRow() + "@" + dbC.countZanmon());

    }

    private void toast(String text) {
        if (text == null) {
            text = "";
        }
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v == startButton) {
            Intent intent = new Intent(this, QA.class);
            startActivity(intent);
        }
        if (v == volButton) {
            if (sound.isSoundON()) {
                Sound.setSoundON(false);
                volButton.setBackgroundResource(R.drawable.marumaru_sound_off);
                pref.writeConfig("soundON", false);
            } else {
                Sound.setSoundON(true);
                volButton.setBackgroundResource(R.drawable.marumaru_sound_on);
                pref.writeConfig("soundON", true);
            }
            sound.playSE();
        }
    }

    private boolean qset() {
        Cursor cursor = dbC.qset(String.valueOf(QNUM));
        int i = 0;
        if (cursor.getCount() < 10) {
            cursor.close();
            return false;
        } else {
            while (cursor.moveToNext()) {
                id[i] = Integer.parseInt(cursor.getString(0));
                question[i] = cursor.getString(1);
                correctAnswer[i] = cursor.getString(2);
                incorrectAnswer[i][0] = cursor.getString(3);
                incorrectAnswer[i][1] = cursor.getString(4);
                incorrectAnswer[i][2] = cursor.getString(5);
                i++;
            }
        }
        cursor.close();
        return true;
    }

    public void onDestroy(){
        super.onDestroy();
        //sound.releaseSE();
        dbC.closeDB();
    }
}
