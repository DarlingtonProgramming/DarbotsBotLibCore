package org.darbots.corebotlib.hardware;

public interface DataReceiver<E extends DataReceiverContainer> {
    void startReceive();
    void stopReceive();
    void receiveData(double secondsBetweenCalls);
    E getContainer();
    void setContainer(E container);
}
