package lib.ui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import lib.controller.KeyChecker;
import lib.controller.Statistics;
import lib.controller.TextReader;

public class SimFrame extends JFrame {

    private final int WIDTH, HEIGHT;

    private TextPanel _textPanel;

    private KeyChecker _keyChecker;

    private Statistics _stat;

    private TextReader _textReader = new TextReader();

    {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        _textPanel = new TextPanel();
        add(_textPanel, BorderLayout.CENTER);

        _keyChecker = new KeyChecker(this);
        addKeyListener(_keyChecker);
    }

    public SimFrame(int width, int height) {
        super();
        WIDTH = width;
        HEIGHT = height;
        requestText();
    }

    public void createFrame() {
        setTitle("PrintSim");
        setBounds(0, 0, WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void requestText() {
        setText(_textReader.getNextText());
    }

    public void setText(String text) {
        _textPanel.setText(text);
        _keyChecker.setText(text);
        _stat = new Statistics(text.length());
    }

    public void greenAtIndex(int index) {
        _textPanel.greenAtIndex(index);
        _stat.greenIncreace();
    }

    public void redAtIndex(int index) {
        _textPanel.redAtIndex(index);
        _stat.redIncreace();
    }

    public void textEnd() {
        JOptionPane.showMessageDialog(this, _stat.getStat(), "INFO", JOptionPane.INFORMATION_MESSAGE);
        _textPanel.pressEnter();
    }

    public void clear() {
        _textPanel.clear();
        _stat.clear();
    }
    
}
