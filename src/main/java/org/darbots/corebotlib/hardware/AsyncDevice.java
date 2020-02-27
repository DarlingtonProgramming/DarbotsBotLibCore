package org.darbots.corebotlib.hardware;

public interface AsyncDevice {
    boolean isBusy();
    void update();
}
