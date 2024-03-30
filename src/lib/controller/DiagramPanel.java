package lib.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class DiagramPanel extends JPanel {

    private final int WIDTH, HEIGHT;

    private ArrayList<Statistics> _statList;

    private int _upperBound, _columnWidth, _columnOffset;
    private double _step;

    private JLabel _upperBoundLabel, _middleBoundLabel, _mainLabel;
    private JPanel _boundLabelPanel;

    private JPanel _graphicsPanel;
    
    {
        Color panelColor = new Color(44, 43, 51);

        setLayout(new BorderLayout());
        setBackground(panelColor);

        _mainLabel = new JLabel("Stats", SwingConstants.CENTER);
        _mainLabel.setForeground(Color.WHITE);
        add(_mainLabel, BorderLayout.PAGE_START);

        _upperBoundLabel = new JLabel("", SwingConstants.LEFT);
        _upperBoundLabel.setForeground(Color.WHITE);
        _upperBoundLabel.setVerticalAlignment(JLabel.NORTH);

        _middleBoundLabel = new JLabel("", SwingConstants.LEFT);
        _middleBoundLabel.setForeground(Color.WHITE);
        _middleBoundLabel.setVerticalAlignment(JLabel.NORTH);

        _boundLabelPanel = new JPanel();
        _boundLabelPanel.setLayout(new GridLayout(2, 1));
        _boundLabelPanel.setOpaque(false);
        _boundLabelPanel.add(_upperBoundLabel);
        _boundLabelPanel.add(_middleBoundLabel);

        add(_boundLabelPanel, BorderLayout.EAST);

        _graphicsPanel = new JPanel() {
            private int x = 20;
            private static final Color RECT = new Color(240, 170, 110);
            private static final Color LINE = new Color(100, 100, 100);
            private JLabel count;

            @Override
            public void paintComponent(Graphics g) {
                for (Statistics stat : _statList) {
                    int i = stat.getSpeed();
                    int columnHeight = (int)(i * _step);
                    g.setColor(RECT);
                    g.fillRect(x, DiagramPanel.this.HEIGHT - columnHeight, _columnWidth, columnHeight);

                    count = new JLabel("" + i, JLabel.CENTER);
                    count.setVerticalAlignment(JLabel.NORTH);
                    count.setForeground(Color.WHITE);
                    count.setBounds(x, DiagramPanel.this.HEIGHT - columnHeight, _columnWidth, 30);
                    add(count);

                    g.setColor(LINE);
                    g.drawLine((x - _columnOffset - ((_columnWidth / 2) / 2)), DiagramPanel.this.HEIGHT / 2, (x + ((_columnWidth / 2) / 2)), DiagramPanel.this.HEIGHT / 2);
                    g.drawLine((x - _columnOffset - ((_columnWidth / 2) / 2)), 0, (x + ((_columnWidth / 2) / 2)), 0);
                    x += _columnWidth + _columnOffset;
                }
            }
        };
        _graphicsPanel.setBackground(panelColor);
        add(_graphicsPanel, BorderLayout.CENTER);
    }

    public DiagramPanel(int width, int height, ArrayList<Statistics> statList) {
        super();
        _statList = statList;
        WIDTH = width - 50;
        HEIGHT = height - 10;
        setDiagram();
    }
    
    public void findParams() {
        findUpperBound(findMaxValue());
        findColumnBounds();
        findStep();   
    }

    private int findMaxValue() {
        int max = 0;
        for (Statistics stat : _statList) {
            int i = stat.getSpeed();
            if (i > max) max = i;
        }
        System.out.println("Max value: " + max);
        return max;
    }

    private void findUpperBound(int maxValue) {
        if (maxValue % 100 == 0) _upperBound = maxValue;
        else if ( maxValue % 100 <= 50) _upperBound = ((maxValue / 100) * 100) + 50;
        else _upperBound = ((maxValue / 100) + 1) * 100;
        System.out.println("upperBound: " + _upperBound);
    }

    private void findStep() {
        _step = (double)HEIGHT / (double)_upperBound;
        System.out.println("_step: " + _step);
    }

    private void findColumnBounds() {
        int width = WIDTH;
        _columnWidth = width / _statList.size();
        _columnOffset = _columnWidth / 10;
        _columnWidth -= _columnOffset;
        System.out.println("_columnWidth: " + _columnWidth + " _columnOffset: " + _columnOffset);
    }

    public void setDiagram() {
        findParams();
        _upperBoundLabel.setText("" + _upperBound + "  ");
        _middleBoundLabel.setText("" + (_upperBound / 2));
        System.out.println("Diagram");
    }
}
