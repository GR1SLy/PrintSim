package lib.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import lib.ui.SimFrame;

public class KeyChecker extends KeyAdapter {

    private SimFrame _simFrame;

    private String _text;
    private int _index = 0;

    private boolean _isEnd = false;

    public KeyChecker(SimFrame simframe) {
        super();
        _simFrame = simframe;
    }

    public void setText(String text) { _text = text; }
    
    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyChar());
        if (!_isEnd) {
            if (e.getKeyChar() == _text.charAt(_index)) {
                //green
                _simFrame.greenAtIndex(_index);
            } else {
                //red
                _simFrame.redAtIndex(_index);
            }
            _index++;
            if (_index >= _text.length()) {
                _isEnd = true;
                _simFrame.textEnd();
            }
        } else {
            //press enter
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                _simFrame.requestText();
                _simFrame.clear();
                _index = 0;
                _isEnd = false;
            }
        }
    }
}
