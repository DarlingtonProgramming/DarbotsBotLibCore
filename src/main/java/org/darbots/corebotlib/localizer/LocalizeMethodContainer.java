package org.darbots.corebotlib.localizer;

import org.darbots.corebotlib.calculations.geometry.Pose2D;
import org.darbots.corebotlib.calculations.geometry.Vector2D;
import org.darbots.corebotlib.calculations.units.AngleUnit;
import org.darbots.corebotlib.calculations.valueholders.Angle;
import org.darbots.corebotlib.calculations.valueholders.NormalizedAngle;
import org.darbots.corebotlib.hardware.AsyncDevice;
import org.darbots.corebotlib.hardware.DataReceiver;
import org.darbots.corebotlib.hardware.DataReceiverContainer;
import org.darbots.corebotlib.hardware.Gyro;

public abstract class LocalizeMethodContainer implements DataReceiverContainer<LocalizeMethod> {
    private LocalizeMethod receiver = null;
    private Gyro headingProvider = null;
    private double headingProviderReadingAtZero;

    public abstract Pose2D getPoseEstimate();
    public abstract void setPoseEstimate(Pose2D pose);

    @Override
    public LocalizeMethod getReceiver() {
        return this.receiver;
    }

    @Override
    public void setReceiver(LocalizeMethod receiver) {
        this.receiver = receiver;
        this.receiver.setContainer(this);
    }

    public Gyro getHeadingProvider(){
        return this.headingProvider;
    }

    public void setHeadingProvider(Gyro provider){
        if(provider instanceof AsyncDevice){
            ((AsyncDevice) provider).update();
        }
        double currentReading = provider.getHeading().asRadian();
        Pose2D currentPose = this.getPoseEstimate();
        this.headingProviderReadingAtZero = currentReading - currentPose.headingNormalizedAngle.asRadian();
        this.headingProvider = provider;
    }

    public void updateHeadingProvider(){
        if(this.headingProvider != null && this.headingProvider instanceof AsyncDevice){
            ((AsyncDevice) headingProvider).update();
        }
    }

    public NormalizedAngle getCurrentHeading(Angle headingProviderReading){
        return new NormalizedAngle(headingProviderReading.asRadian() - this.headingProviderReadingAtZero, AngleUnit.RADIAN);
    }

    public abstract void updatePositionWithRobotAxisDelta(Pose2D robotAxisDelta);
    public abstract void updatePositionWithFieldAbsolutePosition(Pose2D fieldPose);
}
