package org.darbots.corebotlib.hardware;

import org.darbots.corebotlib.control.PIDFCoefficients;

public interface PIDFMotor extends SimpleMotor, Encoder {
    public enum RunMode{
        PIDF_TO_POSITION,
        PIDF_WITH_SPEED,
        NO_PIDF_WITH_SPEED
    }
    int getTargetPositionTick();
    void setTargetPositionTick(int targetPositionTick);
    RunMode getCurrentRunMode();
    void setCurrentRunMode(RunMode mode);
    PIDFCoefficients getSpeedPIDFCoefficients();
    void setSpeedPIDFCoefficients(PIDFCoefficients coefficients);
    PIDFCoefficients getPositionPIDFCoefficients();
    void setPositionPIDFCoefficients(PIDFCoefficients coefficients);
    int getPositionErrorToleranceTicks();
    void setPositionErrorToleranceTicks(int ticks);
}
