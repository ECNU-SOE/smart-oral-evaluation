package net.ecnu.iflytek;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws Exception {
        Test test = new Test();
        test.deploy();
    }
    public void deploy() throws Exception {
        ReadSentence readSentence = new ReadSentence();
        System.out.println("即将评测文本是："+readSentence.getText());
        readSentence.statTest();

    }
}
