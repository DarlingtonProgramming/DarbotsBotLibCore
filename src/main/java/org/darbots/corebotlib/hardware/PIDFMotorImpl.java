package org.darbots.corebotlib.hardware;

import org.darbots.corebotlib.calculations.units.AngleUnit;
import org.darbots.corebotlib.calculations.valueholders.Angle;
import org.darbots.corebotlib.control.PIDFCoefficients;
import org.darbots.corebotlib.control.PIDFController;
import org.darbots.corebotlib.hardware.typedef.instances.EncoderTypeInstance;
import org.darbots.corebotlib.hardware.typedef.instances.MotorTypeInstance;

public class PIDFMotorImpl implements PIDFMotor, AsyncDevice {
    public SimpleMotor motor;
    public Encoder encoder;
    private int targetPosition = 0;
    private RunMode currentRunMode = RunMode.NO_PIDF_WITH_SPEED;
    private double currentPower = 0.0;
    private PIDFController SpeedPIDFController = new PIDFController(new PIDFCoefficients());
    private PIDFController PositionPIDFController = new PIDFController(new PIDFCoefficients());
    private int errorToleranceTicks = 2;
    public PIDFMotorImpl(SimpleMotor motor, Encoder encoder){
        this.motor = motor;
        this.encoder = encoder;
    }
    public PIDFMotorImpl(PIDFMotorImpl motorImpl){
        this.motor = motorImpl.motor;
        this.encoder = motorImpl.encoder;
        this.targetPosition = motorImpl.targetPosition;
        this.currentRunMode = RunMode.NO_PIDF_WITH_SPEED;
        this.currentPower = 0.0;
        this.SpeedPIDFController.pidCoefficients = new PIDFCoefficients((PIDFCoefficients) motorImpl.SpeedPIDFController.pidCoefficients);
        this.PositionPIDFController.pidCoefficients = new PIDFCoefficients((PIDFCoefficients) motorImpl.PositionPIDFController.pidCoefficients);
        this.errorToleranceTicks = motorImpl.errorToleranceTicks;
    }
    @Override
    public int getTargetPositionTick() {
        return this.targetPosition;
    }

    @Override
    public void setTargetPositionTick(int targetPositionTick) {
        this.targetPosition = targetPositionTick;
    }

    @Override
    public RunMode getCurrentRunMode() {
        return this.currentRunMode;
    }

    @Override
    public void setCurrentRunMode(RunMode mode) {
        if(mode != null) {
            this.currentRunMode = mode;
            this.update();
        }
    }

    @Override
    public PIDFCoefficients getSpeedPIDFCoefficients() {
        return (PIDFCoefficients) SpeedPIDFController.pidCoefficients;
    }

    @Override
    public void setSpeedPIDFCoefficients(PIDFCoefficients coefficients) {
        if(coefficients != null){
            SpeedPIDFController.pidCoefficients.Kp = coefficients.Kp;
            SpeedPIDFController.pidCoefficients.Ki = coefficients.Ki;
            SpeedPIDFController.pidCoefficients.Kd = coefficients.Kd;
            ((PIDFCoefficients) SpeedPIDFController.pidCoefficients).Kf = coefficients.Kf;
        }
    }

    @Override
    public PIDFCoefficients getPositionPIDFCoefficients() {
        return (PIDFCoefficients) PositionPIDFController.pidCoefficients;
    }

    @Override
    public void setPositionPIDFCoefficients(PIDFCoefficients coefficients) {
        if(coefficients != null){
            PositionPIDFController.pidCoefficients.Kp = coefficients.Kp;
            PositionPIDFController.pidCoefficients.Ki = coefficients.Ki;
            PositionPIDFController.pidCoefficients.Kd = coefficients.Kd;
            ((PIDFCoefficients) PositionPIDFController.pidCoefficients).Kf = coefficients.Kf;
        }
    }

    @Override
    public int getPositionErrorToleranceTicks() {
        return this.errorToleranceTicks;
    }

    @Override
    public void setPositionErrorToleranceTicks(int ticks) {
        this.errorToleranceTicks = ticks;
    }

    @Override
    public EncoderTypeInstance getEncoderType() {
        if(this.encoder == null){
            return null;
        }else {
            return this.encoder.getEncoderType();
        }
    }

    @Override
    public int getCurrentTick() {
        if(this.encoder == null){
            return 0;
        }else{
            return this.encoder.getCurrentTick();
        }
    }

    @Override
    public Angle getCurrentAngularSpeed() {
        if(this.encoder == null){
            return new Angle(0, AngleUnit.RADIAN);
        }else{
            return this.encoder.getCurrentAngularSpeed();
        }
    }

    @Override
    public double getCurrentTicksPerSecond() {
        if(this.encoder == null){
            return 0;
        }else{
            return this.encoder.getCurrentTicksPerSecond();
        }
    }

    @Override
    public MotorTypeInstance getMotorType() {
        if(this.motor == null){
            return null;
        }else{
            return this.motor.getMotorType();
        }
    }

    @Override
    public void setPower(double power) {
        this.currentPower = power;
        this.update();
    }

    @Override
    public double getPower() {
        return this.currentPower;
    }

    @Override
    public boolean isDirectionReversed() {
        if(this.motor == null){
            return false;
        }else{
            return this.motor.isDirectionReversed();
        }
    }

    @Override
    public void setDirectionReversed(boolean reversed) {
        if(this.motor != null)
            this.motor.setDirectionReversed(reversed);
        if(this.encoder != null)
            this.encoder.setDirectionReversed(reversed);
    }

    @Override
    public void resetTicks() {
        if(this.encoder != null){
            this.encoder.resetTicks();
        }
    }

    @Override
    public boolean isBusy() {
        RunMode runMode = this.getCurrentRunMode();
        switch(runMode){
            case PIDF_WITH_SPEED:
            case NO_PIDF_WITH_SPEED:
                if(this.currentPower != 0){
                    return true;
                }else{
                    return false;
                }
            case PIDF_TO_POSITION:
                if(Math.abs(this.getTargetPositionTick() - this.getCurrentTick()) <= this.getPositionErrorToleranceTicks()
                        || this.currentPower == 0){
                    return false;
                }else{
                    return true;
                }
            default:
                return false;
        }
    }

    @Override
    public void update() {
        if(!this.isBusy()){
            this.stop();
        }
        RunMode runMode = this.getCurrentRunMode();
        double currentTPS = 0;
        double maxTPS = 0;
        if(this.encoder != null){
            currentTPS = this.encoder.getCurrentTicksPerSecond();
        }
        if(this.getMotorType() != null && this.getEncoderType() != null){
            maxTPS = this.getMotorType().getTheoreticalMaxTPS(this.getEncoderType());
        }
        switch(runMode){
            case NO_PIDF_WITH_SPEED:
                if(this.motor != null)
                    this.motor.setPower(this.currentPower);
                break;
            case PIDF_WITH_SPEED:
                this.applyEncoderPower(currentTPS,maxTPS,this.currentPower);
                break;
            case PIDF_TO_POSITION:
                this.applyPIDFToPosition(this.getCurrentTick(),this.getTargetPositionTick(),currentTPS,maxTPS,this.currentPower);
                break;
            default:
                break;
        }
    }

    protected void applyEncoderPower(double currentTPS, double maxTPS, double power){
        if(this.motor == null){
            return;
        }
        double targetTPS = power * maxTPS;
        if(targetTPS != this.SpeedPIDFController.targetPosition){
            this.SpeedPIDFController.targetPosition = targetTPS;
            this.SpeedPIDFController.targetReset();
        }
        double pidfPower = this.SpeedPIDFController.update(currentTPS,1.0);
        this.motor.setPower(pidfPower);
    }

    protected void applyPIDFToPosition(int currentPosition, int targetPosition, double currentTPS, double maxTPS, double power){
        if(this.motor == null){
            return;
        }
        if(targetPosition != this.PositionPIDFController.targetPosition){
            this.PositionPIDFController.targetPosition = targetPosition;
            this.PositionPIDFController.targetReset();
        }
        double targetPIDFSpeed = this.PositionPIDFController.update(currentPosition,targetPosition);
        targetPIDFSpeed *= power;
        this.applyEncoderPower(currentTPS,maxTPS,targetPIDFSpeed);
    }

    public void stop(){
        if(this.motor != null){
            this.motor.setPower(0);
        }
    }
}
