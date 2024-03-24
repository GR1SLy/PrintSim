package lib.controller;

import java.io.FileReader;

public class TextReader {

    private String _direction = "../lib/texts/";

    private int _textNumber = 0;
    
    public TextReader() {
        super();
    }

    public TextReader(String direction) {
        super();
        _direction = direction;
    }

    public String getNextText() {
        return getText(_textNumber++);
    }

    public String getText(int textNumber) {
        String text = "";
        try {
            FileReader reader = new FileReader(_direction + "text" + textNumber + ".txt");
            int c;
            while ((c = reader.read()) != -1) {
                text += (char)c;
            }
            System.out.println(text);
            reader.close();
        } catch (Exception e) { e.printStackTrace(); }
        return text;
    }
}
