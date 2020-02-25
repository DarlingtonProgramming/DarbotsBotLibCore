package org.darbots.corebotlib.control;

public class PIDCoefficients {
    public double Kp;
    public double Ki;
    public double Kd;
    public PIDCoefficients(){
        this(0,0,0);
    }
    public PIDCoefficients(double Kp, double Ki, double Kd){
        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;
    }
    public PIDCoefficients(PIDCoefficients coefficients){
        this.Kp = coefficients.Kp;
        this.Ki = coefficients.Ki;
        this.Kd = coefficients.Kd;
    }
}
