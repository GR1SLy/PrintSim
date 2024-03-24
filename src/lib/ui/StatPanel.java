package lib.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class StatPanel extends JPanel {
    private JLabel _accuracyLabel, _errorLabel,
                   _accuracyPercentLabel, _errorCountLabel,
                   _speedLabel, _speedCountLabel;
    private JPanel _accuracyPanel, _errorPanel, _speedPanel, _mainPanel;
    {
        //<--------Panel settings-------->
        setLayout(new FlowLayout());
        _mainPanel = new JPanel();
        _mainPanel.setLayout(new GridLayout(1, 3, 100, 0));
        _mainPanel.setOpaque(false);
        add(_mainPanel);
        setBackground(new Color(30, 30, 30));

        //<--------Accuracy-------->
        _accuracyPercentLabel = new JLabel("100%", SwingConstants.CENTER);
        _accuracyPercentLabel.setFont(new Font("Lucida Console", Font.BOLD, 24));
        _accuracyPercentLabel.setForeground(Color.LIGHT_GRAY);

        _accuracyLabel = new JLabel("Accuracy", SwingConstants.LEFT);
        _accuracyLabel.setFont(new Font("Lucida Console", Font.ITALIC, 12));
        _accuracyLabel.setForeground(Color.LIGHT_GRAY);

        _accuracyPanel = new JPanel();
        _accuracyPanel.setLayout(new BorderLayout());
        _accuracyPanel.setOpaque(false);
        _accuracyPanel.add(_accuracyPercentLabel, BorderLayout.CENTER);
        _accuracyPanel.add(_accuracyLabel, BorderLayout.PAGE_END);

        _mainPanel.add(_accuracyPanel);

        //<--------Errors-------->
        _errorCountLabel = new JLabel("0", SwingConstants.CENTER);
        _errorCountLabel.setFont(new Font("Lucida Console", Font.BOLD, 24));
        _errorCountLabel.setForeground(Color.LIGHT_GRAY);

        _errorLabel = new JLabel("Errors", SwingConstants.LEFT);
        _errorLabel.setFont(new Font("Lucida Console", Font.ITALIC, 12));
        _errorLabel.setForeground(Color.LIGHT_GRAY);

        _errorPanel = new JPanel();
        _errorPanel.setLayout(new BorderLayout());
        _errorPanel.setOpaque(false);
        _errorPanel.add(_errorCountLabel, BorderLayout.CENTER);
        _errorPanel.add(_errorLabel, BorderLayout.PAGE_END);

        _mainPanel.add(_errorPanel);

        //<--------Speed-------->
        _speedCountLabel = new JLabel("0", SwingConstants.CENTER);
        _speedCountLabel.setFont(new Font("Lucida Console", Font.BOLD, 24));
        _speedCountLabel.setForeground(Color.LIGHT_GRAY);

        _speedLabel = new JLabel("char/min", SwingConstants.LEFT);
        _speedLabel.setFont(new Font("Lucida Console", Font.ITALIC, 12));
        _speedLabel.setForeground(Color.LIGHT_GRAY);

        _speedPanel = new JPanel();
        _speedPanel.setLayout(new BorderLayout());
        _speedPanel.setOpaque(false);
        _speedPanel.add(_speedCountLabel, BorderLayout.CENTER);
        _speedPanel.add(_speedLabel, BorderLayout.PAGE_END);

        _mainPanel.add(_speedPanel);
    }

    void setAccuracy(int accuracy) {
        _accuracyPercentLabel.setText(accuracy + "%");
    }

    void setErrors(int errors) {
        _errorCountLabel.setText("" + errors);
    }

    void setSpeed(int speed) {
        _speedCountLabel.setText("" + speed);
    }

    void clear() {
        _accuracyPercentLabel.setText("100%");
        _errorCountLabel.setText("0");
        _speedCountLabel.setText("0");
    }
}
