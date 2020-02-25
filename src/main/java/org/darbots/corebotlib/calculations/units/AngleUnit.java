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
    public double toSquareAngle(double normalizedAngle){
        double quarterRange = this.halfRange / 2.0;
        double eighthRange = quarterRange / 2.0;
        if(normalizedAngle <= (-halfRange + eighthRange) || normalizedAngle > (halfRange - eighthRange)){
            return -halfRange;
        }else if(normalizedAngle <= (halfRange - eighthRange) && normalizedAngle > (eighthRange)){
            return quarterRange;
        }else if(normalizedAngle <= (eighthRange) && normalizedAngle > (-eighthRange)){
            return 0;
        }else{ //normalizedAngle <= (-eighthRange) && normalizedAngle > (-halfRange + eighthRange)
            return -quarterRange;
        }
    }
}
