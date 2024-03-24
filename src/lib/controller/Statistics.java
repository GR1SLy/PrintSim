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
        return "Finished! Accuracy: " + percent() + "%\tErrors: " + _redCount;
    }

    private int percent() {
        double percent = (double) _greenCount / (double) _keyCount;
        return (int)(percent * 100);
    }

    public void clear() {
        _greenCount = 0;
        _redCount = 0;
    }
}
