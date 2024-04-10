package lib.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import lib.ui.SimFrame;

public class DiagramPanel extends JPanel {

    private final int WIDTH, UP_HEIGHT, DOWN_HEIGHT, HEIGHT, OFFSET = 200;

    private ArrayList<Statistics> _statList;

    private int _upperBound, _lowerBound, _columnWidth, _columnOffset;
    private double _upStep, _downStep;

    private JLabel _mainLabel, _avgLabel;

    private JPanel _graphicsPanel;

    private JButton _backButton;
    
    {
        Color panelColor = new Color(44, 43, 51);

        setLayout(new BorderLayout());
        setBackground(panelColor);

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new FlowLayout());
        labelPanel.setOpaque(false);
        add(labelPanel, BorderLayout.PAGE_START);

        _mainLabel = new JLabel("Statistics    ");
        _mainLabel.setForeground(Color.WHITE);
        _mainLabel.setFont(new Font("Lucida Console", Font.BOLD, 24));
        labelPanel.add(_mainLabel);
        
        _avgLabel = new JLabel();
        _avgLabel.setForeground(Color.WHITE);
        _avgLabel.setFont(new Font("Lucida Console", Font.ITALIC, 16));
        labelPanel.add(_avgLabel);

        _graphicsPanel = new JPanel() {
            private static final Color SPEED_RECT = new Color(240, 170, 110);
            private static final Color ERROR_RECT = new Color(240, 160, 160);
            private static final Color LINE = new Color(100, 100, 100);
            private JLabel count;
            private ArrayList<JLabel> labels = new ArrayList<>();

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (JLabel label : labels) remove(label);
                labels.clear();
                int x = 20 + (OFFSET / 2);
                int y = (int)((double)OFFSET / 3.5);
                for (Statistics stat : _statList) {
                    int speed = stat.getSpeed();
                    int errors = stat.getErrors();
                    int columnHeight = (int)(speed * _upStep);
                    g.setColor(SPEED_RECT);
                    g.fillRect(x, y + UP_HEIGHT - columnHeight, _columnWidth, columnHeight);

                    g.setColor(ERROR_RECT);
                    g.fillRect(x, y + UP_HEIGHT, _columnWidth, (int)(errors * _downStep));

                    count = new JLabel("" + speed, JLabel.CENTER);
                    count.setVerticalAlignment(JLabel.NORTH);
                    count.setForeground(Color.WHITE);
                    if (_columnWidth <= 66) {
                        count.setBounds(x, y + UP_HEIGHT - columnHeight, _columnWidth, 30); 
                        count.setFont(count.getFont().deriveFont(((float)_columnWidth / 2f) - 1f));
                    } else {
                        count.setBounds(x, y + UP_HEIGHT - columnHeight, _columnWidth, 40);
                        count.setFont(count.getFont().deriveFont(32f));
                    }
                    add(count);
                    labels.addLast(count);

                    g.setColor(LINE);
                    g.drawLine((x - _columnOffset - ((_columnWidth / 2) / 2)), y + UP_HEIGHT / 2, (x + ((_columnWidth / 2) / 2)), y + UP_HEIGHT / 2);
                    g.drawLine((x - _columnOffset - ((_columnWidth / 2) / 2)), y, (x + ((_columnWidth / 2) / 2)), y);
                    g.drawLine((x - _columnOffset - ((_columnWidth / 2) / 2)), y + DiagramPanel.this.HEIGHT, (x + ((_columnWidth / 2) / 2)), y + DiagramPanel.this.HEIGHT);
                    x += _columnWidth + _columnOffset;
                }

                g.drawLine(20 + (OFFSET / 2), y + UP_HEIGHT, DiagramPanel.this.WIDTH + 10, y + UP_HEIGHT);

                count = new JLabel("" + _upperBound, JLabel.RIGHT);
                count.setVerticalAlignment(JLabel.NORTH);
                count.setForeground(Color.WHITE);
                count.setBounds(DiagramPanel.this.WIDTH, y, 45, 30);
                add(count);
                labels.addLast(count);
                count = new JLabel("" + (_upperBound / 2), JLabel.RIGHT);
                count.setVerticalAlignment(JLabel.NORTH);
                count.setForeground(Color.WHITE);
                count.setBounds(DiagramPanel.this.WIDTH, y + UP_HEIGHT / 2, 45, 30);
                add(count);
                labels.addLast(count);

                count = new JLabel("speed", JLabel.RIGHT);
                count.setVerticalAlignment(JLabel.NORTH);
                count.setForeground(Color.WHITE);
                count.setBounds(DiagramPanel.this.WIDTH, y + UP_HEIGHT - 30, 50, 30);
                add(count);
                labels.addLast(count);
                count = new JLabel("errors", JLabel.RIGHT);
                count.setVerticalAlignment(JLabel.NORTH);
                count.setForeground(Color.WHITE);
                count.setBounds(DiagramPanel.this.WIDTH, y + UP_HEIGHT + 10, 50, 30);
                add(count);
                labels.addLast(count);
                
                count = new JLabel("" + _lowerBound, JLabel.RIGHT);
                count.setVerticalAlignment(JLabel.EAST);
                count.setForeground(Color.WHITE);
                count.setBounds(DiagramPanel.this.WIDTH, y + DiagramPanel.this.HEIGHT - 30, 45, 30);
                add(count);
                labels.addLast(count);
            }
        };
        _graphicsPanel.setBackground(panelColor);
        add(_graphicsPanel, BorderLayout.CENTER);

        _backButton = new JButton(new ImageIcon(SimFrame.ICONS_DIR + "back_stats_icon.png"));
        _backButton.setBorderPainted(false);
        _backButton.setContentAreaFilled(false);
        _backButton.setFocusable(false);

        JPanel _backPanel = new JPanel();
        _backPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        _backPanel.setOpaque(false);
        _backPanel.add(_backButton);
        add(_backPanel, BorderLayout.PAGE_END);        
    }

    public JButton getBackButton() { return _backButton; }
    
    public DiagramPanel(int width, int height) {
        super();
        WIDTH = width - 50 - (OFFSET / 2);
        UP_HEIGHT = height - 250 - (OFFSET / 2);
        DOWN_HEIGHT = height - UP_HEIGHT - 100 - (OFFSET / 2);
        HEIGHT = height - 100 - (OFFSET / 2);
    }
    
    public void findParams() {
        _upperBound = findUpperBound(findMaxValue(true));
        _lowerBound = findLowerBound(findMaxValue(false));
        findColumnBounds();
        _upStep = findStep(true);
        _downStep = findStep(false);
        System.out.println("UP_HEIGHT: " + UP_HEIGHT + " DOWN_HEIGHT: " + DOWN_HEIGHT);

        _avgLabel.setText("<html>Average accuracy: " + findAvgAccuracy() + "%" +
                                        "<br/>Average speed: " + findAvgSpeed() + 
                                        "<br/>Average Errors: " + findAvgErrors() + "</html>");
    }

    public void setStats(ArrayList<Statistics> stats) {
        _statList = stats;
        findParams();
        revalidate();
    }

    private int findMaxValue(boolean isSpeed) {
        int max = 0;
        for (Statistics stat : _statList) {
            int i;
            if (isSpeed) i = stat.getSpeed();
            else i = stat.getErrors();
            if (i > max) max = i;
        }
        System.out.println("Max value: " + max);
        return max;
    }

    private int findUpperBound(int maxValue) {
        int bound;
        if (maxValue % 100 == 0) bound = maxValue;
        else if ( maxValue % 100 <= 50) bound = ((maxValue / 100) * 100) + 50;
        else bound = ((maxValue / 100) + 1) * 100;
        System.out.println("bound: " + bound);
        return bound;
    }

    private int findLowerBound(int maxValue) {
        int bound;
        if (maxValue % 10 == 0) bound = maxValue;
        else if ( maxValue % 10 <= 5) bound = ((maxValue / 10) * 10) + 5;
        else bound = ((maxValue / 10) + 1) * 10;
        System.out.println("bound: " + bound);
        return bound;
    }

    private double findStep(boolean isUpper) {
        double step;
        if (isUpper) step = (double)UP_HEIGHT / (double)_upperBound;
        else step = (double)DOWN_HEIGHT / (double)_lowerBound;
        System.out.println("Step: " + step);
        return step;
    }

    private void findColumnBounds() {
        _columnWidth = (WIDTH - (OFFSET / 2)) / _statList.size();
        _columnOffset = _columnWidth / 10;
        _columnWidth -= _columnOffset;
        System.out.println("_columnWidth: " + _columnWidth + " _columnOffset: " + _columnOffset);
    }

    private int findAvgAccuracy() {
        double avgAccuracy = 0;
        for (Statistics stat : _statList) avgAccuracy += stat.getAccuracy();
        System.out.println("avg accuracy sum: " + avgAccuracy);
        avgAccuracy /= _statList.size();
        return (int)avgAccuracy;
    }

    private int findAvgSpeed() {
        double avgSpeed = 0;
        for (Statistics stat : _statList) avgSpeed += stat.getSpeed();
        System.out.println("avg speed sum: " + avgSpeed);
        avgSpeed /= _statList.size();
        return (int)avgSpeed;
    }

    private int findAvgErrors() {
        double avgErrors = 0;
        for (Statistics stat : _statList) avgErrors += stat.getErrors();
        System.out.println("avg errors sum: " + avgErrors);
        avgErrors /= _statList.size();
        return (int)avgErrors;
    }
}
