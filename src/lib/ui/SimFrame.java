package lib.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

import lib.controller.KeyChecker;
import lib.controller.Statistics;
import lib.controller.TextReader;

public class SimFrame extends JFrame {

    private final int WIDTH, HEIGHT;

    private TextPanel _textPanel;

    private StatPanel _statPanel;

    private KeyChecker _keyChecker;

    private Statistics _stat;

    private TextReader _textReader = new TextReader();

    {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        setMinimumSize(new Dimension(1000, 600));

        _statPanel = new StatPanel();
        add(_statPanel, BorderLayout.PAGE_START);

        _textPanel = new TextPanel();
        add(_textPanel, BorderLayout.CENTER);

        _keyChecker = new KeyChecker(this);
        addKeyListener(_keyChecker);
    }

    public SimFrame(int width, int height) {
        super();
        WIDTH = width;
        HEIGHT = height;
        requestPhrase(2, 5);
    }

    public void createFrame() {
        setTitle("PrintSim");
        setBounds(0, 0, WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void requestPhrase() {
        setPhrase(_textReader.getPhrase());
    }

    public void requestPhrase(int textNumber, int phraseNumber) {
        setPhrase(_textReader.getPhrase(textNumber, phraseNumber));
    }

    public void setPhrase(String phrase) {
        _textPanel.setPhrase(phrase);
        _keyChecker.setPhrase(phrase);
        _stat = new Statistics(phrase.length());
    }

    public void setSpeed(int speed) { _statPanel.setSpeed(speed); }

    public void greenAtIndex(int index) {
        _textPanel.greenAtIndex(index);
        _stat.greenIncreace();
        _statPanel.setAccuracy(_stat.getAccuracy(index));
    }

    public void redAtIndex(int index, boolean isSpace) {
        _textPanel.redAtIndex(index, isSpace);
        _stat.redIncreace();
        _statPanel.setAccuracy(_stat.getAccuracy(index));
        _statPanel.setErrors(_stat.getErrors());
    }

    public int getTypedCount() { return _stat.getTypedCount(); }

    public void textEnd() {
        _textPanel.requestEnter();
    }

    public void clear() {
        _statPanel.clear();
        _stat.clear();
    }
    
}
