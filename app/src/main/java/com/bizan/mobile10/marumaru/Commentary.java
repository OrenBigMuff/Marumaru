package com.bizan.mobile10.marumaru;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class Commentary extends AppCompatActivity {
    CheckBox checkBox;
    Sound sound;
    PreferenceC pref;
    private int magureCheck = 1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commentary);
        LinearLayout cardLinear = (LinearLayout) this.findViewById(R.id.cardLinear);
        cardLinear.removeAllViews();

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.card_commentary, null);
        final CardView cardView = (CardView) linearLayout.findViewById(R.id.cardView);
        cardView.setTag(0);
        cardLinear.addView(linearLayout, 0);

        //マグレボタン
        final Button btnMgure = (Button) cardView.findViewById(R.id.btnMgure);
        final RelativeLayout rtlCommentaryLayout = (RelativeLayout) findViewById(R.id.rtlCommentaryLayout);
        btnMgure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (magureCheck == 1) {
                    //スナックバーを表示させる
                    Snackbar.make(rtlCommentaryLayout,
                            "マグレボタンを再出題ボタンへ変更します",
                            Snackbar.LENGTH_SHORT).show();

                    //出題フラグを｢出題する｣へ
                    magureCheck = 0;

                    //ボタンのテキストを｢出題停止｣へ変更
                    btnMgure.setText(R.string.questionstop_button);
                    btnMgure.setBackgroundResource(R.drawable.re_question_button);
                    btnMgure.setTextColor(Color.parseColor("#ff8000"));
                } else if (magureCheck == 0) {
                    //スナックバーを表示させる
                    Snackbar.make(rtlCommentaryLayout,
                            "再出題ボタンをマグレボタンへ変更します",
                            Snackbar.LENGTH_SHORT).show();

                    //出題フラグを｢出題しない｣へ
                    magureCheck = 1;

                    //ボタンのテキストを｢マグレ｣へ
                    btnMgure.setText(R.string.magure_button);
                    btnMgure.setBackgroundResource(R.drawable.start_button_back_color);
                    btnMgure.setTextColor(Color.WHITE);
                }
            }
        });

        //checkbox
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked() == true) {
                    // チェックされた状態の時の処理を記述
                } else {
                    // チェックされていない状態の時の処理を記述
                }
            }
        });
//        pref = new PreferenceC(this);
        final PreferenceC pref = new PreferenceC(this);
//        音楽ファイル入れて名前書く.
        sound = new Sound(this, R.raw.click1);


        final Button button = (Button) findViewById(R.id.button);
        if (sound.isSoundON()) {
            button.setBackgroundResource(R.drawable.marumaru_sound_on);
            //iconChange
        } else {
            button.setBackgroundResource(R.drawable.marumaru_sound_off);
            //iconChange
        }
        button.setOnClickListener(new View.OnClickListener() {
            //音を消す
            @Override
            public void onClick(View view) {
                if (sound.isSoundON()) {
                    sound.setSoundON(false);
                    button.setBackgroundResource(R.drawable.marumaru_sound_off);
                    //iconChange
                } else {
                    sound.setSoundON(true);
                    button.setBackgroundResource(R.drawable.marumaru_sound_on);
                    //iconChange
                }
                pref.writeConfig("soundON", sound.isSoundON());
                sound.playSE();
            }
        });


        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            // TOP画面を表示
            @Override
            public void onClick(View view) {
                pref.writeConfig("comm", checkBox.isChecked());
                Commentary.this.finish();
            }
        });


    }

}



