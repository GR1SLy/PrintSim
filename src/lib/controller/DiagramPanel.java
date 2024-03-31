package lib.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class DiagramPanel extends JPanel {

    private final int WIDTH, UP_HEIGHT, DOWN_HEIGHT, HEIGHT;

    private ArrayList<Statistics> _statList;

    private int _upperBound, _lowerBound, _columnWidth, _columnOffset;
    private double _upStep, _downStep;

    private JLabel _mainLabel;

    private JPanel _graphicsPanel;
    
    {
        Color panelColor = new Color(44, 43, 51);

        setLayout(new BorderLayout());
        setBackground(panelColor);

        _mainLabel = new JLabel("Stats", SwingConstants.CENTER);
        _mainLabel.setForeground(Color.WHITE);
        add(_mainLabel, BorderLayout.PAGE_START);

        _graphicsPanel = new JPanel() {
            private static final Color SPEED_RECT = new Color(240, 170, 110);
            private static final Color ERROR_RECT = new Color(240, 160, 160);
            private static final Color LINE = new Color(100, 100, 100);
            private JLabel count;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                int x = 20;
                for (Statistics stat : _statList) {
                    int speed = stat.getSpeed();
                    int errors = stat.getErrors();
                    int columnHeight = (int)(speed * _upStep);
                    g.setColor(SPEED_RECT);
                    g.fillRect(x, UP_HEIGHT - columnHeight, _columnWidth, columnHeight);

                    g.setColor(ERROR_RECT);
                    g.fillRect(x, UP_HEIGHT, _columnWidth, (int)(errors * _downStep));

                    count = new JLabel("" + speed, JLabel.CENTER);
                    count.setVerticalAlignment(JLabel.NORTH);
                    count.setForeground(Color.WHITE);
                    count.setBounds(x, UP_HEIGHT - columnHeight, _columnWidth, 30);
                    add(count);

                    g.setColor(LINE);
                    g.drawLine((x - _columnOffset - ((_columnWidth / 2) / 2)), UP_HEIGHT / 2, (x + ((_columnWidth / 2) / 2)), UP_HEIGHT / 2);
                    g.drawLine((x - _columnOffset - ((_columnWidth / 2) / 2)), 0, (x + ((_columnWidth / 2) / 2)), 0);
                    g.drawLine((x - _columnOffset - ((_columnWidth / 2) / 2)), DiagramPanel.this.HEIGHT, (x + ((_columnWidth / 2) / 2)), DiagramPanel.this.HEIGHT);
                    x += _columnWidth + _columnOffset;
                }

                g.drawLine(20, UP_HEIGHT, DiagramPanel.this.WIDTH + 10, UP_HEIGHT);
                count = new JLabel("" + _upperBound, JLabel.RIGHT);
                count.setVerticalAlignment(JLabel.NORTH);
                count.setForeground(Color.WHITE);
                count.setBounds(DiagramPanel.this.WIDTH, 0, 45, 30);
                add(count);
                count = new JLabel("" + (_upperBound / 2), JLabel.RIGHT);
                count.setVerticalAlignment(JLabel.NORTH);
                count.setForeground(Color.WHITE);
                count.setBounds(DiagramPanel.this.WIDTH, UP_HEIGHT / 2, 45, 30);
                add(count);

                count = new JLabel("speed", JLabel.RIGHT);
                count.setVerticalAlignment(JLabel.NORTH);
                count.setForeground(Color.WHITE);
                count.setBounds(DiagramPanel.this.WIDTH, UP_HEIGHT - 30, 50, 30);
                add(count);
                count = new JLabel("errors", JLabel.RIGHT);
                count.setVerticalAlignment(JLabel.NORTH);
                count.setForeground(Color.WHITE);
                count.setBounds(DiagramPanel.this.WIDTH, UP_HEIGHT + 10, 50, 30);
                add(count);
                
                count = new JLabel("" + _lowerBound, JLabel.RIGHT);
                count.setVerticalAlignment(JLabel.EAST);
                count.setForeground(Color.WHITE);
                count.setBounds(DiagramPanel.this.WIDTH, DiagramPanel.this.HEIGHT - 30, 45, 30);
                add(count);
            }
        };
        _graphicsPanel.setBackground(panelColor);
        add(_graphicsPanel, BorderLayout.CENTER);
    }

    public DiagramPanel(int width, int height, ArrayList<Statistics> statList) {
        super();
        _statList = statList;
        WIDTH = width - 50;
        UP_HEIGHT = height - 200;
        DOWN_HEIGHT = height - UP_HEIGHT - 50;
        HEIGHT = height - 50;
        findParams();
    }
    
    public void findParams() {
        _upperBound = findUpperBound(findMaxValue(true));
        _lowerBound = findLowerBound(findMaxValue(false));
        findColumnBounds();
        _upStep = findStep(UP_HEIGHT);
        _downStep = findStep(DOWN_HEIGHT);
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

    private double findStep(int height) {
        double step;
        if (height == UP_HEIGHT) step = (double)height / (double)_upperBound;
        else step = (double)height / (double)_lowerBound;
        System.out.println("Step: " + step);
        return step;
    }

    private void findColumnBounds() {
        _columnWidth = WIDTH / _statList.size();
        _columnOffset = _columnWidth / 10;
        _columnWidth -= _columnOffset;
        System.out.println("_columnWidth: " + _columnWidth + " _columnOffset: " + _columnOffset);
    }
}
