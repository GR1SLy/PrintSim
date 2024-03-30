package lib.controller;

import java.io.Serializable;

public class Statistics implements Serializable {

    private static final long serialVersionUID = 1L;

    private transient int _keyCount, _greenCount; 
    private int _redCount, _accuracy, _speed;

    public Statistics() {
        super();
    }

    public void setKeyCount(int keyCount) { _keyCount = keyCount; }

    public void greenIncreace() { _greenCount++; }

    public void redIncreace() { _redCount++; }

    public String getStat() {
        return "Finished! Accuracy: " + getAccuracy(_keyCount - 1) + "%\tErrors: " + _redCount;
    }

    public int getAccuracy(int index) {
        double percent = (double) _greenCount / (double) (index + 1);
        return (int)(percent * 100);
    }

    public int getErrors() { return _redCount; }

    public int getTypedCount() { return _greenCount + _redCount; }

    public int getSpeed() { return _speed; }

    public void clear() {
        _greenCount = 0;
        _redCount = 0;
    }

    public Statistics save(int speed) {
        Statistics res = new Statistics();
        res._accuracy = this.getAccuracy(_keyCount);
        res._redCount = this._redCount;
        res._speed = speed;
        return res;
    }

    @Override
    public String toString() {
        return "Speed: " + _speed + "\tErrors: " + _redCount + "\tAccuracy: " + _accuracy + "\n";
    }
}
