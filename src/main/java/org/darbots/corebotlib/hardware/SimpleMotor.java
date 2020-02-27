package org.darbots.corebotlib.hardware;

import org.darbots.corebotlib.hardware.typedef.instances.MotorTypeInstance;

public interface SimpleMotor {
    MotorTypeInstance getMotorType();

    /**
     * Set current operation speed of the motor.
     * @param power fraction and direction of speed the motor should operate on, with range of [-1, 1]
     */
    void setPower(double power);

    /**
     * Get current operation speed of the motor
     * @return Power fraction and direction of speed the motor should operate on, with range of [-1, 1]
     */
    double getPower();

    /**
     * Is the direction of motor reversed?
     * @return true if the motor is reversed, false if the motor is not reversed
     */
    boolean isDirectionReversed();

    /**
     * Set direction of the motor
     * @param reversed true if you want the direciton of the motor reversed
     */
    void setDirectionReversed(boolean reversed);

    /**
     * Returns if the motor controller is applying an actual power not equal to 0
     * @return true if motor is running, false if the motor is stopped.
     */
    boolean isBusy();
}
