package org.darbots.corebotlib.calculations.units;

public enum AngleUnit {
    DEGREE(360),
    RADIAN(2 * Math.PI);

    private double range;
    private double halfRange;
    private AngleUnit(double range){
        this.range = Math.abs(range);
        this.halfRange = this.range / 2.0;
    }
    public double fromAngleUnit(AngleUnit fromAngleUnit, double fromAngle){
        if(fromAngleUnit == this){
            return fromAngle;
        }
        return fromAngle / fromAngleUnit.halfRange * this.halfRange;
    }
    public double toAngleUnit(AngleUnit toAngleUnit, double fromAngle){
        return toAngleUnit.fromAngleUnit(this,fromAngle);
    }
    public double normalize(double angle){
        double tempAng = angle % range;
        if(tempAng >= halfRange){
            tempAng -= range;
        }else if(tempAng < -halfRange){
            tempAng += range;
        }
        return tempAng;
    }
    public float normalize(float angle){
        return ((float) normalize((double) angle));
    }
}
