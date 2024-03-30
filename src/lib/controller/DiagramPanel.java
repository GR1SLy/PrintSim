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

    private ArrayList<Statistics> statList;

    private int upperBound, columnWidth, columnOffset;
    private double step;

    private JLabel upperBoundLabel, middleBoundLabel, mainLabel;
    private JPanel boundLabelPanel;

    private JPanel graphicsPanel;
    
    {
        setLayout(new BorderLayout());

        mainLabel = new JLabel("Stats", SwingConstants.CENTER);
        add(mainLabel, BorderLayout.PAGE_START);

        upperBoundLabel = new JLabel("", SwingConstants.LEFT);
        upperBoundLabel.setVerticalAlignment(JLabel.NORTH);

        middleBoundLabel = new JLabel("", SwingConstants.LEFT);
        middleBoundLabel.setVerticalAlignment(JLabel.NORTH);

        boundLabelPanel = new JPanel();
        boundLabelPanel.setLayout(new GridLayout(2, 1));
        boundLabelPanel.add(upperBoundLabel);
        boundLabelPanel.add(middleBoundLabel);

        add(boundLabelPanel, BorderLayout.EAST);

        graphicsPanel = new JPanel() {
            private int x = 20;

            @Override
            public void paintComponent(Graphics g) {
                g.setColor(Color.BLACK);
                for (Statistics stat : statList) {
                    int i = stat.getSpeed();
                    int columnHeight = (int)(i * step);
                    g.drawRect(x, DiagramPanel.this.HEIGHT - columnHeight, columnWidth, columnHeight);
                    x += columnWidth + columnOffset;
                }
                x = 0;//(columnWidth / 2) + ((columnWidth / 2) / 2);
                while (x <= DiagramPanel.this.WIDTH) {
                    g.drawLine(x, 0, x + ((columnWidth / 2) / 2) + columnOffset, 0);
                    g.drawLine(x, DiagramPanel.this.HEIGHT / 2, x + ((columnWidth / 2) / 2) + columnOffset, DiagramPanel.this.HEIGHT / 2);
                    x += (columnWidth / 2) + columnOffset;
                }
            }
        };
        add(graphicsPanel, BorderLayout.CENTER);
    }

    public DiagramPanel(int width, int height, ArrayList<Statistics> statList) {
        super();
        this.statList = statList;
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
        for (Statistics stat : statList) {
            int i = stat.getSpeed();
            if (i > max) max = i;
        }
        System.out.println("Max value: " + max);
        return max;
    }

    private void findUpperBound(int maxValue) {
        if (maxValue % 100 == 0) upperBound = maxValue;
        else if ( maxValue % 100 <= 50) upperBound = ((maxValue / 100) * 100) + 50;
        else upperBound = ((maxValue / 100) + 1) * 100;
        System.out.println("upperBound: " + upperBound);
    }

    private void findStep() {
        step = (double)HEIGHT / (double)upperBound;
        System.out.println("Step: " + step);
    }

    private void findColumnBounds() {
        int width = WIDTH;
        columnWidth = width / statList.size();
        columnOffset = columnWidth / 10;
        columnWidth -= columnOffset;
        System.out.println("ColumnWidth: " + columnWidth + " ColumnOffset: " + columnOffset);
    }

    public void setDiagram() {
        test();
        upperBoundLabel.setText("" + upperBound + "  ");
        middleBoundLabel.setText("" + (upperBound / 2));
        // graphicsPanel.repaint();
        System.out.println("Diagram");
    }
}
