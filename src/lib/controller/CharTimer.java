package lib.controller;

import java.util.Timer;
import java.util.TimerTask;

import lib.ui.SimFrame;

//char/min = (char / sec) * 60
class CharTimer extends Timer {

    private class CharTimerTask extends TimerTask {
        @Override
        public void run() {
            if (_currentTime != 0.0) {
                _speedCount = (int)(((double)_simFrame.getTypedCount() / _currentTime) * 60);
                _simFrame.setSpeed(_speedCount);
            }
            _currentTime += 0.2;
        }
    }
    
    private double _currentTime = 0.0;

    private SimFrame _simFrame;

    private int _speedCount = 0;

    CharTimer(SimFrame simFrame) {
        super();
        _simFrame = simFrame;
    }

    void start() {
        schedule(new CharTimerTask(), 0, 200);
    }

    void stop() { cancel(); }

    int getSpeed() { return _speedCount; }

}


