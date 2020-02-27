package org.darbots.corebotlib.gyro;

import org.darbots.corebotlib.calculations.valueholders.Angle;
import org.darbots.corebotlib.hardware.DataReceiverContainer;
import org.darbots.corebotlib.hardware.Gyro;

public abstract class GyroMethodContainer implements DataReceiverContainer<GyroMethod>, Gyro {
    private GyroMethod method = null;

    @Override
    public GyroMethod getReceiver() {
        return this.method;
    }

    @Override
    public void setReceiver(GyroMethod receiver) {
        this.method = receiver;
        this.method.setContainer(this);
    }
    public abstract void setHeading(Angle heading);
}
