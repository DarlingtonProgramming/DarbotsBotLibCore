package org.darbots.corebotlib.gyro;

import org.darbots.corebotlib.calculations.units.AngleUnit;
import org.darbots.corebotlib.calculations.valueholders.Angle;
import org.darbots.corebotlib.hardware.AsyncDevice;
import org.darbots.corebotlib.util.ElapsedTimer;

public class AsyncGyro extends GyroMethodContainer implements AsyncDevice {
    private Angle angle;
    private boolean gyroEnable = false;
    private ElapsedTimer timer;

    public AsyncGyro(GyroMethod method){
        this.setReceiver(method);
        this.__setup();
    }

    public AsyncGyro(GyroMethodContainer container){
        this.setReceiver(container.getReceiver());
        this.__setup();
        this.setHeading(container.getHeading());
    }

    protected void __setup(){
        this.angle = new Angle(0, AngleUnit.RADIAN);
        this.timer = new ElapsedTimer();
    }

    @Override
    public void setHeading(Angle heading) {
        this.angle.setAngle(
                heading.asUnit(this.angle.getAngleUnit())
        );
    }

    @Override
    public void start() {
        if(this.gyroEnable){
            return;
        }
        this.gyroEnable = true;
        this.timer.reset();
        this.getReceiver().startReceive();
    }

    @Override
    public void stop() {
        if(!this.gyroEnable){
            return;
        }
        this.gyroEnable = false;
        this.getReceiver().stopReceive();
    }

    @Override
    public Angle getHeading() {
        return new Angle(this.angle);
    }

    @Override
    public boolean isBusy() {
        return this.gyroEnable;
    }

    @Override
    public void update() {
        if(!this.gyroEnable){
            return;
        }
        double time = this.timer.seconds();
        this.timer.reset();
        this.getReceiver().receiveData(time);
    }
}
