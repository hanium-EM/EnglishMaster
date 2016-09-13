package com.hanium.em.englishmaster;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class GameActivity extends Activity  {

    private TextView ans;
    private TextView question;
    private TextView scoreView;



    private TextView time;
    CountDownTimer timer;
    private int mnMilliSecond = 1000;
    private int mnExitDelay = 60; //타이머 시간

    private EditText ins;
    private Button insBtn;
    private Button hintBtn;
    private Button passBtn;

    private String engAns;
    private String korAns;

    private StringBuffer hintStrBuf =null;

    private  int score = 0;
    private  int hintCnt = 0;

    int scoreVal = 0;
    final int scoreList [] = {1000,500,250};

    InputMethodManager imm;
    RecordDBHelper mHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        int delay = mnExitDelay*mnMilliSecond;
        timer = new CountDownTimer(delay, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                time = (TextView) findViewById(R.id.timer);
                    time.setText(millisUntilFinished/1000 + " 초");
            }

            @Override
            public void onFinish() {

                LayoutInflater inflater=getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_result,null);
                TextView dialogScoreView = (TextView) dialogView.findViewById(R.id.dialog_score);
                final EditText dialogUsr = (EditText) dialogView.findViewById(R.id.dialog_edit);
                dialogScoreView.setText(String.valueOf(score));
                AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                builder.setTitle("결과");
                builder.setView(dialogView);

                builder.setPositiveButton("저장", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mHelper = new RecordDBHelper(GameActivity.this);
                        SQLiteDatabase db;
                        ContentValues row;

                        db = mHelper.getWritableDatabase();
                        row=new ContentValues();
                        row.put("usr",dialogUsr.getText().toString());
                        row.put("score",score);
                        db.insert(mHelper.getTableName(),null,row);
                        db.close();
                        mHelper.close();
                        Toast.makeText(getApplicationContext(),"저장되었습니다.",Toast.LENGTH_SHORT).show();
                        Log.d("test::",String.valueOf(db));
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog=builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                time.setText("");
                ins.setEnabled(false);
                insBtn.setEnabled(false);
                passBtn.setEnabled(false);
                hintBtn.setEnabled(false);
            }
        }.start();
        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        Intent intent = getIntent();
        int ranNum = intent.getIntExtra("num",-1);
        ans = (TextView) findViewById(R.id.ans);
        question = (TextView) findViewById(R.id.question);
        scoreView = (TextView) findViewById(R.id.score);


        ins = (EditText) findViewById(R.id.ins);

        insBtn = (Button) findViewById(R.id.enter);
        passBtn = (Button) findViewById(R.id.pass);
        hintBtn = (Button) findViewById(R.id.hint);
        scoreVal=scoreList[0];
        ans.setGravity(1);
        setWord(ranNum);

    }
    public void setWord(int ranNum){
        HashMap<String, String> engWord =  Constants.engList.get(""+ranNum);
        Set<String> keySet = engWord.keySet();
        Iterator<String> iter = keySet.iterator();
        engAns = iter.next();
        korAns = engWord.get(engAns);
        //Toast.makeText(getApplicationContext(), engAns, Toast.LENGTH_SHORT).show();

        question.setText("\n뜻 : " + korAns);
        hintStrBuf = new StringBuffer();
        hintCnt = 2;
        hintBtn.setText("힌트:"+hintCnt);
    }

    public void mOnClick(View v){

        switch (v.getId()){
            case R.id.enter:
                imm.hideSoftInputFromWindow(ins.getWindowToken(), 0);

                String insEng = ins.getText().toString();


                if(engAns.equals(insEng)){
                    score+=scoreVal;
                    scoreView.setText(String.valueOf(score));

                    Toast.makeText(getApplicationContext(),"정답입니다!",Toast.LENGTH_SHORT).show();
                    ranWord();
                } else{
                    score=score-50;
                    scoreView.setText(String.valueOf(score));


                }
                ins.setText("");
                break;

            case R.id.pass:
                ranWord();
                break;

            case R.id.hint:
                switch (hintCnt) {
                    case 2:
                        scoreVal = scoreList[1];
                        for(int i=0; i<engAns.length();i++)
                            hintStrBuf.append("_");

                        break;
                    case 1:
                        scoreVal = scoreList[2];
                        StringBuffer ansAlphaBuf = new StringBuffer(engAns);
                        hintBtn.setEnabled(false);
                        Random ran = new Random();
                        int ranNum = ran.nextInt(engAns.length()) ;
                        hintStrBuf.replace(ranNum,ranNum+1,ansAlphaBuf.charAt(ranNum)+"");
                        break;
                    default:
                        break;
                }
                ans.setText(new String(hintStrBuf));

                hintBtn.setText("힌트:" + --hintCnt);
                break;

            case R.id.stop :
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("정말 그만두시겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                timer.cancel();
                                finish();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

                break;
        }
    }
    public void ranWord(){
        ans.setText("");
        scoreVal = scoreList[0];
        hintBtn.setEnabled(true);
        Random ran = new Random();
        int ranNum = ran.nextInt(Constants.engList.size())+1 ;
        setWord(ranNum);
    }



}
