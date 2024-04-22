package lib.ui;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
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
            _doc.setCharacterAttributes(0, 1, _caretHighlight, false);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    void clearAtIndex(int index, boolean isSpace) {
        _doc.setCharacterAttributes(index, 1, _defauStyle, false);
        if (isSpace) {
            try {
                _doc.remove(index, 1);
                _doc.insertString(index, " ", _defauStyle);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
        setBackCaret(index);
    }

    void greenAtIndex(int index) {
        _doc.setCharacterAttributes(index, 1, _greenStyle, false);
        setCaret(index);
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
        setCaret(index);
    }

    void setCaret(int index) {
        _doc.setCharacterAttributes(index, 1, _caretNormal, false);
        _doc.setCharacterAttributes(index + 1, 1, _caretHighlight, false);
    }

    void setBackCaret(int index) {
        _doc.setCharacterAttributes(index + 1, 1, _caretNormal, false);
        _doc.setCharacterAttributes(index, 1, _caretHighlight, false);
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
    private SimpleAttributeSet _caretHighlight, _caretNormal;

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

        //<--------Caret-------->
        _caretHighlight = new SimpleAttributeSet();
        StyleConstants.setBackground(_caretHighlight, new Color(100, 130, 200, 80));
        _caretNormal = new SimpleAttributeSet();
        StyleConstants.setBackground(_caretNormal, new Color(0, 0, 0, 0));

        //<--------TextPane settings-------->
        _textPane.setEditable(false);
        _textPane.setFocusable(false);
        _textPane.setOpaque(false);
    }
    
}
