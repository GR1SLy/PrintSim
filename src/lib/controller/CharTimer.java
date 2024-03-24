package lib.controller;

import java.util.Timer;
import java.util.TimerTask;

import lib.ui.SimFrame;

//char/min = (char / sec) * 60
public class CharTimer extends Timer {

    private class CharTimerTask extends TimerTask {
        @Override
        public void run() {
            if (_currentTime != 0.0) {
                _speedCount = (int)(((double)_simFrame.getTypedCount() / _currentTime) * 60);
                // _speedCount = (int)(speedCount);
                _simFrame.setSpeed(_speedCount);
            }
            _currentTime += 0.2;
        }
    }
    
    private double _currentTime = 0.0;

    private SimFrame _simFrame;

    private int _speedCount = 0;

    public CharTimer(SimFrame simFrame) {
        super();
        _simFrame = simFrame;
    }

    public void start() {
        schedule(new CharTimerTask(), 0, 200);
    }

    public void stop() { cancel(); }
}


