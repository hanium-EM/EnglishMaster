package com.hanium.em.englishmaster;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RecordDBHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "record.db";
    private static String TABLE_NAME = "rec";

    public static String getTableName() {                                                                                  // tableName으로 지정하면 관리가 쉽다
        return TABLE_NAME;
    }

    public RecordDBHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE "+TABLE_NAME+" ( _id INTEGER PRIMARY KEY AUTOINCREMENT, usr TEXT, score INTEGER);");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
}
