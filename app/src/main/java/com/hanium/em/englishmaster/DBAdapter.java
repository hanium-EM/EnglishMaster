package com.hanium.em.englishmaster;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class DBAdapter extends CursorAdapter{
    public DBAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView id = (TextView)view.findViewById(R.id.db_id);
        TextView usr = (TextView)view.findViewById(R.id.db_usr);
        TextView score = (TextView)view.findViewById(R.id.db_score);
        id.setText(cursor.getString(cursor.getColumnIndex("_id")));
        usr.setText(cursor.getString(cursor.getColumnIndex("usr")));
        score.setText(cursor.getString(cursor.getColumnIndex("score")));
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dblist_row,parent,false);
        return v;
    }


}
