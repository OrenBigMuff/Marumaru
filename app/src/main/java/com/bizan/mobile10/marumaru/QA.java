package com.bizan.mobile10.marumaru;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Arrays;

/**
 * Created by user on 2016/01/28.
 */
public class QA extends AppCompatActivity implements View.OnClickListener, Animator.AnimatorListener {
    private int count = 0;                  //現在の問題
    private static final int COUNT_Q = 10;  //1サイクルの問題数

    private PreferenceC pref;               //プリファレンス利用のためのクラス

    private String[] correct;               //正解の配列を入れる
    private String[] question;              //問題の配列を入れる
    private String[][] incorrect;           //選択肢配列を入れる

    public static int[] getCorrection() {   //次ページ用の配列とそのゲッター
        return correction;
    }

    private static int[] correction = new int[COUNT_Q];
    private int correctionF = 0;            //正解不正解のフラッグ

    private boolean btnF = true;                   //連打防止ボタンフラグ

    //レイアウト
    private Button volumeButton;
    private Button[] btnQA;         //回答ボタン
    private ImageView[] imvStroke;  //外枠
    private ImageView[] imvMaru;    //○✖画像
    private ImageView[] imvBatu;    //○✖画像

    private TextView qaTextViwe;
    private TextView questionTextView;

    //上にあるテキストビューの問題数配列作成
    private String QuestionNo[] = new String[]{"QuestionNo:1", "QuestionNo:2",
            "QuestionNo:3", "QuestionNo:4", "QuestionNo:5", "QuestionNo:6",
            "QuestionNo:7", "QuestionNo:8", "QuestionNo:9", "QuestionNo:10"};

    //サウンドリソース
    private Sound soundSeikai;
    private Sound soundHazure;

    //アニメーション
    private LinearLayout animeL;
    private AnimatorSet animatorSet;
    private Handler handler;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        //タイトルバー非表示
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.qa);

        //プリファレンス利用準備
        pref = new PreferenceC(this);

        qaTextViwe = (TextView) findViewById(R.id.qaTxv1);
        questionTextView = (TextView) findViewById(R.id.questionTxv);

        //ボタンをウィジェットに登録
        btnQA = new Button[4];
        btnQA[0] = (Button) findViewById(R.id.qaBtnAnswer1);
        btnQA[1] = (Button) findViewById(R.id.qaBtnAnswer2);
        btnQA[2] = (Button) findViewById(R.id.qaBtnAnswer3);
        btnQA[3] = (Button) findViewById(R.id.qaBtnAnswer4);
        for (int i = 0; i < btnQA.length; i++) {
            btnQA[i].setOnClickListener(this);
        }

        //アンサーボタンの外枠
        imvStroke = new ImageView[4];
        imvStroke[0] = (ImageView) findViewById(R.id.qaImgStroke1);
        imvStroke[1] = (ImageView) findViewById(R.id.qaImgStroke2);
        imvStroke[2] = (ImageView) findViewById(R.id.qaImgStroke3);
        imvStroke[3] = (ImageView) findViewById(R.id.qaImgStroke4);


        //○✖画像非表示
        imvMaru = new ImageView[4];
        imvMaru[0] = (ImageView) findViewById(R.id.imgMaru1);
        imvMaru[1] = (ImageView) findViewById(R.id.imaMaru2);
        imvMaru[2] = (ImageView) findViewById(R.id.imgMaru3);
        imvMaru[3] = (ImageView) findViewById(R.id.imgMaru4);

        imvBatu = new ImageView[4];
        imvBatu[0] = (ImageView) findViewById(R.id.imgBatu1);
        imvBatu[1] = (ImageView) findViewById(R.id.imgBatu2);
        imvBatu[2] = (ImageView) findViewById(R.id.imgBatu3);
        imvBatu[3] = (ImageView) findViewById(R.id.imgBatu4);

        //ボリュームボタンウェジェットに登録
        volumeButton = (Button) findViewById(R.id.volumeBtn2);
        volumeButton.setOnClickListener(this);
        if (pref.readConfig("soundON", true)) {
            volumeButton.setBackgroundResource(R.drawable.marumaru_sound_on);
        } else {
            volumeButton.setBackgroundResource(R.drawable.marumaru_sound_off);
        }

        //LinearKayoutをウェジェットに登録
        animeL = (LinearLayout) findViewById(R.id.animationL);


        //サウンド設定
        soundSeikai = new Sound(this, R.raw.seikai1);
        soundHazure = new Sound(this, R.raw.hazure1);
        soundSeikai.setSoundON(pref.readConfig("soundON", true));

        //正解
        correct = MainActivity.getCorrectAnswer();
        //問題
        question = MainActivity.getQuestion();
        //誤答セット
        incorrect = MainActivity.getIncorrectAnswer();

        //スレッド利用のためのハンドラー
        handler = new Handler();
        if (correct == null || question == null || incorrect == null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            QA.this.finish();
        }

        init();
        AnimationLinear();
        setOuestion();
        animStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (int i = 0; i < correction.length; i++) {
            correction[i] = 0;
        }
    }


    /**
     * アンサーボタン外枠の初期化
     * 丸バツ画像の非表示
     */
    private void init() {
        //アンサーボタンの外枠を隠す
        for (int i = 0; i < imvStroke.length; i++) {
            imvStroke[i].setVisibility(View.GONE);
        }
        //○✖画像非表示
        for (int i = 0; i < imvMaru.length; i++) {
            imvMaru[i].setVisibility(View.GONE);
        }
        for (int i = 0; i < imvBatu.length; i++) {
            imvBatu[i].setVisibility(View.GONE);
        }

    }

    /**
     * 問題文、選択肢セット
     */
    private void setOuestion() {
        //一時保存用の配列
        int[] arrayNum = new int[3];
        //-1で初期化　のちに0-2が入るのでそれ以外の数で初期化
        for (int i = 0; i < arrayNum.length; i++) {
            arrayNum[i] = -1;
        }
        if (COUNT_Q > count) {
            init();

            qaTextViwe.setText(QuestionNo[count]);
            questionTextView.setText(question[count]);
            for (int i = 0; i < 3; ) {
                int randNum = new java.util.Random().nextInt(4);
                if (isCheckNum(arrayNum, randNum)) {
                    //なにもしない
                } else {
                    arrayNum[i] = randNum;
                    btnQA[randNum].setText(incorrect[count][i]);
                    btnQA[randNum].setTag("false");
                    i++;
                }
            }
            for (int i = 0; i < 4; i++) {
                if (isCheckNum(arrayNum, i)) {
                    //なにもしない
                } else {
                    btnQA[i].setText(correct[count]);
                    btnQA[i].setTag("true");
                    i++;
                }
            }
        } else {
            if(count != (COUNT_Q * 2)) {
                Intent intent = new Intent(QA.this, Results.class);
                startActivity(intent);
                QA.this.finish();
            }
        }
        count++;
    }

    private boolean isCheckNum(int[] arrayNum, int checkNum) {
        for (int i = 0; i < arrayNum.length; i++) {
            if (arrayNum[i] == checkNum) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if (btnF && v != volumeButton) {
            btnF = false;
            if (v == btnQA[0]) {
                imvStroke[0].setVisibility(View.VISIBLE);
            } else if (v == btnQA[1]) {
                imvStroke[1].setVisibility(View.VISIBLE);
            } else if (v == btnQA[2]) {
                imvStroke[2].setVisibility(View.VISIBLE);
            } else if (v == btnQA[3]) {
                imvStroke[3].setVisibility(View.VISIBLE);
            }

            if (v.getTag().equals("true")) {
                correctionF = 1;
            }
            if (v.getTag().equals("false")) {
                correctionF = 0;
            }
            if (animatorSet != null) {
                animatorSet.cancel();
            }
        }

        //ボリュームボタン処理
        if (v == volumeButton) {
            if (soundSeikai.isSoundON()) {
                Sound.setSoundON(false);
                volumeButton.setBackgroundResource(R.drawable.marumaru_sound_off);
                pref.writeConfig("soundON", false);
            } else {
                Sound.setSoundON(true);
                volumeButton.setBackgroundResource(R.drawable.marumaru_sound_on);
                pref.writeConfig("soundON", true);
            }
            soundSeikai.playSE();
        }

    }

    private void sleepThread() {
        if (count == (COUNT_Q * 2)) {
            return;
        }
        btnF = false;
        if (correctionF == 0) {
            soundHazure.playSE();
            correction[count - 1] = 0;
        } else if (correctionF == 1) {
            soundSeikai.playSE();
            correction[count - 1] = 1;
        }
        correctionF = 0;

        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        imageChange();
                        if (animatorSet != null) {
                            animatorSet.cancel();
                            animatorSet = null;
                        }
                    }
                });

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        btnF = true;
                        AnimationLinear();
                        setOuestion();
                        animStart();
                    }
                });
            }
        }).start();
        Log.e("question", Arrays.toString(correction));
    }

    private void imageChange() {
        for (int i = 0; i < 4; i++) {
            if (btnQA[i].getTag().equals("true")) {
                imvMaru[i].setVisibility(View.VISIBLE);
            } else if (btnQA[i].getTag().equals("false")) {
                imvBatu[i].setVisibility(View.VISIBLE);
            }
        }
    }

    //効果音に関するものは全て開放
    @Override
    protected void onPause() {
        super.onPause();
        if (animatorSet != null) {
            animatorSet.cancel();
            animatorSet = null;
        }
        soundSeikai.releaseSE();
        soundHazure.releaseSE();
        //いきなりResultsに移動する
        count = COUNT_Q * 2;
    }

    /**
     * アニメーションをスタートさせる
     */
    private void animStart() {
        if (count <= COUNT_Q) {
            animatorSet.start();
        }
    }

    /**
     * アニメーションの準備
     */
    private void AnimationLinear() {
        ObjectAnimator scaledown = ObjectAnimator.ofFloat(animeL, "scaleX", 1.0f, 0.0f);
        scaledown.setDuration(3000);
        animeL.setPivotX(0);
        scaledown.setInterpolator(new LinearInterpolator());

        animatorSet = new AnimatorSet();
        animatorSet.addListener(this);
        animatorSet.play(scaledown);
    }

    @Override
    public void onAnimationStart(Animator animation) {
    }

    /**
     * アニメーションが終わるかクリックされたときここを通る
     *
     * @param animation
     */
    @Override
    public void onAnimationEnd(Animator animation) {
        sleepThread();
    }

    @Override
    public void onAnimationCancel(Animator animation) {
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
    }
}
