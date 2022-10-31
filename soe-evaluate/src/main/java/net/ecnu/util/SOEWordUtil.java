package net.ecnu.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.RegularExpressionValueMatcher;

public class SOEWordUtil {
    public static String getChinse(String s){
        String reg = "[^\u4e00-\u9fa5]";
        s = s.replaceAll(reg,"");
        return s;
    }//提取段落中的中文汉字
    public static String[] cutPinyin(String pinyin){
        String[] words = pinyin.trim().split("\\s+");
        return words;
    }//将拼音按空格切割，分组
    public static String formatText(String text,String pinyin) throws JSONException {
        //text = "今天天气怎么样";
        //pinyin = "jin1 tian1 tian1 qi4 zen3 mo yang4";
        String[] pinyinArray = SOEWordUtil.cutPinyin(pinyin);
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray  = new JSONArray() ;
        for(int i=0;i<text.length()&&i<pinyinArray.length;i++){
            JSONObject word = new JSONObject();
            JSONArray pron1 = new JSONArray();
            JSONArray pron2 = new JSONArray();
            //String reg = "^[A-Za-z]{1,10}[1-4]{1}$";
            String reg = "^[A-Za-z]{1,10}[1-4]{1}$";
            if(pinyinArray[i].matches(reg)&&pinyinArray[i]!="me"){
                pron2.put(pinyinArray[i]);
                pron1.put(0,pron2);
                word.put("pron",pron1 );
            }
            word.put("word",text.substring(i,i+1));
            jsonArray.put(i,word);
        }
        jsonObject.put("wordlist", jsonArray);
        String alignedText = jsonObject.toString();
        System.out.println(jsonObject.toString());
        return alignedText;
    }//将汉字与拼音组合成腾讯云指定的json格式，以完成指定汉字发音功能

}
