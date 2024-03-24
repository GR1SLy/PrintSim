package lib.controller;

import java.io.BufferedReader;
import java.io.FileReader;

public class TextReader {

    private String _direction = "../lib/texts/";

    private int _textNumber = 0, _phraseNumber = 0;
    
    public TextReader() {
        super();
    }

    public TextReader(String direction) {
        super();
        _direction = direction;
    }

    public String getNextText() {
        return getText(_textNumber, _phraseNumber++);
    }

    public String getText(int textNumber, int phraseNumber) {
        String text = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(_direction + "text" + textNumber + ".txt"));
            for (int i = 0; i <= phraseNumber; i++) { text = reader.readLine(); if (text == "") phraseNumber++; }
            System.out.println(text);
            reader.close();
        } catch (Exception e) { e.printStackTrace(); }
        return text;
    }
}
