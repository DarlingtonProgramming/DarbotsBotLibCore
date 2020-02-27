package org.darbots.corebotlib.hardware;

public interface DataReceiverContainer{
    DataReceiver getReceiver();
    void setReceiver(DataReceiver receiver);
    void start();
    void stop();
}
