package org.darbots.corebotlib.standardization.hardware.typedef.instances;

import org.darbots.corebotlib.standardization.hardware.typedef.annotations.MotorType;

import java.lang.annotation.Annotation;

public class MotorTypeInstance {
    private String name;
    private String manufacturer;
    private double minimumVoltage;
    private double maximumVoltage;
    private double maximumRPM;
    public MotorTypeInstance(String name, String manufacturer, double minimumVoltage, double maximumVoltage, double maximumRPM){
        this.name = name;
        this.manufacturer = manufacturer;
        this.minimumVoltage = minimumVoltage;
        this.maximumVoltage = maximumVoltage;
        this.maximumRPM = maximumRPM;
    }
    public MotorTypeInstance(MotorTypeInstance motorTypeInstance){
        this.name = motorTypeInstance.name;
        this.manufacturer = motorTypeInstance.manufacturer;
        this.minimumVoltage = motorTypeInstance.minimumVoltage;
        this.maximumVoltage = motorTypeInstance.maximumVoltage;
        this.maximumRPM = motorTypeInstance.maximumRPM;
    }
    public MotorTypeInstance(Class<?> motorTypeClass) throws IllegalArgumentException{
        MotorType mTypeAnnotation = motorTypeClass.getAnnotation(MotorType.class);
        if(mTypeAnnotation == null){
            throw new IllegalArgumentException("Parameter motorTypeClass must be an instance of MotorType Annotation Class!");
        }
        this.name = mTypeAnnotation.name();
        this.manufacturer = mTypeAnnotation.manufacturer();
        this.minimumVoltage = mTypeAnnotation.minimumVoltage();
        this.maximumVoltage = mTypeAnnotation.maximumVoltage();
        this.maximumRPM = mTypeAnnotation.maxRPM();
    }
    public static MotorTypeInstance getInstance(Class<?> motorTypeClass) throws IllegalArgumentException{
        return new MotorTypeInstance(motorTypeClass);
    }
    public String getMotorName(){
        return this.name;
    }
    public void setMotorName(String motorName){
        this.name = motorName;
    }
    public String getManufacturer(){
        return this.manufacturer;
    }
    public void setManufacturer(String manufacturer){
        this.manufacturer = manufacturer;
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
    public double getMaximumRPM(){
        return this.maximumRPM;
    }
    public void setMaximumRPM(double maxRPM){
        this.maximumRPM = maxRPM;
    }
}
