package org.darbots.corebotlib.localizer;

import org.darbots.corebotlib.calculations.BotMath;
import org.darbots.corebotlib.calculations.geometry.Pose2D;
import org.darbots.corebotlib.calculations.units.AngleUnit;
import org.darbots.corebotlib.calculations.valueholders.Angle;
import org.darbots.corebotlib.calculations.valueholders.NormalizedAngle;
import org.darbots.corebotlib.hardware.AsyncDevice;
import org.darbots.corebotlib.util.ElapsedTimer;

public class AsyncLocalizer extends LocalizeMethodContainer implements AsyncDevice {
    private boolean enableTracking = false;
    private ElapsedTimer timer = null;
    private Pose2D currentPose = null;

    public AsyncLocalizer(LocalizeMethod method, Pose2D initialPose){
        this.setReceiver(method);
        this.__setup();
        this.setPoseEstimate(initialPose);
    }

    public AsyncLocalizer(LocalizeMethodContainer container){
        this.setReceiver(container.getReceiver());
        this.__setup();
        this.setPoseEstimate(container.getPoseEstimate());
    }

    protected void __setup(){
        this.currentPose = new Pose2D(0,0,new NormalizedAngle(0, AngleUnit.RADIAN));
        this.timer = new ElapsedTimer();
        this.enableTracking = false;
    }

    @Override
    public Pose2D getPoseEstimate() {
        return new Pose2D(this.currentPose);
    }

    @Override
    public void setPoseEstimate(Pose2D pose) {
        if(pose == null){
            return;
        }
        this.currentPose.X = pose.X;
        this.currentPose.Y = pose.Y;
        this.currentPose.headingNormalizedAngle.setAngle(
                pose.headingNormalizedAngle.asUnit(this.currentPose.headingNormalizedAngle.getAngleUnit())
        );
    }

    @Override
    public void updatePositionWithRobotAxisDelta(Pose2D robotAxisDelta) {
        Pose2D newPose = BotMath.getAbsolutePosition(this.currentPose,robotAxisDelta);
        if(this.getHeadingProvider() != null){
            Angle headingProviderReading = this.getHeadingProvider().getHeading();
            newPose.headingNormalizedAngle = this.getCurrentHeading(headingProviderReading);
        }
        this.currentPose.X = newPose.X;
        this.currentPose.Y = newPose.Y;
        this.currentPose.headingNormalizedAngle.setAngle(
                newPose.headingNormalizedAngle.asUnit(this.currentPose.headingNormalizedAngle.getAngleUnit())
        );
    }

    @Override
    public void updatePositionWithFieldAbsolutePosition(Pose2D fieldPose) {
        Pose2D newPose = new Pose2D(fieldPose);
        if(this.getHeadingProvider() != null){
            Angle headingProviderReading = this.getHeadingProvider().getHeading();
            newPose.headingNormalizedAngle = this.getCurrentHeading(headingProviderReading);
        }
        this.currentPose.X = newPose.X;
        this.currentPose.Y = newPose.Y;
        this.currentPose.headingNormalizedAngle.setAngle(
                newPose.headingNormalizedAngle.asUnit(this.currentPose.headingNormalizedAngle.getAngleUnit())
        );
    }

    @Override
    public void start() {
        if(this.enableTracking){
            return;
        }
        this.enableTracking = true;
        this.getReceiver().startReceive();
    }

    @Override
    public void stop() {
        if(!this.enableTracking){
            return;
        }
        this.enableTracking = false;
        this.getReceiver().stopReceive();
    }

    @Override
    public boolean isBusy() {
        return this.enableTracking;
    }

    @Override
    public void update() {
        double time = timer.seconds();
        timer.reset();
        if(!this.enableTracking){
            this.getReceiver().receiveDataWithoutPositionShift(time);
        }else {
            this.getReceiver().receiveData(time);
        }
    }
}
