package com.hanium.em.englishmaster;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Random;

public class ChooseLevelActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_level);
    }
    public void mOnClick(View v){
        Intent intent;
        Random ran;
        int num;
        switch (v.getId()){
            case R.id.easy:
                wordSet("easy.ini");

                intent = new Intent(getApplicationContext(),GameActivity.class);
                ran = new Random();
                num = ran.nextInt(Constants.engList.size()) + 1;
                intent.putExtra("num", num);
                startActivity(intent);
                finish();
                break;
            case R.id.hard:
                wordSet("hard.ini");

                intent = new Intent(getApplicationContext(),GameActivity.class);
                ran = new Random();
                num = ran.nextInt(Constants.engList.size()) + 1;
                intent.putExtra("num", num);
                startActivity(intent);
                finish();
                break;
        }

    }
    private void wordSet(String s){
        AssetManager aMgr = getResources().getAssets();
        InputStream is = null;
        try {
            is = aMgr.open(s);
        } catch (IOException e){
            e.printStackTrace();
        }
        String engWordsIni = readWords(is);
        StringReader sReader = new StringReader(engWordsIni);
        BufferedReader bReader = new BufferedReader(sReader);

        String line;
        try{
            while((line = bReader.readLine()) != null){
                String[] split = line.split("@");

                for(int i =0;i<split.length;i++){
                    // splitWord[0] : key, splitWord[1] : englist, splitWord[2] : Korean
                    String[] splitWord = split[i].split("=");
                    HashMap<String,String> word = new <String, String>HashMap();
                    word.put(splitWord[1], splitWord[2]);
                    Constants.engList.put(splitWord[0], word);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    private String readWords(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is, "EUC-KR"));
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
