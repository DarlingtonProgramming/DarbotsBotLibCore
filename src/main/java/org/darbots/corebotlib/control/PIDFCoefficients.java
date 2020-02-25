package org.darbots.corebotlib.control;

public class PIDFCoefficients extends PIDCoefficients {
    public double Kf;
    public PIDFCoefficients(){
        this(0,0,0,0);
    }
    public PIDFCoefficients(double Kp, double Ki, double Kd, double Kf){
        super(Kp,Ki,Kd);
        this.Kf = Kf;
    }
    public PIDFCoefficients(PIDCoefficients pidCoefficients, double Kf){
        super(pidCoefficients);
        this.Kf = Kf;
    }
    public PIDFCoefficients(PIDFCoefficients coefficients){
        super(coefficients);
        this.Kf = coefficients.Kf;
    }
}
