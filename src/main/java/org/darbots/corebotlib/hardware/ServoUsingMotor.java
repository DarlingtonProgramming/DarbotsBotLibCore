package org.darbots.corebotlib.hardware;

import org.darbots.corebotlib.calculations.Range;
import org.darbots.corebotlib.hardware.typedef.instances.CRServoTypeInstance;
import org.darbots.corebotlib.hardware.typedef.instances.ServoTypeInstance;

public class ServoUsingMotor implements Servo, AsyncDevice {
    private double targetPosition;
    private long encoderTickAtZeroPos;
    private PIDFMotor motor;
    private double targetPower;
    private double maximumPosition;
    private double minimumPosition;
    public Servo.ServoPositionReachedCallback positionReachedCallback = null;
    private boolean lastIsBusy = false;

    public ServoUsingMotor(PIDFMotor motor, double targetPower, double minPosition, double maxPosition, double initialPosition){
        this.setMotor(motor);
        this.motor.setCurrentRunMode(PIDFMotor.RunMode.PIDF_TO_POSITION);
        this.setTargetPower(targetPower);
        this.minimumPosition = minPosition;
        this.maximumPosition = maxPosition;
        this.calibrateCurrentPosition(initialPosition);
        this.targetPosition = initialPosition;
    }

    public ServoUsingMotor(ServoUsingMotor servoUsingMotor){
        this.setMotor(servoUsingMotor.motor);
        this.targetPosition = servoUsingMotor.targetPosition;
        this.encoderTickAtZeroPos = servoUsingMotor.encoderTickAtZeroPos;
        this.targetPower = servoUsingMotor.targetPower;
        this.maximumPosition = servoUsingMotor.maximumPosition;
        this.minimumPosition = servoUsingMotor.minimumPosition;
    }

    public double getPositionTolerance(){
        return ((double) this.motor.getPositionErrorToleranceTicks()) / this.motor.getEncoderType().getTicksPerRev();
    }

    public void setPositionTolerance(double tolerance){
        this.motor.setPositionErrorToleranceTicks((int) Math.round(tolerance * this.motor.getEncoderType().getTicksPerRev()));
    }

    public PIDFMotor getMotor(){
        return this.motor;
    }

    public void setMotor(PIDFMotor motor){
        this.motor = motor;
        this.lastIsBusy = motor.isBusy();
    }

    public double getTargetPower(){
        return this.targetPower;
    }

    public void setTargetPower(double power){
        this.targetPower = Range.clip(Math.abs(power),0.0,1.0);
        this.motor.setPower(this.targetPower);
    }

    public double getCurrentPosition(){
        long currentEncoderCount = this.motor.getCurrentTick();
        long deltaEncoderCount = currentEncoderCount - this.encoderTickAtZeroPos;
        double currentPosition = deltaEncoderCount / this.motor.getEncoderType().getTicksPerRev();
        return currentPosition;
    }

    @Override
    public double getTargetPosition() {
        return this.targetPosition;
    }

    public void calibrateCurrentPosition(double currentPosition){
        long currentEncoderCount = this.motor.getCurrentTick();
        long deltaEncoderCount = Math.round(currentPosition * this.motor.getEncoderType().getTicksPerRev());
        this.encoderTickAtZeroPos = currentEncoderCount - deltaEncoderCount;
    }

    public long targetPositionToTicks(double targetPosition){
        return Math.round(targetPosition * motor.getEncoderType().getTicksPerRev()) + encoderTickAtZeroPos;
    }

    public double encoderTicksToTargetPosition(long ticks){
        return (ticks - encoderTickAtZeroPos) / motor.getEncoderType().getTicksPerRev();
    }

    @Override
    public void setTargetPosition(double targetPosition) {
        targetPosition = Range.clip(targetPosition,this.minimumPosition,this.maximumPosition);
        this.motor.setTargetPositionTick(this.targetPositionToTicks(targetPosition));
        this.targetPosition = targetPosition;
        this.lastIsBusy = true;
    }

    public void setTargetPosition(double targetPosition, double targetPower){
        this.setTargetPower(targetPower);
        this.setTargetPosition(targetPosition);
    }

    @Override
    public ServoTypeInstance getServoType() {
        return new ServoTypeInstance(
                "ServoUsingMotor",
                "IntegratedSensors",
                1500,
                2500,
                this.getMaxAngleRange(),
                CRServoTypeInstance.getSecondsToTurn60Deg(this.motor.getMotorType().getMaximumRPM()),
                this.motor.getMotorType().getMinimumVoltage(),
                this.motor.getMotorType().getMaximumVoltage()
        );
    }

    @Override
    public boolean isBusy() {
        return motor.isBusy();
    }

    public double getMaxAngleRange(){
        return (this.maximumPosition - this.minimumPosition) * 360.0;
    }

    public double getMaximumPosition(){
        return this.maximumPosition;
    }

    public void setMaximumPosition(double maximumPosition){
        this.maximumPosition = maximumPosition;
    }

    public double getMinimumPosition(){
        return this.minimumPosition;
    }

    public void setMinimumPosition(double minimumPosition){
        this.minimumPosition = minimumPosition;
    }

    @Override
    public void update() {
        if(motor instanceof AsyncDevice){
            ((AsyncDevice) motor).update();
        }
        {
            boolean currentIsBusy = this.motor.isBusy();
            if (lastIsBusy && (!currentIsBusy)) {
                if (this.positionReachedCallback != null) {
                    this.positionReachedCallback.positionReached(this, this.targetPosition, this.getCurrentPosition());
                    this.positionReachedCallback = null;
                }
            }
            lastIsBusy = currentIsBusy;
        }
    }
}
