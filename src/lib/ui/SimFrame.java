package lib.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import lib.controller.DiagramPanel;
import lib.controller.KeyChecker;
import lib.controller.Statistics;
import lib.controller.TextReader;

public class SimFrame extends JFrame {

    private final int WIDTH, HEIGHT;

    private JPanel _mainPanel, _globalPanel;

    private TextPanel _textPanel;

    private StatPanel _statPanel;

    private KeyChecker _keyChecker;

    private Statistics _stat;
    private ArrayList<Statistics> _statHistory;
    private static final String SAVE_DIR = "../stat.bin";
    public static final String ICONS_DIR = "../lib/icons/";

    private JPanel _statButtonPanel;
    private JButton _statButton;
    private DiagramPanel _diagramPanel;

    private TextReader _textReader = new TextReader();


    {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        setMinimumSize(new Dimension(1000, 600));

        _globalPanel = new JPanel();
        _globalPanel.setLayout(new CardLayout());
        add(_globalPanel, BorderLayout.CENTER);

        _mainPanel = new JPanel();
        _mainPanel.setLayout(new BorderLayout());
        _globalPanel.add(_mainPanel);

        _statPanel = new StatPanel();
        _mainPanel.add(_statPanel, BorderLayout.PAGE_START);

        _textPanel = new TextPanel();
        _mainPanel.add(_textPanel, BorderLayout.CENTER);

        _keyChecker = new KeyChecker(this);
        _mainPanel.addKeyListener(_keyChecker);

        _stat = new Statistics();

        _statHistory = SimFrame.deserializeStats();
        if (_statHistory == null) _statHistory = new ArrayList<>();

        _statButton = new JButton(new ImageIcon(ICONS_DIR + "stats_icon.png"));
        _statButton.setContentAreaFilled(false);
        _statButton.setBorderPainted(false);
        _statButton.setFocusable(false);
        _statButton.addActionListener(e -> {
            // showStatHistory();
            _diagramPanel.setStats(_statHistory);
            ((CardLayout)_globalPanel.getLayout()).next(_globalPanel);
            _diagramPanel.requestFocus();
        });

        _statButtonPanel = new JPanel();
        _statButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        _statButtonPanel.setBackground(new Color(30, 30, 30));
        _statButtonPanel.add(_statButton);
        _mainPanel.add(_statButtonPanel, BorderLayout.PAGE_END);

        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            SimFrame.serializeStats(_statHistory);
        }));
    }
    
    public SimFrame(int width, int height) {
        super();
        WIDTH = width;
        HEIGHT = height;
        _diagramPanel = new DiagramPanel(WIDTH, HEIGHT);
        _diagramPanel.getBackButton().addActionListener(e -> {
            ((CardLayout)_globalPanel.getLayout()).previous(_globalPanel);
            _mainPanel.requestFocus();
        });
        _globalPanel.add(_diagramPanel);
        requestPhrase();
    }

    public void createFrame() {
        setTitle("PrintSim");
        setBounds(0, 0, WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        _mainPanel.requestFocus();
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
        _stat.setKeyCount(phrase.length());
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

    public void statAppend(int speed) {
        _statHistory.addLast(_stat.save(speed));
    }

    public void showStatHistory() {
        System.out.println("Stat:\n" + _statHistory);
        JFrame newFrame = new JFrame();
        newFrame.setSize(1000, 600);
        newFrame.setLocationRelativeTo(null);
        newFrame.add(new DiagramPanel(1000, 600));
        newFrame.setVisible(true);
    }

    private static void serializeStats(ArrayList<Statistics> object) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_DIR));
            oos.writeObject(object);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static ArrayList<Statistics> deserializeStats() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_DIR));
            ArrayList<Statistics> res = (ArrayList<Statistics>) ois.readObject();
            ois.close();
            return res;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
