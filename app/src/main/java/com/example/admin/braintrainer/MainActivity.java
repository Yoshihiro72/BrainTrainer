package com.example.admin.braintrainer;

import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private FrameLayout title;
    private LinearLayout instruction;
    private LinearLayout game;
    private LinearLayout result;
    private LinearLayout countDown;
    private MediaPlayer mMediaPlayer;
    private SoundPool soundPool;
    private int[] AnsSEid = new int[2];


    private Data data = new Data();

    private TextView jCharactor;
    private TextView judgeText;
    private TextView curScore;
    private int scoreNum;
    private TextView timeLeft;
    private TextView figure;

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private ArrayList<Button> buttons;
    private ArrayList<Integer> randList;
    //private Button correctButton;
    private Button buttonToInput;
    private int correctButton;

    private Random rdmSelector = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = (FrameLayout)findViewById(R.id.title);
        instruction = (LinearLayout)findViewById(R.id.instruction);
        countDown = (LinearLayout)findViewById(R.id.countDown);
        game = (LinearLayout)findViewById(R.id.game);
        result = (LinearLayout)findViewById(R.id.result);

        title.setVisibility(View.VISIBLE);
        instruction.setVisibility(View.GONE);
        countDown.setVisibility(View.GONE);
        game.setVisibility(View.GONE);
        result.setVisibility(View.GONE);


        //<Buttons>
        Button titleBtn = (Button)findViewById(R.id.titleBtn);
        titleBtn.setOnClickListener(this);

        Button gameStartBtn = (Button)findViewById(R.id.gameStartBtn);
        gameStartBtn.setOnClickListener(this);

        Button button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(this);
        Button button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(this);
        Button button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(this);
        Button button4 = (Button)findViewById(R.id.button4);
        button4.setOnClickListener(this);

        Button retryBtn = (Button)findViewById(R.id.retryBtn);
        retryBtn.setOnClickListener(this);

        ArrayList<Button> buttons = new ArrayList<Button>(Arrays.asList(button1,button2,button3,button4));
        //</Buttons>

        //<texts>
        jCharactor = (TextView)findViewById(R.id.jCharactor);
        judgeText = (TextView)findViewById(R.id.judgeText);
        //</texts>
    }

    @Override
    public void onResume(){
        super.onResume();
        mMediaPlayer = MediaPlayer.create(this, R.raw.b_trainer_bgm);
        mMediaPlayer.start();
        mMediaPlayer.setLooping(true);
        soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        AnsSEid[0] = soundPool.load(getApplicationContext(), R.raw.correctanswer, 1);
        AnsSEid[1] = soundPool.load(getApplicationContext(), R.raw.wronganswer, 1);
    }

    @Override
    protected void onPause(){
        super.onPause();
        mMediaPlayer.pause();
        soundPool.unload(AnsSEid[0]);
        soundPool.unload(AnsSEid[1]);
        soundPool.release();
    }

    @Override
    public void onClick(View view){

        switch(view.getId()){

            case R.id.titleBtn:
                title.setVisibility(View.GONE);
                instruction.setVisibility(View.VISIBLE);
                break;

            case R.id.gameStartBtn:
                instruction.setVisibility(View.GONE);
                countDown.setVisibility(View.VISIBLE);
                new CountDownTimer(3000, 500){
                    public void onTick(long millisUntilFinished){
                        ((TextView)findViewById(R.id.figure)).setText(String.valueOf(1 + (millisUntilFinished/1000)));
                    }
                    public void onFinish(){
                        countDown.setVisibility(View.GONE);
                        game.setVisibility(View.VISIBLE);
                        questionBegin();
                        new CountDownTimer(60000, 500){
                            public void onTick(long millisUntilFinished){
                                ((TextView)findViewById(R.id.timeLeft)).setText(String.valueOf(millisUntilFinished/1000));
                            }
                            public void onFinish(){
                                game.setVisibility(View.GONE);
                                result.setVisibility(View.VISIBLE);
                                ((TextView)findViewById(R.id.resultScore)).setText(String.valueOf(scoreNum) + "点");
                                if(scoreNum < 5){
                                    ((TextView)findViewById(R.id.comment)).setText(R.string.score_verylow);
                                }else if(scoreNum >= 5 && scoreNum < 10 ){
                                    ((TextView)findViewById(R.id.comment)).setText(R.string.score_low);
                                }else if(scoreNum >= 10 && scoreNum < 15 ){
                                    ((TextView)findViewById(R.id.comment)).setText(R.string.score_normal);
                                }else if(scoreNum >= 15 && scoreNum < 20 ){
                                    ((TextView)findViewById(R.id.comment)).setText(R.string.score_a_littlehigh);
                                }else if(scoreNum >= 20 && scoreNum < 25 ){
                                    ((TextView)findViewById(R.id.comment)).setText(R.string.score_high);
                                }else if(scoreNum >= 25 && scoreNum < 30 ){
                                    ((TextView)findViewById(R.id.comment)).setText(R.string.score_veryhigh);
                                }else{
                                    ((TextView)findViewById(R.id.comment)).setText(R.string.score_extremelyhigh);
                                }
                            }
                        }.start();
                    }
                }.start();
                break;

            case R.id.button1:
                findViewById(R.id.button1).setEnabled(false);
                findViewById(R.id.button2).setEnabled(false);
                findViewById(R.id.button3).setEnabled(false);
                findViewById(R.id.button4).setEnabled(false);
                if(correctButton == 0) {
                    soundPool.play(AnsSEid[0], 1.0f, 1.0f, 0, 0, 1.0f);
                    findViewById(R.id.judgeText).setVisibility(View.VISIBLE);
                    ((TextView)findViewById(R.id.judgeText)).setText("正解！");
                    scoreNum++;
                    ((TextView)findViewById(R.id.curScore)).setText("Score: " + String.valueOf(scoreNum));
                }else{
                    soundPool.play(AnsSEid[1], 1.0f, 1.0f, 0, 0, 1.0f);
                    findViewById(R.id.judgeText).setVisibility(View.VISIBLE);
                    ((TextView)findViewById(R.id.judgeText)).setText("不正解");
                    scoreNum--;
                    ((TextView)findViewById(R.id.curScore)).setText("Score: " + String.valueOf(scoreNum));
                }
                new CountDownTimer(500, 250){
                    public void onTick(long l){
                    }
                    public void onFinish(){
                        findViewById(R.id.button1).setEnabled(true);
                        findViewById(R.id.button2).setEnabled(true);
                        findViewById(R.id.button3).setEnabled(true);
                        findViewById(R.id.button4).setEnabled(true);
                        questionBegin();
                    }
                }.start();
                break;

            case R.id.button2:
                findViewById(R.id.button1).setEnabled(false);
                findViewById(R.id.button2).setEnabled(false);
                findViewById(R.id.button3).setEnabled(false);
                findViewById(R.id.button4).setEnabled(false);
                if(correctButton == 1) {
                    soundPool.play(AnsSEid[0], 1.0f, 1.0f, 0, 0, 1.0f);
                    findViewById(R.id.judgeText).setVisibility(View.VISIBLE);
                    ((TextView)findViewById(R.id.judgeText)).setText("正解！");
                    scoreNum++;
                    ((TextView)findViewById(R.id.curScore)).setText("Score: " + String.valueOf(scoreNum));
                }else{
                    soundPool.play(AnsSEid[1], 1.0f, 1.0f, 0, 0, 1.0f);
                    findViewById(R.id.judgeText).setVisibility(View.VISIBLE);
                    ((TextView)findViewById(R.id.judgeText)).setText("不正解");
                    scoreNum--;
                    ((TextView)findViewById(R.id.curScore)).setText("Score: " + String.valueOf(scoreNum));
                }
                new CountDownTimer(500, 250){
                    public void onTick(long l){
                    }
                    public void onFinish(){
                        findViewById(R.id.button1).setEnabled(true);
                        findViewById(R.id.button2).setEnabled(true);
                        findViewById(R.id.button3).setEnabled(true);
                        findViewById(R.id.button4).setEnabled(true);
                        questionBegin();
                    }
                }.start();
                break;

            case R.id.button3:
                findViewById(R.id.button1).setEnabled(false);
                findViewById(R.id.button2).setEnabled(false);
                findViewById(R.id.button3).setEnabled(false);
                findViewById(R.id.button4).setEnabled(false);
                if(correctButton == 2) {
                    soundPool.play(AnsSEid[0], 1.0f, 1.0f, 0, 0, 1.0f);
                    findViewById(R.id.judgeText).setVisibility(View.VISIBLE);
                    ((TextView)findViewById(R.id.judgeText)).setText("正解！");
                    scoreNum++;
                    ((TextView)findViewById(R.id.curScore)).setText("Score: " + String.valueOf(scoreNum));
                }else{
                    soundPool.play(AnsSEid[1], 1.0f, 1.0f, 0, 0, 1.0f);
                    findViewById(R.id.judgeText).setVisibility(View.VISIBLE);
                    ((TextView)findViewById(R.id.judgeText)).setText("不正解");
                    scoreNum--;
                    ((TextView)findViewById(R.id.curScore)).setText("Score: " + String.valueOf(scoreNum));
                }
                new CountDownTimer(500, 250){
                    public void onTick(long l){
                    }
                    public void onFinish(){
                        findViewById(R.id.button1).setEnabled(true);
                        findViewById(R.id.button2).setEnabled(true);
                        findViewById(R.id.button3).setEnabled(true);
                        findViewById(R.id.button4).setEnabled(true);
                        questionBegin();
                    }
                }.start();
                break;

            case R.id.button4:
                findViewById(R.id.button1).setEnabled(false);
                findViewById(R.id.button2).setEnabled(false);
                findViewById(R.id.button3).setEnabled(false);
                findViewById(R.id.button4).setEnabled(false);
                if(correctButton == 3) {
                    soundPool.play(AnsSEid[0], 1.0f, 1.0f, 0, 0, 1.0f);
                    findViewById(R.id.judgeText).setVisibility(View.VISIBLE);
                    ((TextView)findViewById(R.id.judgeText)).setText("正解！");
                    scoreNum++;
                    ((TextView)findViewById(R.id.curScore)).setText("Score: " + String.valueOf(scoreNum));
                }else{
                    soundPool.play(AnsSEid[1], 1.0f, 1.0f, 0, 0, 1.0f);
                    findViewById(R.id.judgeText).setVisibility(View.VISIBLE);
                    ((TextView)findViewById(R.id.judgeText)).setText("不正解");
                    scoreNum--;
                    ((TextView)findViewById(R.id.curScore)).setText("Score: " + String.valueOf(scoreNum));
                }
                new CountDownTimer(500, 250){
                    public void onTick(long l){
                    }
                    public void onFinish(){
                        findViewById(R.id.button1).setEnabled(true);
                        findViewById(R.id.button2).setEnabled(true);
                        findViewById(R.id.button3).setEnabled(true);
                        findViewById(R.id.button4).setEnabled(true);
                        questionBegin();
                    }
                }.start();
                break;

            case R.id.retryBtn:
                scoreNum = 0;
                ((TextView)findViewById(R.id.curScore)).setText("Score: " + String.valueOf(scoreNum));
                result.setVisibility(View.GONE);
                countDown.setVisibility(View.VISIBLE);
                new CountDownTimer(3000, 500){
                    public void onTick(long millisUntilFinished){
                        ((TextView)findViewById(R.id.figure)).setText(String.valueOf(1 + (millisUntilFinished/1000)));
                    }
                    public void onFinish(){
                        countDown.setVisibility(View.GONE);
                        game.setVisibility(View.VISIBLE);
                        questionBegin();
                        new CountDownTimer(60000, 500){
                            public void onTick(long millisUntilFinished){
                                ((TextView)findViewById(R.id.timeLeft)).setText(String.valueOf(millisUntilFinished/1000));
                            }
                            public void onFinish(){
                                game.setVisibility(View.GONE);
                                result.setVisibility(View.VISIBLE);
                                ((TextView)findViewById(R.id.resultScore)).setText(String.valueOf(scoreNum) + "点");
                                if(scoreNum < 5){
                                    ((TextView)findViewById(R.id.comment)).setText(R.string.score_verylow);
                                }else if(scoreNum >= 5 && scoreNum < 10 ){
                                    ((TextView)findViewById(R.id.comment)).setText(R.string.score_low);
                                }else if(scoreNum >= 10 && scoreNum < 15 ){
                                    ((TextView)findViewById(R.id.comment)).setText(R.string.score_normal);
                                }else if(scoreNum >= 15 && scoreNum < 20 ){
                                    ((TextView)findViewById(R.id.comment)).setText(R.string.score_a_littlehigh);
                                }else if(scoreNum >= 20 && scoreNum < 25 ){
                                    ((TextView)findViewById(R.id.comment)).setText(R.string.score_high);
                                }else if(scoreNum >= 25 && scoreNum < 30 ){
                                    ((TextView)findViewById(R.id.comment)).setText(R.string.score_veryhigh);
                                }else{
                                    ((TextView)findViewById(R.id.comment)).setText(R.string.score_extremelyhigh);
                                }
                            }
                        }.start();
                    }
                }.start();
                break;
        }

    }

    public void questionBegin(){
        judgeText.setVisibility(View.INVISIBLE);
        ArrayList<String> textSets = data.makeQuestionData();
        Log.i("textSet", String.valueOf(textSets));

        Log.i("setText", textSets.get(0));
        jCharactor.setText(textSets.get(0));
        jCharactor.setTextColor(Color.parseColor(textSets.get(1)));

        randList = new ArrayList<Integer>(Arrays.asList(0,1,2,3));
        Collections.shuffle(randList);
        Log.i("randList", String.valueOf(randList));

        //buttons.get(randList.get(0))

        switch(randList.get(0)){

            case 0:
                ((Button)findViewById(R.id.button1)).setText(textSets.get(2));
                correctButton = 0;
                break;
            case 1:
                ((Button)findViewById(R.id.button2)).setText(textSets.get(2));
                correctButton = 1;
                break;
            case 2:
                ((Button)findViewById(R.id.button3)).setText(textSets.get(2));
                correctButton = 2;
                break;
            case 3:
                ((Button)findViewById(R.id.button4)).setText(textSets.get(2));
                correctButton = 3;
                break;
        }
        Log.i("correctbutton", String.valueOf(correctButton));
        for(int i = 1; i < 4; i++){
            switch(randList.get(i)){

                case 0:
                    ((Button)findViewById(R.id.button1)).setText(textSets.get(i + 2));
                    break;
                case 1:
                    ((Button)findViewById(R.id.button2)).setText(textSets.get(i + 2));
                    break;
                case 2:
                    ((Button)findViewById(R.id.button3)).setText(textSets.get(i + 2));
                    break;
                case 3:
                    ((Button)findViewById(R.id.button4)).setText(textSets.get(i + 2));
                    break;
            }
        }
    }

}
