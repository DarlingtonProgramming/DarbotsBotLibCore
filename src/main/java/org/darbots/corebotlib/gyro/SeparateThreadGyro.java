package org.darbots.corebotlib.gyro;

import org.darbots.corebotlib.runtime_support.threadmanager.GlobalThreadRegister;
import org.darbots.corebotlib.runtime_support.threadmanager.ProgramCycleThreadRegister;
import org.darbots.corebotlib.calculations.units.AngleUnit;
import org.darbots.corebotlib.calculations.valueholders.Angle;
import org.darbots.corebotlib.hardware.typedef.Destroyable;
import org.darbots.corebotlib.util.ElapsedTimer;

public class SeparateThreadGyro extends GyroMethodContainer implements Destroyable {
    private class SeparateThreadGyroRunnable implements Runnable{
        private volatile boolean runningCommand = false;
        private volatile boolean runningFlag = false;

        @Override
        public void run() {
            runningFlag = true;
            ElapsedTimer timer = new ElapsedTimer();
            getReceiver().startReceive();
            timer.reset();
            while(this.runningCommand){
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
    private Angle angle;
    private SeparateThreadGyroRunnable m_Runnable = null;
    private Thread m_Thread = null;

    public SeparateThreadGyro(GyroMethod method){
        this.setReceiver(method);
        this.__setup();
    }

    public SeparateThreadGyro(GyroMethodContainer container){
        this.setReceiver(container.getReceiver());
        this.__setup();
        this.setHeading(container.getHeading());
    }

    protected void __setup(){
        this.angle = new Angle(0, AngleUnit.RADIAN);
        this.m_Runnable = new SeparateThreadGyroRunnable();
        GlobalThreadRegister.registerDestroyable(this);
    }

    @Override
    public void setHeading(Angle heading) {
        synchronized (this.angle) {
            this.angle.setAngle(
                    heading.asUnit(this.angle.getAngleUnit())
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

    @Override
    public Angle getHeading() {
        synchronized (this.angle) {
            return new Angle(this.angle);
        }
    }
}
