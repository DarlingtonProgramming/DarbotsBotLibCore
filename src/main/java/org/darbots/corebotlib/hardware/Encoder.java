package org.darbots.corebotlib.hardware;

import org.darbots.corebotlib.calculations.valueholders.Angle;
import org.darbots.corebotlib.hardware.typedef.instances.EncoderTypeInstance;

public interface Encoder {
    EncoderTypeInstance getEncoderType();
    int getCurrentTick();
    Angle getCurrentAngularSpeed();
    double getCurrentTicksPerSecond();
    boolean isDirectionReversed();
    void setDirectionReversed(boolean directionReversed);
    void resetTicks();
}
