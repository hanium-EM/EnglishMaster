package com.hanium.em.englishmaster;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class IntroActivity extends Activity {
    Handler h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        h=new Handler();
        h.postDelayed(irun, 1000);

    }
    Runnable irun = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(IntroActivity.this, WelcomeActivity.class);
            startActivity(intent);
            finish();

            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    };

}
