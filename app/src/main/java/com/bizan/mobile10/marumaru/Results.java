package com.bizan.mobile10.marumaru;

import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        //10問中の正解数を表示させる
        TextView txvResult = (TextView) this.findViewById(R.id.txvArResult);
        txvResult.setText("結果 : " + "正解数" + "問" + " / 10問");

        //CardViewを表示させる
        LinearLayout cardLinear = (LinearLayout) this.findViewById(R.id.lilArCardLiner);
        cardLinear.removeAllViews();

        for (int i = 0; i < 10; i++) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            if (正誤の配列[i]==1) {      //正解
                LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.card_correct, null);

                CardView cardView = (CardView) linearLayout.findViewById(R.id.cdvCmCard);

                //問題番号
                TextView txvNumber = (TextView) linearLayout.findViewById(R.id.txvCcNumber);
                txvNumber.setText("問題 " + (i+1));

                //単語
                TextView txvQuestion = (TextView) linearLayout.findViewById(R.id.txvCcQuestion);
                txvQuestion.setText(問題の配列[i]);

                //意味
                TextView txvAnswer = (TextView) linearLayout.findViewById(R.id.txvCmAnswer);
                txvAnswer.setText(解答の配列[i]);

                //マグレボタン
                Button btnFluke = (Button) this.findViewById(R.id.btnCcFluke);
                btnFluke.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        正誤の配列[i] = 0;
                    }
                });

                cardView.setTag(i);
                cardLinear.addView(linearLayout, i);
            }else if (正誤の配列[i]==0) {        //不正解
                LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.card_mistake, null);

                CardView cardView = (CardView) linearLayout.findViewById(R.id.cdvCmCard);

                //問題番号
                TextView txvNumber = (TextView) linearLayout.findViewById(R.id.txvCcNumber);
                txvNumber.setText("問題 " + (i+1));

                //単語
                TextView txvQuestion = (TextView) linearLayout.findViewById(R.id.txvCcQuestion);
                txvQuestion.setText(問題の配列[i]);

                //意味
                TextView txvAnswer = (TextView) linearLayout.findViewById(R.id.txvCmAnswer);
                txvAnswer.setText(解答の配列[i]);

                cardView.setTag(i);
                cardLinear.addView(linearLayout, i);
            }
        }

        //TOPへ戻るボタン押下の動作
        Button btnReturn = (Button) this.findViewById(R.id.btnArReturn);
        //btnReturn.
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
