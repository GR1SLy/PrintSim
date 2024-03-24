package lib.ui;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.Color;

public class TextPanel extends JPanel {

    void setText(String text) {
        clear();
        try {
            _doc.insertString(_doc.getLength(), text, _defauStyle);
            _doc.setParagraphAttributes(0, _doc.getLength(), _centeredStyle, false);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    void greenAtIndex(int index) {
        _doc.setCharacterAttributes(index, 1, _greenStyle, false);
    }

    void redAtIndex(int index) {
        _doc.setCharacterAttributes(index, 1, _redStyle, false);
    }

    void clear() {
        try {
            _doc.remove(0, _doc.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    void pressEnter() {
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
        setBackground(Color.BLACK);
        add(_textPane);
        
        //<--------Styles-------->
        _defauStyle = _textPane.addStyle("Light Gray", null);
        StyleConstants.setForeground(_defauStyle, Color.LIGHT_GRAY);
        _redStyle = _textPane.addStyle("Red", null);
        StyleConstants.setForeground(_redStyle, Color.RED);
        _greenStyle = _textPane.addStyle("Dark Gray", null);
        StyleConstants.setForeground(_greenStyle, Color.DARK_GRAY);
        _centeredStyle = _textPane.addStyle("Centered", null);
        StyleConstants.setAlignment(_centeredStyle, StyleConstants.ALIGN_CENTER);

        //<--------TextPane settings-------->
        _textPane.setEditable(false);
        _textPane.setFocusable(false);
        _textPane.setOpaque(false);
    }
    
}
