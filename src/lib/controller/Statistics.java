package lib.controller;

public class Statistics {

    private int _keyCount, _greenCount, _redCount;

    public Statistics(int keyCount) {
        super();
        _keyCount = keyCount;
    }

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

    public void clear() {
        _greenCount = 0;
        _redCount = 0;
    }
}
