package com.hanium.em.englishmaster;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void mOnClick(View v){
        switch (v.getId()){
            case R.id.start:
                startActivity(new Intent(getApplicationContext(),ChooseLevelActivity.class));
                finish();
                break;
            case R.id.howTo:
                startActivity(new Intent(getApplicationContext(),HowToActivity.class));
                finish();
                break;
            case R.id.viewRecord:
                startActivity(new Intent(getApplicationContext(),ViewRecordActivity.class));
                finish();
                break;
        }

    }

    public void onRestart() {
        super.onRestart();
    }
}
