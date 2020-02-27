package org.darbots.corebotlib.localizer;

import org.darbots.corebotlib.runtime_support.ControlLoopUtil;
import org.darbots.corebotlib.runtime_support.threadmanager.GlobalThreadRegister;
import org.darbots.corebotlib.runtime_support.threadmanager.ProgramCycleThreadRegister;
import org.darbots.corebotlib.calculations.BotMath;
import org.darbots.corebotlib.calculations.geometry.Pose2D;
import org.darbots.corebotlib.calculations.units.AngleUnit;
import org.darbots.corebotlib.calculations.valueholders.Angle;
import org.darbots.corebotlib.calculations.valueholders.NormalizedAngle;
import org.darbots.corebotlib.hardware.typedef.Destroyable;
import org.darbots.corebotlib.util.ElapsedTimer;

public class SeparateThreadLocalizer extends LocalizeMethodContainer implements Destroyable {
    private class SeparateThreadLocalizerRunnable implements Runnable{
        public volatile boolean runningCommand = false;
        public volatile boolean runningFlag = false;

        @Override
        public void run() {
            runningFlag = true;
            ElapsedTimer timer = new ElapsedTimer();
            try {
                ControlLoopUtil.clearData();
            }catch(Exception e){
                e.printStackTrace();
            }
            getReceiver().startReceive();
            timer.reset();
            while(this.runningCommand){
                try {
                    ControlLoopUtil.clearData();
                }catch(Exception e){
                    e.printStackTrace();
                }
                double time = timer.seconds();
                timer.reset();
                getReceiver().receiveData(time);
            }
            getReceiver().stopReceive();
            runningFlag = false;
            runningCommand = false;
        }

        public boolean isRunning(){
            return runningCommand || runningFlag;
        }

        public void stop(){
            this.runningCommand = false;
        }
    }
    private Pose2D currentPose = null;
    private SeparateThreadLocalizerRunnable m_Runnable = null;
    private Thread m_Thread = null;

    public SeparateThreadLocalizer(LocalizeMethod method, Pose2D initialPose){
        this.setReceiver(method);
        this.__setup();
        this.setPoseEstimate(initialPose);
    }

    public SeparateThreadLocalizer(LocalizeMethodContainer container){
        this.setReceiver(container.getReceiver());
        this.__setup();
        this.setPoseEstimate(container.getPoseEstimate());
    }

    protected void __setup(){
        this.currentPose = new Pose2D(0,0,new NormalizedAngle(0,AngleUnit.RADIAN));
        this.m_Runnable = new SeparateThreadLocalizerRunnable();
        GlobalThreadRegister.registerDestroyable(this);
    }

    @Override
    public Pose2D getPoseEstimate() {
        synchronized (currentPose){
            return new Pose2D(currentPose);
        }
    }

    @Override
    public void setPoseEstimate(Pose2D pose) {
        synchronized (currentPose) {
            currentPose.X = pose.X;
            currentPose.Y = pose.Y;
            currentPose.headingNormalizedAngle.setAngle(
                    pose.headingNormalizedAngle.asUnit(currentPose.headingNormalizedAngle.getAngleUnit())
            );
        }
    }

    @Override
    public void updatePositionWithRobotAxisDelta(Pose2D robotAxisDelta) {
        synchronized (this.currentPose) {
            Pose2D newPose = BotMath.getAbsolutePosition(this.currentPose, robotAxisDelta);
            if (this.getHeadingProvider() != null) {
                Angle headingProviderReading = this.getHeadingProvider().getHeading();
                newPose.headingNormalizedAngle = this.getCurrentHeading(headingProviderReading);
            }
            this.currentPose.X = newPose.X;
            this.currentPose.Y = newPose.Y;
            this.currentPose.headingNormalizedAngle.setAngle(
                    newPose.headingNormalizedAngle.asUnit(this.currentPose.headingNormalizedAngle.getAngleUnit())
            );
        }
    }

    @Override
    public void updatePositionWithFieldAbsolutePosition(Pose2D fieldPose) {
        synchronized (this.currentPose) {
            Pose2D newPose = new Pose2D(fieldPose);
            if (this.getHeadingProvider() != null) {
                Angle headingProviderReading = this.getHeadingProvider().getHeading();
                newPose.headingNormalizedAngle = this.getCurrentHeading(headingProviderReading);
            }
            this.currentPose.X = newPose.X;
            this.currentPose.Y = newPose.Y;
            this.currentPose.headingNormalizedAngle.setAngle(
                    newPose.headingNormalizedAngle.asUnit(this.currentPose.headingNormalizedAngle.getAngleUnit())
            );
        }
    }

    @Override
    public void start() {
        if(this.m_Runnable.isRunning()){
            return;
        }
        this.m_Thread = new Thread(this.m_Runnable);
        this.m_Runnable.runningCommand = true;
        this.m_Thread.start();
    }

    @Override
    public void stop() {
        if(!this.m_Runnable.isRunning()){
            return;
        }
        this.m_Runnable.stop();
    }

    @Override
    public void terminate() {
        this.stop();
    }
}
