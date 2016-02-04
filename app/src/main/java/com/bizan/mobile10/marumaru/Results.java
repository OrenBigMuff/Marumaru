package com.bizan.mobile10.marumaru;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by kei on 2016/01/28.
 */
public class Results extends AppCompatActivity {

    private static MainActivity mainActivity = new MainActivity();
    private static QA qA = new QA();

    private int[] mId = mainActivity.getId();                       //問題のID
    private int[] mCorrection = qA.getCorrection();                 //問題の正誤
    private String[] mQuestion = mainActivity.getQuestion();        //問題
    private String[] mAnswer = mainActivity.getCorrectAnswer();     //答え

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        if (mCorrection.length!=10) {
            //正誤の要素数が10個じゃなければスルー
            return;
        }else if (mQuestion.length!=10) {
            //問題の要素数が10個じゃなければスルー
            return;
        }else if (mAnswer.length!=10) {
            //答えの要素数が10個じゃなければスルー
            return;
        }

        //10問中の正解数を表示させる
        int correct = 0;
        for (int i = 0; i < 10; i++) {
            if (mCorrection[i]==1) {
                correct++;
            }
        }
        TextView txvResult = (TextView) this.findViewById(R.id.txvArResult);
        txvResult.setText("結果 : " + correct + "問" + " / 10問");

        //CardViewを表示させる
        LinearLayout cardLinear = (LinearLayout) this.findViewById(R.id.lilArCardLiner);
        cardLinear.removeAllViews();

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < 10; i++) {
            final LinearLayout snackbarLayout = (LinearLayout) findViewById(R.id.lilArlinearLayout);

            //正解の場合
            if (mCorrection[i]==1) {
                LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.card_correct, null);

                CardView cardView = (CardView) linearLayout.findViewById(R.id.cdvCcCard);

                //問題番号
                TextView txvNumber = (TextView) linearLayout.findViewById(R.id.txvCcNumber);
                txvNumber.setText("Question:" + (i+1));

                //単語
                TextView txvQuestion = (TextView) linearLayout.findViewById(R.id.txvCcQuestion);
                txvQuestion.setText(mQuestion[i]);

                //意味
                TextView txvAnswer = (TextView) linearLayout.findViewById(R.id.txvCcAnswer);
                txvAnswer.setText(mAnswer[i]);

                cardLinear.addView(linearLayout, i);

                //マグレボタン
                final Button mBtnFluke = (Button) cardView.findViewById(R.id.btnCcFluke);
                mBtnFluke.setTag(i);
                mBtnFluke.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mCorrection[Integer.parseInt(String.valueOf(v.getTag()))]==1) {
                            //スナックバーを表示させる
                            Snackbar.make(snackbarLayout,
                                    "Question:" + (Integer.parseInt(String.valueOf(v.getTag())) + 1) + " を再度出題します",
                                    Snackbar.LENGTH_SHORT).show();

                            //出題フラグを｢出題する｣へ
                            mCorrection[Integer.parseInt(String.valueOf(v.getTag()))] = 0;

                            //ボタンのテキストを｢出題停止｣へ変更
                            mBtnFluke.setText(R.string.questionstop_button);
                        }
                        else if (mCorrection[Integer.parseInt(String.valueOf(v.getTag()))]==0) {
                            //スナックバーを表示させる
                            Snackbar.make(snackbarLayout,
                                    "Question:" + (Integer.parseInt(String.valueOf(v.getTag())) + 1) + " を出題停止にします",
                                    Snackbar.LENGTH_SHORT).show();

                            //出題フラグを｢出題しない｣へ
                            mCorrection[Integer.parseInt(String.valueOf(v.getTag()))] = 1;

                            //ボタンのテキストを｢マグレ｣へ
                            mBtnFluke.setText(R.string.magure_button);
                        }
                    }
                });
            }

            //不正解の場合
            else if (mCorrection[i]==0) {
                LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.card_mistake, null);

                //問題番号
                TextView txvNumber = (TextView) linearLayout.findViewById(R.id.txvCmNumber);
                txvNumber.setText("Question:" + (i+1));

                //単語
                TextView txvQuestion = (TextView) linearLayout.findViewById(R.id.txvCmQuestion);
                txvQuestion.setText(mQuestion[i]);

                //意味
                TextView txvAnswer = (TextView) linearLayout.findViewById(R.id.txvCmAnswer);
                txvAnswer.setText(mAnswer[i]);

                cardLinear.addView(linearLayout, i);
            }
        }

        //TOPへ戻るボタン押下の動作
        Button btnReturn = (Button) this.findViewById(R.id.btnArReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Results.this.finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mId.length!=10) {
            //IDの要素数が10個じゃなければDBに書き込まずスルー
            return;
        }else if (mCorrection.length!=10) {
            //正誤の要素数が10個じゃなければDBに書き込まずスルー
            return;
        }

        //Databaseへ出題フラグの登録
        DatabaseC dbC = new DatabaseC(MainActivity.getDbHelper(), MainActivity.getDB_TABLE());
        dbC.updateQuestionFlag(mId, mCorrection);
    }
}

