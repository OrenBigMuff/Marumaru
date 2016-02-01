package com.bizan.mobile10.marumaru;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by user on 2016/01/28.
 */
public class QA extends AppCompatActivity implements View.OnClickListener,Animation.AnimationListener{

    private Button button;
    private ImageView imv;
    private Sound sound;
    private Button volumeButton;

    private PreferenceC pref;

    private LinearLayout animeL;

    private AnimatorSet animator;

    private String[] ca;

    String[] qu;

    String[][] ia;

    private Button[] btnQA; //回答ボタン

    private ImageView[] imvStroke   //外枠
    private ImageView[] imvMaru;    //○✖画像
    private ImageView[] imvBatu;    //○✖画像


    //上にあるテキストビューの問題数配列作成
    private String QuestionNo[] = new String[]{"QuestionNo:1","QuestionNo:2",
            "QuestionNo:3","QuestionNo:4","QuestionNo:5","QuestionNo:6",
            "QuestionNo:7","QuestionNo:9","QuestionNo:9","QuestionNo:10"};

    private String Kotae;

    //SoundPool(効果音再生)
    private SoundPool mSoundPool;
    private int[] mSoundId = new int[2];    //２つ分

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        //タイトルバー非表示
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.qa);

        //ボタンをウィジェットに登録

        btnQA = new Button[4];
        btnQA[0] = (Button) findViewById(R.id.qaBtnAnswer1);
        btnQA[1] = (Button) findViewById(R.id.qaBtnAnswer2);
        btnQA[2] = (Button) findViewById(R.id.qaBtnAnswer3);
        btnQA[3] = (Button) findViewById(R.id.qaBtnAnswer4);
        for(int i = 0; i < btnQA.length ; i++){
            btnQA[i].setOnClickListener(this);
        }


        //アンサーボタンの外枠を隠す
        imvStroke = new ImageView[4];
        imvStroke[0] = (ImageView) findViewById(R.id.qaImgStroke1);
        imvStroke[1] = (ImageView) findViewById(R.id.qaImgStroke2);
        imvStroke[2] = (ImageView) findViewById(R.id.qaImgStroke3);
        imvStroke[3] = (ImageView) findViewById(R.id.qaImgStroke4);
        for(int i = 0; i < imvStroke.length ; i++){
            imvStroke[i].setVisibility(View.GONE);
        }

        //○✖画像非表示
        imvMaru = new ImageView[4];
        imvMaru[0] = (ImageView) findViewById(R.id.imgMaru1);
        imvMaru[1] = (ImageView) findViewById(R.id.imaMaru2);
        imvMaru[2] = (ImageView) findViewById(R.id.imgMaru3);
        imvMaru[3] = (ImageView) findViewById(R.id.imgMaru4);
        for(int i = 0; i < imvMaru.length ; i++){
            imvMaru[i].setVisibility(View.GONE);
        }

        imvBatu = new ImageView[4];
        imvBatu[0] = (ImageView) findViewById(R.id.imgBatu1);
        imvBatu[1] = (ImageView) findViewById(R.id.imgBatu2);
        imvBatu[2] = (ImageView) findViewById(R.id.imgBatu3);
        imvBatu[3] = (ImageView) findViewById(R.id.imgBatu4);
        for(int i = 0; i < imvBatu.length ; i++){
            imvBatu[i].setVisibility(View.GONE);
        }


        //ボリュームボタンウェジェットに登録
        volumeButton = (Button) findViewById(R.id.volumeBtn2);
        volumeButton.setOnClickListener(this);

        //LinearKayoutをウェジェットに登録
        animeL = (LinearLayout) findViewById(R.id.animationL);

        pref = new PreferenceC(this);

        //サウンド設定
        //sound = new sound(mSoundId);
        sound.setSoundON(pref.readConfig("soundON", true));

        //正解
        String[] ca = MainActivity.getCorrectAnswer();

        //問題
        String[] qu = MainActivity.getQuestion();
        String Question = qu[0];   //問題文
        //誤答セット
        String[][] ia = MainActivity.getIncorrectAnswer();
    }

    @Override
    protected void onResume(){
        super.onResume();

        //効果音を使えるように読み込み
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        mSoundId[0] = mSoundPool.load(getApplicationContext(),R.raw.seikai1, 1);
        mSoundId[1] = mSoundPool.load(getApplicationContext(),R.raw.hazure1, 1);

        //((TextView)findViewById(R.id.qaTxv1)).setText(QuestionNo);

        //問題文セット処理　呼び出し
        //setOuestion();

    }

    private void questionStart(){
        int[] array
    }

    //問題文セット処理
    private void setOuestion(){
//        //作成したDatabaseHelperクラスに読み取り専用でアクセス
//        DatabaseHelper dbHelper =
//        SQLiteDatabase db = deHelper.getReadableDatabase();
//
//        /*
//        SELECT文
//        テーブル名「Question_Answer」からidがマッチする項目を取得する条件式
//        */
//        String sql = "SELECT Question, Answer1, Answer2, Answer3, Answer4 FROM Question_Answer WHERE _id";
//

        //データベースから取ってきたデータを変数にセット

        ((TextView)findViewById(R.id.questionTxv)).setText(Question);   //問題文をテキストビューに表示
        ((Button)findViewById(R.id.qaBtnAnswer1)).setText(Answer1);     //選択肢をボタンに表示
        ((Button)findViewById(R.id.qaBtnAnswer2)).setText(Answer2);     //選択肢をボタンに表示
        ((Button)findViewById(R.id.qaBtnAnswer3)).setText(Answer3);     //選択肢をボタンに表示
        ((Button)findViewById(R.id.qaBtnAnswer4)).setText(Answer4);     //選択肢をボタンに表示




    }

    @Override
    public void onClick(View v) {
        //１０回回す
        for (int i=0; i < QuestionNo.length; i++) {

            //アニメーション開始
            AnimationLinear();

            //上のテキストに現在問題数を表示
            ((TextView)findViewById(R.id.qaTxv1)).setText(QuestionNo[i]);

            //押されたボタンのテキストと正解を比較
            if (((Button) v).getText().equals(Kotae)) {

                //正解の効果音処理
                mSoundPool.play(mSoundId[0], 1.0f, 1.0f, 0, 0, 1.0f);   //正解音再生

                //○✖画像表示
                if (((Button) v).getText()==Kotae){

                }else {

                }

                //データベース更新処理
                ContentValues values = new ContentValues();
                //フラグ 0から1
                values.put("QuestionFlag", 1);
                //カラム選択
                String whereClause = "_id = ?";
                //データベースと接続
                DatabaseHelper dbHelper = new DatabaseHelper(this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                //データベース更新
                int ret;
                try {
                    ret = db.update("Question_Answer", values, whereClause,
                            null);
                } finally {
                    db.close();
                }
                if (ret == -1) {
                } else {
                }

            } else {
                //不正解の効果音処理
                mSoundPool.play(mSoundId[1], 1.0f, 1.0f, 0, 0, 1.0f);   //不正解音

                //○✖画像表示
                if (((Button) v).getText()==Kotae){

                }else {

                }

            }
        }

        //ボリュームボタン処理
        if (v == volumeButton){
            if(sound.isSoundON()){
                Sound.setSoundON(false);
                volumeButton.setBackgroundResource(R.drawable.mute);
                pref.writeConfig("soundON", false);
            }else{
                Sound.setSoundON(true);
                volumeButton.setBackgroundResource(R.drawable.volume);
                pref.writeConfig("soundON", true);
            }
        }
    }

    //効果音に関するものは全て開放
    @Override
    protected  void onPause(){
        super.onPause();
        //SoundPool開放
        mSoundPool.unload(mSoundId[0]);
        mSoundPool.unload(mSoundId[1]);

        mSoundPool.release();
    }

    private void AnimationLinear(){
        ObjectAnimator scaledown = ObjectAnimator.ofFloat(animeL, "scaleX", 1.0f, 0.0f);
        scaledown.setDuration(3000);
        animeL.setPivotX(0);
        scaledown.setInterpolator(new LinearInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaledown);
        animatorSet.start();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
