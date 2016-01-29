package com.bizan.mobile10.marumaru;



import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * 短い音再生
 * ActivitiとresIDを渡す
 * sound = new Sound(this, R.raw.u001);
 * 音のオン：ture ミュート:false
 * sound.setSoundON(true);
 *
 * playSE()で音なる
 * sound.playSE();
 *
 * アクティビティ閉じるときに入れとくのが望ましい
 * sound.releaseSE();
 */
public class Sound {

    private SoundPool soundPool;            //サウンドプール(短い音)
    private int soundId;                    //サウンドID
    private static boolean soundON;         //音のオン：ture ミュート:false

    //コンストラクタ


    public Sound(Activity activity, int resID) {
        //サウンドプールの生成
        /**
         * サウンドムールの生成
         * maxStream    同時再生の最大数
         * streamType   ストリーム種別     AudioManager.STREAM_MUSIC
         * srcQuality   現在意味なし
         */
        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);

        //サウンドプールにサウンドを読み込む
        /**
         * SoundPool.load
         * Context  コンテキスト
         * resID    りそーすID
         * priority 現在意味なし
         * 戻り値  サウンドID
         */
        soundId = soundPool.load(activity, resID, 1);

    }

    public static boolean isSoundON() {
        return soundON;
    }

    public static void setSoundON(boolean soundON) {
        Sound.soundON = soundON;
    }

    public void playSE() {
        //サウンドプールの再生
        /**
         * サウンドID
         * 左ボリューム
         * 右ボリューム
         * 優先順位0＜1...
         * ループ-1無限ループ0ループ無し
         * 再生速度0.5～2.0
         * 戻り値：ストリームID
         */
        if (soundON) {
            soundPool.play(soundId, 100, 100, 1, 0, 1);
        }
    }

    public void releaseSE() {
        //サウンドプールの開放
        soundPool.release();
    }
}
