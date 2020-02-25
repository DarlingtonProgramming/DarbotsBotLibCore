package org.darbots.corebotlib.control;

import org.darbots.corebotlib.util.TimeCounter;

public class PIDFController {
    public double targetPosition;
    public double lastError;
    public double integratedError;
    public PIDCoefficients pidCoefficients;
    private TimeCounter timeCounter;
    public PIDFController(PIDCoefficients coefficients){
        this(0,coefficients);
    }
    public PIDFController(double targetPosition, PIDCoefficients coefficients){
        this.targetPosition = targetPosition;
        this.lastError = 0;
        this.integratedError = 0;
        this.pidCoefficients = coefficients;
        this.timeCounter = new TimeCounter();
    }
    public PIDFController(PIDFController controller){
        this.targetPosition = controller.targetPosition;
        this.lastError = controller.lastError;
        this.integratedError = controller.integratedError;
        this.pidCoefficients = controller.pidCoefficients;
        this.timeCounter = new TimeCounter();
    }
    public double update(double currentPosition){
        if(this.pidCoefficients instanceof PIDFCoefficients){
            return this.update(currentPosition,((PIDFCoefficients) this.pidCoefficients).Kf);
        }else{
            return this.update(currentPosition,0);
        }
    }
    public double update(double currentPosition, double PIDFOverride){
        double deltaTime = this.timeCounter.seconds();
        double error = this.targetPosition - currentPosition;
        double integratedError = this.integratedError + error * deltaTime;
        double derivativeError = (error - this.lastError) / deltaTime;

        double PIDPower = (this.pidCoefficients.Kp * error) + (this.pidCoefficients.Ki * integratedError) + (this.pidCoefficients.Kd * derivativeError);
        double PIDFPower = PIDPower + PIDFOverride;

        this.lastError = error;
        this.integratedError = integratedError;
        this.timeCounter.reset();

        return PIDFPower;
    }
    public void targetReset(){
        this.lastError = 0;
        this.integratedError = 0;
        this.timeCounter.reset();
    }
}
