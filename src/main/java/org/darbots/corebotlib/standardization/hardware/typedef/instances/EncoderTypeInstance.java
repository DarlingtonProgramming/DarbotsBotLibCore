package org.darbots.corebotlib.standardization.hardware.typedef.instances;

import org.darbots.corebotlib.standardization.hardware.typedef.annotations.EncoderType;

public class EncoderTypeInstance {
    private String name;
    private String manufacturer;
    private double operatingVoltage;
    private double ticksPerRev;
    public EncoderTypeInstance(String name, String manufacturer, double operatingVoltage, double ticksPerRev){
        this.name = name;
        this.manufacturer = manufacturer;
        this.operatingVoltage = operatingVoltage;
        this.ticksPerRev = ticksPerRev;
    }
    public EncoderTypeInstance(EncoderTypeInstance encoderTypeInstance){
        this.name = encoderTypeInstance.name;
        this.manufacturer = encoderTypeInstance.manufacturer;
        this.operatingVoltage = encoderTypeInstance.operatingVoltage;
        this.ticksPerRev = encoderTypeInstance.ticksPerRev;
    }
    public EncoderTypeInstance(Class<?> encoderTypeClass) throws IllegalArgumentException{
        EncoderType encoderType = encoderTypeClass.getAnnotation(EncoderType.class);
        if(encoderType == null){
            throw new IllegalArgumentException("Parameter encoderTypeClass must be an instance of EncoderType Annotation Class!");
        }
        this.name = encoderType.name();
        this.manufacturer = encoderType.manufacturer();
        this.operatingVoltage = encoderType.operatingVoltage();
        this.ticksPerRev = encoderType.ticksPerRev();
    }
    public static EncoderTypeInstance getInstance(Class<?> encoderTypeClass) throws IllegalArgumentException{
        return new EncoderTypeInstance(encoderTypeClass);
    }
    public String getEncoderName(){
        return this.name;
    }
    public void setEncoderName(String name){
        this.name = name;
    }
    public String getManufacturer(){
        return this.manufacturer;
    }
    public void setManufacturer(String manufacturer){
        this.manufacturer = manufacturer;
    }
    public double getOperatingVoltage(){
        return this.operatingVoltage;
    }
    public void setOperatingVoltage(double operatingVoltage){
        this.operatingVoltage = operatingVoltage;
    }
    public double getTicksPerRev(){
        return this.ticksPerRev;
    }
    public void setTicksPerRev(double ticksPerRev){
        this.ticksPerRev = ticksPerRev;
    }
}
