package org.darbots.corebotlib.localizer;

import org.darbots.corebotlib.calculations.geometry.Pose2D;
import org.darbots.corebotlib.hardware.Gyro;

public interface Localizer {
    Pose2D getPoseEstimate();
    void setPoseEstimate(Pose2D pose);
    Gyro getHeadingProvider();
    void setHeadingProvider(Gyro provider);
}
