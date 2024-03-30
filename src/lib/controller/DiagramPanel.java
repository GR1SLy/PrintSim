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
        setLayout(new BorderLayout());

        _mainLabel = new JLabel("Stats", SwingConstants.CENTER);
        add(_mainLabel, BorderLayout.PAGE_START);

        _upperBoundLabel = new JLabel("", SwingConstants.LEFT);
        _upperBoundLabel.setVerticalAlignment(JLabel.NORTH);

        _middleBoundLabel = new JLabel("", SwingConstants.LEFT);
        _middleBoundLabel.setVerticalAlignment(JLabel.NORTH);

        _boundLabelPanel = new JPanel();
        _boundLabelPanel.setLayout(new GridLayout(2, 1));
        _boundLabelPanel.add(_upperBoundLabel);
        _boundLabelPanel.add(_middleBoundLabel);

        add(_boundLabelPanel, BorderLayout.EAST);

        _graphicsPanel = new JPanel() {
            private int x = 20;

            @Override
            public void paintComponent(Graphics g) {
                g.setColor(Color.BLACK);
                for (Statistics stat : _statList) {
                    int i = stat.getSpeed();
                    int columnHeight = (int)(i * _step);
                    g.drawRect(x, DiagramPanel.this.HEIGHT - columnHeight, _columnWidth, columnHeight);
                    g.drawLine((x - _columnOffset - ((_columnWidth / 2) / 2)), DiagramPanel.this.HEIGHT / 2, (x + ((_columnWidth / 2) / 2)), DiagramPanel.this.HEIGHT / 2);
                    g.drawLine((x - _columnOffset - ((_columnWidth / 2) / 2)), 0, (x + ((_columnWidth / 2) / 2)), 0);
                    x += _columnWidth + _columnOffset;
                }
            }
        };
        add(_graphicsPanel, BorderLayout.CENTER);
    }

    public DiagramPanel(int width, int height, ArrayList<Statistics> _statList) {
        super();
        this._statList = _statList;
        WIDTH = width - 50;
        HEIGHT = height - 10;
        setDiagram();
    }
    
    public void test() {
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
        test();
        _upperBoundLabel.setText("" + _upperBound + "  ");
        _middleBoundLabel.setText("" + (_upperBound / 2));
        System.out.println("Diagram");
    }
}
