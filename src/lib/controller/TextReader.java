package lib.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

public class TextReader {

    private String _direction = "../lib/texts/";

    private final int TEXT_COUNT = 4;

    private Random _rand = new Random();
    
    public TextReader() {
        super();
    }

    public TextReader(String direction) {
        super();
        _direction = direction;
    }

    public String getPhrase(int textNumber, int phraseNumber) {
        String phrase = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(_direction + "text" + textNumber + ".txt"));
            for (int i = 0; i <= phraseNumber; i++) phrase = reader.readLine();
            System.out.println(phrase);
            reader.close();
        } catch (Exception e) { e.printStackTrace(); }
        return phrase;
    }

    public String getPhrase() {
        String phrase = "";
        try {
            int textNum = _rand.nextInt(TEXT_COUNT);
            BufferedReader reader = new BufferedReader(new FileReader(_direction + "text" + textNum + ".txt"));
            int phraseNum = _rand.nextInt(Integer.parseInt(reader.readLine()));
            System.out.println("Text: " + textNum + "\tPhrase num: " + phraseNum + 1);
            for (int i = 0; i <= phraseNum; i++) {
                phrase = reader.readLine();
            }
            System.out.println("Phrase: " + phrase);
            reader.close();
        } catch (Exception e) { e.printStackTrace(); }
        return phrase;
    }
}
