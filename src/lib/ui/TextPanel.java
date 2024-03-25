package lib.ui;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class TextPanel extends JPanel {

    void setPhrase(String phrase) {
        clear();
        try {
            _doc.insertString(_doc.getLength(), phrase, _defauStyle);
            _doc.setParagraphAttributes(0, _doc.getLength(), _centeredStyle, false);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    void greenAtIndex(int index) {
        _doc.setCharacterAttributes(index, 1, _greenStyle, false);
    }

    void redAtIndex(int index, boolean isSpace) {
        if (isSpace) {
            try {
                _doc.remove(index, 1);
                _doc.insertString(index, "·", _defauStyle);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
        _doc.setCharacterAttributes(index, 1, _redStyle, false);
    }

    void clear() {
        try {
            _doc.remove(0, _doc.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    void requestEnter() {
        try {
            _doc.insertString(_doc.getLength(), "\nPress ENTER to continue...", _defauStyle);
            _doc.setParagraphAttributes(0, _doc.getLength(), _centeredStyle, false);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private JTextPane _textPane = new JTextPane();
    private StyledDocument _doc = _textPane.getStyledDocument();
    private Style _redStyle, _greenStyle, _defauStyle, _centeredStyle;

    {
        //<--------Panel settings-------->
        setBackground(new Color(30, 30, 30));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        add(_textPane, gbc);
        
        //<--------Styles-------->
        _defauStyle = _textPane.addStyle("Light Gray", null);
        StyleConstants.setForeground(_defauStyle, Color.LIGHT_GRAY);
        StyleConstants.setFontSize(_defauStyle, 20);
        _redStyle = _textPane.addStyle("Red", null);
        StyleConstants.setForeground(_redStyle, new Color(240, 90, 100));
        _greenStyle = _textPane.addStyle("Dark Gray", null);
        StyleConstants.setForeground(_greenStyle, new Color(100, 100, 100));
        _centeredStyle = _textPane.addStyle("Centered", null);
        StyleConstants.setAlignment(_centeredStyle, StyleConstants.ALIGN_CENTER);

        //<--------TextPane settings-------->
        _textPane.setEditable(false);
        _textPane.setFocusable(false);
        _textPane.setOpaque(false);
    }
    
}
