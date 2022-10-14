package net.ecnu.controller;

import lombok.Data;

@Data
public class Result {
    private String SuggestedScore;   //作为总分使用
    private String PronAccuracy;     //准确度
    private String PronFluency;      //流利度
    private String PronCompletion;   //完整度
/*
    private String Phone;            //音素
    private String DetectedStress;   //用户是否重音
    private String Stress;           //是否应该重音
    private String MatchTag;         //当前词匹配情况,评分为0情况下观察是否漏读，错读
*/

}