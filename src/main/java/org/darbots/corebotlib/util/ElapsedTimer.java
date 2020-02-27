package org.darbots.corebotlib.util;

public class ElapsedTimer {
    public long startMilliSeconds;
    public ElapsedTimer(){
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
