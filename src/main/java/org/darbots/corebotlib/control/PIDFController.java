package org.darbots.corebotlib.control;

import org.darbots.corebotlib.calculations.Range;
import org.darbots.corebotlib.util.ElapsedTimer;

public class PIDFController {
    public double targetPosition;
    public double lastError;
    public double integratedError;
    public PIDCoefficients pidCoefficients;
    private ElapsedTimer timeCounter;
    public PIDFController(PIDCoefficients coefficients){
        this(0,coefficients);
    }
    public PIDFController(double targetPosition, PIDCoefficients coefficients){
        this.targetPosition = targetPosition;
        this.lastError = 0;
        this.integratedError = 0;
        this.pidCoefficients = coefficients;
        this.timeCounter = new ElapsedTimer();
    }
    public PIDFController(PIDFController controller){
        this.targetPosition = controller.targetPosition;
        this.lastError = controller.lastError;
        this.integratedError = controller.integratedError;
        this.pidCoefficients = controller.pidCoefficients;
        this.timeCounter = new ElapsedTimer();
    }
    public double update(double currentPosition, double powerCap){
        if(this.pidCoefficients instanceof PIDFCoefficients){
            return this.update(currentPosition, powerCap, ((PIDFCoefficients) this.pidCoefficients).Kf);
        }else{
            return this.update(currentPosition, powerCap, 0);
        }
    }
    public double update(double currentPosition, double powerCap, double FOverride){
        powerCap = Math.abs(powerCap);

        double deltaTime = this.timeCounter.seconds();
        double error = this.targetPosition - currentPosition;
        double integratedError = this.integratedError + error * deltaTime;
        double derivativeError = (error - this.lastError) / deltaTime;

        double PIDPower = (this.pidCoefficients.Kp * error) + (this.pidCoefficients.Ki * integratedError) + (this.pidCoefficients.Kd * derivativeError);
        double PIDFPower = PIDPower + FOverride;

        this.lastError = error;
        if(Math.abs(PIDFPower) >= powerCap) {
            //Maximum Power Reached, Clip power, and don't add any more integral.
            PIDFPower = Range.clip(PIDFPower,-powerCap,powerCap);
        }else{
            this.integratedError = integratedError;
        }
        this.timeCounter.reset();

        return PIDFPower;
    }
    public void targetReset(){
        this.lastError = 0;
        this.timeCounter.reset();
    }
}
