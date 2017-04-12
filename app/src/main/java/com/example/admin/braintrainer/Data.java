package com.example.admin.braintrainer;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by admin on 2016/02/09.
 */
public class Data {

    private Random rndSelector = new Random();
    private int charactorSelectNumber;
    private int colorSelectNumber;
    private int correctEnglishNumber;
    private ArrayList<String> questionData;
    private ArrayList<String> kanjiList = new ArrayList<String>(Arrays.asList("赤","青","黄色","紫","緑","橙","黒"));
    private ArrayList<String> hiraganaList = new ArrayList<String>(Arrays.asList("あか","あお","きいろ","むらさき","みどり","だいだい","くろ"));
    private ArrayList<String> englishList = new ArrayList<String>(Arrays.asList("red","blue","yellow","purple","green","orange","black"));
    private ArrayList<String> colorStringList = new ArrayList<String>(Arrays.asList("#ff0000","#0000ff","#ffff00","#800080","#008000","#ffa500","#000000"));

    public void Data(){

    }
        public ArrayList<String> makeQuestionData(){
            questionData = new ArrayList<String>();
            int hOrK = rndSelector.nextInt(2);
            if(hOrK == 0){
            //漢字の処理
                charactorSelectNumber = rndSelector.nextInt(7);
                questionData.add(kanjiList.get(charactorSelectNumber));
                colorSelectNumber = rndSelector.nextInt(7);
                while(colorSelectNumber == charactorSelectNumber){
                    colorSelectNumber = rndSelector.nextInt(7);
                }
                questionData.add(colorStringList.get(colorSelectNumber));
                ArrayList<Integer> randList = new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,5,6));
                randList.remove(charactorSelectNumber);
                Collections.shuffle(randList);
                questionData.add(englishList.get(charactorSelectNumber));
                for(int i = 1; i < 4; i++){
                    questionData.add(englishList.get(randList.get(i)));
                }
                return questionData;
            }else{
            //ひらがなの処理
                charactorSelectNumber = rndSelector.nextInt(7);
                questionData.add(hiraganaList.get(charactorSelectNumber));
                colorSelectNumber = rndSelector.nextInt(7);
                while(colorSelectNumber == charactorSelectNumber){
                    colorSelectNumber = rndSelector.nextInt(7);
                }
                questionData.add(colorStringList.get(colorSelectNumber));
                ArrayList<Integer> randList = new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,5,6));
                randList.remove(colorSelectNumber);
                Collections.shuffle(randList);
                questionData.add(englishList.get(colorSelectNumber));
                for(int i = 1; i < 4; i++){
                    questionData.add(englishList.get(randList.get(i)));
                }
                return questionData;
            }

        }
}
