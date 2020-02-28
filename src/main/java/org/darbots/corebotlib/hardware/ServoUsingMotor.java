package org.darbots.corebotlib.hardware;

import org.darbots.corebotlib.hardware.typedef.instances.ServoTypeInstance;

public class ServoUsingMotor implements Servo, AsyncDevice {
    
    @Override
    public double getTargetPosition() {
        return 0;
    }

    @Override
    public void setTargetPosition(double targetPosition) {

    }

    @Override
    public ServoTypeInstance getServoType() {
        return null;
    }

    @Override
    public boolean isBusy() {
        return false;
    }

    @Override
    public void update() {

    }
}
