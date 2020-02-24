package org.darbots.corebotlib.calculations.units;

public enum DistanceUnit {
    MM(1.0 / 0.1),
    CM(1.0),
    M(1.0 / 100.0),
    INCH(1.0 / 2.54),
    FEET(1.0 / (2.54 * 12));

    private double convertFactor;
    private DistanceUnit(double convertFactor){
        this.convertFactor = convertFactor;
    }
    public double fromDistanceUnit(DistanceUnit fromDistanceUnit, double fromDistance){
        if(fromDistanceUnit == this){
            return fromDistance;
        }
        return fromDistance / fromDistanceUnit.convertFactor * this.convertFactor;
    }
    public double toDistanceUnit(DistanceUnit toDistanceUnit, double fromDistance){
        return toDistanceUnit.fromDistanceUnit(this,fromDistance);
    }
}
