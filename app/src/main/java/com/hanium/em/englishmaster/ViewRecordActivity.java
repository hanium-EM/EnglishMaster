package com.hanium.em.englishmaster;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class ViewRecordActivity extends Activity {
    ListView list;
    RecordDBHelper mHelper;
    SQLiteDatabase db;
    String sql;
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);



        list = (ListView) findViewById(R.id.db_list);
        mHelper = new RecordDBHelper(this);
        db = mHelper.getWritableDatabase();

        selectDB();
    }
    private void selectDB(){
        db = mHelper.getWritableDatabase();
        sql="SELECT * FROM "+mHelper.getTableName();

        cursor = db.rawQuery(sql,null);
        if (cursor.getCount()>0){
            startManagingCursor(cursor);
            DBAdapter dbAdapter = new DBAdapter(this,cursor);
            list.setAdapter(dbAdapter);
        }
    }
    public void mOnClick(View v){
        switch (v.getId()){
            case R.id.back1:
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
                break;
        }

    }
}
