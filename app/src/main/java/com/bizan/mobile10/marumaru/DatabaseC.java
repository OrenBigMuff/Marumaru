package com.bizan.mobile10.marumaru;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;
/**
 * @author yyukihhide
 * @version 0.5
 */

/**
 * 今回は丸コピでOK
 * DatabaseC dbC = new DatabaseC(MainActivity.getDbHelper(), MainActivity.getDB_TABLE());
 * 第一引数にSQLiteOpenHelper継承したDBHelper、第二引数にテーブル（今回は1テーブルなので手抜き）
 * <p/>
 * あとは、
 * dbC.readDB();
 * などすると使える。
 */
public class DatabaseC {
    private String dbTable = "";
    private static SQLiteDatabase db;

    /**
     * DatabaseC dbC = new DatabaseC(MainActivity.getDbHelper(), MainActivity.getDB_TABLE());
     *
     * @param dbHelper
     * @param dbTable
     */
    public DatabaseC(DatabaseHelper dbHelper, String dbTable) {
        this.db = dbHelper.getWritableDatabase();
        this.dbTable = dbTable;
    }

    public void closeDB(){
        db.close();
    }

    /**
     * 初回起動時のみ利用
     *
     * @return long 登録失敗-1、登録件数
     */
    public long firstInsert(Context context) {
        AssetManager assetManager = context.getResources().getAssets();
        int i = 0;
        try {
            InputStream inputStream = assetManager.open("qa.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, ",");
                ContentValues contentValues = new ContentValues();
                contentValues.put("Question", st.nextToken());
                contentValues.put("CorrectAnswer", st.nextToken());
                contentValues.put("PhoneticSymbol", st.nextToken());
                contentValues.put("PartOfSpeech", st.nextToken());
                contentValues.put("IncorrectAnswer1", st.nextToken());
                contentValues.put("IncorrectAnswer2", st.nextToken());
                contentValues.put("IncorrectAnswer3", st.nextToken());
                contentValues.put("Level", Integer.parseInt(st.nextToken()));
                contentValues.put("QuestionFlag", Integer.parseInt(st.nextToken()));
                contentValues.put("CreateDate", st.nextToken());
                contentValues.put("UpdateDate", st.nextToken());
                if (db.insert(dbTable, "", contentValues) == -1) {
                    return -1;
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * @return boolean 登録あり：true、0件:false
     */
    public boolean isCheckDBQA() {
        Cursor cursor = db.query(dbTable, new String[]{"_id"},
                null, null, null, null, null);
        if (cursor.getCount() == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * テーブルの確認にどうぞ
     *
     * @return Cursor
     */
    public Cursor readDB() {
        Cursor cursor = db.query(dbTable, new String[]{
                        "_id", "Question",
                        "CorrectAnswer", "PhoneticSymbol", "PartOfSpeech", "IncorrectAnswer1", "IncorrectAnswer2",
                        "IncorrectAnswer3", "Level", "QuestionFlag", "CreateDate", "UpdateDate"},
                null, null, null, null, null);
        return cursor;
    }

    /**
     * 問題セット用
     *
     * @param qnum 　問題数
     * @return
     */
    public Cursor qset(String qnum) {
        String[] sqlD = {qnum};
        String sql = "SELECT _id,Question,CorrectAnswer,IncorrectAnswer1,IncorrectAnswer2,IncorrectAnswer3 FROM Question_Answer WHERE QuestionFlag = 0 ORDER BY RANDOM() LIMIT ?";
        Cursor cursor = db.rawQuery(sql, sqlD);
        return cursor;
    }

    /**
     * 全問題数
     *
     * @return int
     */
    public int countAllRow() {
        int count;
        String sql = "SELECT _id FROM Question_Answer";
        Cursor cursor = db.rawQuery(sql, null);
        count = cursor.getCount();
        return count;
    }

    /**
     * 残問数
     * @return int
     */
    public int countZanmon() {
        int count;
        String[] sqlD = {"0"};
        String sql = "SELECT QuestionFlag FROM Question_Answer WHERE QuestionFlag=?";
        Cursor cursor = db.rawQuery(sql, sqlD);
        count = cursor.getCount();
        return count;
    }

    /**
     * @param id
     * @param flag
     * @return boolean 成功true　失敗false
     */
    public boolean updateQuestionFlag(int id, int flag) {
        try {
            db.beginTransaction();
            ContentValues val = new ContentValues();
            val.put("QuestionFlag", flag);
            val.put("UpdateDate", getDate());

            db.update(dbTable, val, "_id=?",
                    new String[]{String.valueOf(id)});
            db.setTransactionSuccessful();
            db.endTransaction();
            return true;
        } catch (Exception e) {
            Log.e("updateQuestionFlag", "err");
            return false;
        }
    }

    public boolean[] updateQuestionFlag(int[] id, int[] flag) {
        boolean[] tORf = new boolean[id.length];
        if (id.length == flag.length) {
            for (int i = 0; i < id.length; i++) {
                tORf[i] = updateQuestionFlag(id[i], flag[i]);
            }
        }
        return tORf;
    }

    public boolean reset() {
        try {
            db.beginTransaction();
            ContentValues val = new ContentValues();
            val.put("QuestionFlag", "0");
            val.put("UpdateDate", getDate());
            db.update(dbTable, val, null, null);
            db.setTransactionSuccessful();
            db.endTransaction();
            return true;
        } catch (Exception e) {
            Log.e("reset", "err");
            return false;
        }
    }

    private String getDate() {
        String today = "";
        Calendar cale = Calendar.getInstance();
        today = longToStringYDM(cale.getTimeInMillis());
        return today;
    }

    private String longToStringYDM(long longNum) {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("y/M/d");
        str = sdf.format(longNum);
        return str;
    }
}
