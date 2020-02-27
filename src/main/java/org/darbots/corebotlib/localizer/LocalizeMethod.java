package org.darbots.corebotlib.localizer;

import org.darbots.corebotlib.hardware.DataReceiver;

public abstract class LocalizeMethod implements DataReceiver<LocalizeMethodContainer> {
    private LocalizeMethodContainer container;
    @Override
    public LocalizeMethodContainer getContainer() {
        return this.container;
    }

    @Override
    public void setContainer(LocalizeMethodContainer container) {
        this.container = container;
    }
    public abstract void receiveDataWithoutPositionShift(double secondsBetweenCalls);
}
