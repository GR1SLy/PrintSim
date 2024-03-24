package lib.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import lib.ui.SimFrame;

public class KeyChecker extends KeyAdapter {

    private SimFrame _simFrame;

    private String _text;
    private int _index = 0;

    private CharTimer _timer;

    private boolean _isEnd = false;

    public KeyChecker(SimFrame simframe) {
        super();
        _simFrame = simframe;
    }

    public void setText(String text) { _text = text; }
    
    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyChar());
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            _simFrame.requestText();
            _simFrame.clear();
            _index = 0;
            _isEnd = false;
        }
        else if (!_isEnd) {
            if (e.getKeyCode() != KeyEvent.VK_SHIFT) {
                if (_index == 0) { 
                    _timer = new CharTimer(_simFrame);
                    _timer.start(); 
                }
                if (e.getKeyChar() == _text.charAt(_index)) {
                    //green
                    _simFrame.greenAtIndex(_index);
                } else {
                    //red
                    _simFrame.redAtIndex(_index, _text.charAt(_index) == ' ');
                }
                _index++;
            }
            if (_index >= _text.length()) {
                _isEnd = true;
                _timer.stop();
                _simFrame.textEnd();
            }
        }
    }
}
