package org.darbots.corebotlib.gyro;

import org.darbots.corebotlib.hardware.DataReceiver;

public abstract class GyroMethod implements DataReceiver<GyroMethodContainer> {
    private GyroMethodContainer container = null;

    @Override
    public GyroMethodContainer getContainer() {
        return this.container;
    }

    @Override
    public void setContainer(GyroMethodContainer container) {
        this.container = container;
    }
}
