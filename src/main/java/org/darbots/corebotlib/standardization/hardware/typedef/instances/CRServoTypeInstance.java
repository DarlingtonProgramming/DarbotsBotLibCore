package org.darbots.corebotlib.standardization.hardware.typedef.instances;

import org.darbots.corebotlib.standardization.hardware.typedef.annotations.CRServoType;

public class CRServoTypeInstance extends MotorTypeInstance {
    private double pwmMicroSecMin;
    private double pwmMicroSecMax;
    public static double getRPM(double secondsToTurn60Deg){
        return 60.0 / (secondsToTurn60Deg * (360.0 / 60.0));
    }
    public static double getSecondsToTurn60Deg(double RPM){
        return (60.0 / 360.0) / (RPM / 60.0);
    }
    public CRServoTypeInstance(String name, String manufacturer, double pwmMicroSecMin, double pwmMicroSecMax, double secondsToTurn60Deg, double minimumVoltage, double maximumVoltage){
        super(name,manufacturer,minimumVoltage,maximumVoltage,getRPM(secondsToTurn60Deg));
        this.pwmMicroSecMin = pwmMicroSecMin;
        this.pwmMicroSecMax = pwmMicroSecMax;
    }
    public CRServoTypeInstance(CRServoTypeInstance crServoTypeInstance){
        super(crServoTypeInstance);
        this.pwmMicroSecMin = crServoTypeInstance.pwmMicroSecMin;
        this.pwmMicroSecMax = crServoTypeInstance.pwmMicroSecMax;
    }
    public CRServoTypeInstance(Class<?> crServoTypeClass) throws IllegalArgumentException{
        super("","",0,0,0);
        CRServoType crServoType = crServoTypeClass.getAnnotation(CRServoType.class);
        if(crServoType == null){
            throw new IllegalArgumentException("Parameter crServoTypeClass must be an instance of CRServoType Annotation Class!");
        }
        super.setMotorName(crServoType.name());
        super.setManufacturer(crServoType.manufacturer());
        super.setMinimumVoltage(crServoType.minimumVoltage());
        super.setMaximumVoltage(crServoType.maximumVoltage());
        super.setMaximumRPM(getRPM(crServoType.secondsToTurn60Deg()));
        this.pwmMicroSecMin = crServoType.pwmMicroSecMin();
        this.pwmMicroSecMax = crServoType.pwmMicroSecMax();
    }
    public static CRServoTypeInstance getInstance(Class<?> crServoTypeClass) throws IllegalArgumentException{
        return new CRServoTypeInstance(crServoTypeClass);
    }
    public double getPwmMicroSecMin() {
        return this.pwmMicroSecMin;
    }
    public void setPwmMicroSecMin(double pwmMicroSecMin){
        this.pwmMicroSecMin = pwmMicroSecMin;
    }
    public double getPwmMicroSecMax(){
        return this.pwmMicroSecMax;
    }
    public void setPwmMicroSecMax(double pwmMicroSecMax){
        this.pwmMicroSecMax = pwmMicroSecMax;
    }
    public double getSecondsToTurn60Deg(){
        return getSecondsToTurn60Deg(this.getMaximumRPM());
    }
    public void setSecondsToTurn60Deg(double secondsToTurn60Deg){
        this.setMaximumRPM(getRPM(secondsToTurn60Deg));
    }
}
