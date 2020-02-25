package org.darbots.corebotlib.standardization.hardware.typedef.instances;

import org.darbots.corebotlib.standardization.hardware.typedef.annotations.CRServoType;
import org.darbots.corebotlib.standardization.hardware.typedef.annotations.ServoType;

public class ServoTypeInstance {
    private String name;
    private String manufacturer;
    private double pwmMicroSecMin;
    private double pwmMicroSecMax;
    private double maxAngle;
    private double secondsToTurn60Deg;
    private double minimumVoltage;
    private double maximumVoltage;
    public ServoTypeInstance(String name, String manufacturer, double pwmMicroSecMin, double pwmMicroSecMax, double maxAngle, double secondsToTurn60Deg, double minimumVoltage, double maximumVoltage){
        this.name = name;
        this.manufacturer = manufacturer;
        this.pwmMicroSecMin = pwmMicroSecMin;
        this.pwmMicroSecMax = pwmMicroSecMax;
        this.maxAngle = maxAngle;
        this.secondsToTurn60Deg = secondsToTurn60Deg;
        this.minimumVoltage = minimumVoltage;
        this.maximumVoltage = maximumVoltage;
    }
    public ServoTypeInstance(ServoTypeInstance servoTypeInstance){
        this.name = servoTypeInstance.name;
        this.manufacturer = servoTypeInstance.manufacturer;
        this.pwmMicroSecMin = servoTypeInstance.pwmMicroSecMin;
        this.pwmMicroSecMax = servoTypeInstance.pwmMicroSecMax;
        this.maxAngle = servoTypeInstance.maxAngle;
        this.secondsToTurn60Deg = servoTypeInstance.secondsToTurn60Deg;
        this.minimumVoltage = servoTypeInstance.minimumVoltage;
        this.maximumVoltage = servoTypeInstance.maximumVoltage;
    }
    public ServoTypeInstance(Class<?> servoTypeClass) throws IllegalArgumentException{
        ServoType servoType = servoTypeClass.getAnnotation(ServoType.class);
        if(servoType == null){
            throw new IllegalArgumentException("Parameter servoTypeClass must be an instance of servoType Annotation Class!");
        }
        this.name = servoType.name();
        this.manufacturer = servoType.manufacturer();
        this.pwmMicroSecMin = servoType.pwmMicroSecMin();
        this.pwmMicroSecMax = servoType.pwmMicroSecMax();
        this.maxAngle = servoType.maxAngle();
        this.secondsToTurn60Deg = servoType.secondsToTurn60Deg();
        this.minimumVoltage = servoType.minimumVoltage();
        this.maximumVoltage = servoType.maximumVoltage();
    }
    public static ServoTypeInstance getInstance(Class<?> servoTypeClass) throws IllegalArgumentException{
        return new ServoTypeInstance(servoTypeClass);
    }
    public String getServoName(){
        return this.name;
    }
    public void setServoName(String servoName){
        this.name = servoName;
    }
    public String getManufacturer(){
        return this.manufacturer;
    }
    public void setManufacturer(String manufacturer){
        this.manufacturer = manufacturer;
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
    public double getMaxAngle(){
        return this.maxAngle;
    }
    public void setMaxAngle(double maxAngle){
        this.maxAngle = maxAngle;
    }
    public double getSecondsToTurn60Deg(){
        return this.secondsToTurn60Deg;
    }
    public void setSecondsToTurn60Deg(double secondsToTurn60Deg){
        this.secondsToTurn60Deg = secondsToTurn60Deg;
    }
    public double getMinimumVoltage(){
        return this.minimumVoltage;
    }
    public void setMinimumVoltage(double minimumVoltage){
        this.minimumVoltage = minimumVoltage;
    }
    public double getMaximumVoltage(){
        return this.maximumVoltage;
    }
    public void setMaximumVoltage(double maximumVoltage){
        this.maximumVoltage = maximumVoltage;
    }
}
