package org.darbots.corebotlib.hardware;

import org.darbots.corebotlib.hardware.typedef.instances.ServoTypeInstance;

public interface Servo {
    public static interface ServoPositionReachedCallback{
        void positionReached(Servo servo, double targetPosition, double actualPosition);
    }
    /**
     * Get last set position
     * @return Last set position in range of [-1,1]
     */
    double getTargetPosition();

    /**
     * Set a target position for servo
     * @param targetPosition target position in range [-1, 1]
     */
    void setTargetPosition(double targetPosition);
    ServoTypeInstance getServoType();
}
