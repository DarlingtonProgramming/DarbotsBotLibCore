package org.darbots.corebotlib.hardware;

public interface DataReceiverContainer<E extends DataReceiver>{
    E getReceiver();
    void setReceiver(E receiver);
    void start();
    void stop();
}
