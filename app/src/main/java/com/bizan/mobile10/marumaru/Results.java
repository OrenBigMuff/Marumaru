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
import android.widget.Toast;

/**
 * Created by kei on 2016/01/28.
 */
public class Results extends AppCompatActivity {

    private int[] id = {1,2,3,4,5,6,7,8,9,10};
    private int[] correction = {1,0,0,1,0,1,1,1,0,0};
    private String[] question = {"Question1","Question2","Question3","Question4","Question5",
            "Question6","Question7","Question8","Question9","Question10"};
    private String[] answer = {"意味1","意味2","意味3","意味4","意味5","意味6","意味7","意味8","意味9","意味10"};
    private Button btnFluke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        //10問中の正解数を表示させる
        int correct = 0;
        for (int i = 0; i < 10; i++) {
            if (correction[i]==1) {
                correct++;
            }
        }
        TextView txvResult = (TextView) this.findViewById(R.id.txvArResult);
        txvResult.setText("結果 : " + correct + "問" + " / 10問");

        //CardViewを表示させる
        LinearLayout cardLinear = (LinearLayout) this.findViewById(R.id.lilArCardLiner);
        cardLinear.removeAllViews();

        for (int i = 0; i < 10; i++) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

            //正解の場合
            if (correction[i]==1) {
                LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.card_correct, null);

                CardView cardView = (CardView) linearLayout.findViewById(R.id.cdvCcCard);

                //問題番号
                TextView txvNumber = (TextView) linearLayout.findViewById(R.id.txvCcNumber);
                txvNumber.setText("問題 " + (i+1));

                //単語
                TextView txvQuestion = (TextView) linearLayout.findViewById(R.id.txvCcQuestion);
                txvQuestion.setText(question[i]);

                //意味
                TextView txvAnswer = (TextView) linearLayout.findViewById(R.id.txvCcAnswer);
                txvAnswer.setText(answer[i]);

                cardView.setTag(i);
                cardLinear.addView(linearLayout, i);

                //マグレボタン
                btnFluke = (Button) cardView.findViewById(R.id.btnCcFluke);
                btnFluke.setTag(i);
                btnFluke.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (correction[Integer.parseInt(String.valueOf(v.getTag()))]==1) {
                            Snackbar.make(mParentLayout,
                                    Integer.parseInt(String.valueOf(v.getTag())) + 1) + " を再度出題します",
                                    Snackbar.LENGTH_SHORT).show();
                            Toast.makeText(Results.this,
                                    "Question" + (Integer.parseInt(String.valueOf(v.getTag())) + 1) + " を再度出題します",
                                    Toast.LENGTH_SHORT).show();
                            correction[Integer.parseInt(String.valueOf(v.getTag()))] = 0;
                            btnFluke.setText("出題停止");
                        }
                        else if (correction[Integer.parseInt(String.valueOf(v.getTag()))]==0) {
                            correction[Integer.parseInt(String.valueOf(v.getTag()))] = 1;
                            btnFluke.setText("マグレ");
                        }
                    }
                });
            }

            //不正解の場合
            else if (correction[i]==0) {
                LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.card_mistake, null);

                CardView cardView = (CardView) linearLayout.findViewById(R.id.cdvCmCard);

                //問題番号
                TextView txvNumber = (TextView) linearLayout.findViewById(R.id.txvCmNumber);
                txvNumber.setText("問題 " + (i+1));

                //単語
                TextView txvQuestion = (TextView) linearLayout.findViewById(R.id.txvCmQuestion);
                txvQuestion.setText(question[i]);

                //意味
                TextView txvAnswer = (TextView) linearLayout.findViewById(R.id.txvCmAnswer);
                txvAnswer.setText(answer[i]);

                cardView.setTag(i);
                cardLinear.addView(linearLayout, i);
            }
        }

        //TOPへ戻るボタン押下の動作
        Button btnReturn = (Button) this.findViewById(R.id.btnArReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        String result = "";
        for (int i = 0; i < 10; i++) {
            result = result + correction[i] +", ";
        }
        Toast.makeText(this,result,Toast.LENGTH_LONG).show();
    }
}
