package com.hanium.em.englishmaster;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HowToActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howto);
    }

    public void mOnClick(View v){
        switch (v.getId()){
            case R.id.back:
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
                break;
        }

    }
}
