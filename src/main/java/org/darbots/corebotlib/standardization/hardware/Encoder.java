package org.darbots.corebotlib.standardization.hardware;

import org.darbots.corebotlib.calculations.valueholders.Angle;
import org.darbots.corebotlib.standardization.hardware.typedef.instances.EncoderTypeInstance;

public interface Encoder {
    EncoderTypeInstance getEncoderType();
    int getCurrentTick();
    Angle getCurrentAngularSpeed();
    double getCurrentTicksPerSecond();
}
