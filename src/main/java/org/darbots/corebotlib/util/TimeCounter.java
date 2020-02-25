package org.darbots.corebotlib.util;

public class TimeCounter {
    public long startMilliSeconds;
    public TimeCounter(){
        this.startMilliSeconds = System.currentTimeMillis();
    }
    public void reset(){
        this.startMilliSeconds = System.currentTimeMillis();
    }
    public double seconds(){
        return this.milliseconds() / 1000.0;
    }
    public long milliseconds(){
        return System.currentTimeMillis() - startMilliSeconds;
    }
}
