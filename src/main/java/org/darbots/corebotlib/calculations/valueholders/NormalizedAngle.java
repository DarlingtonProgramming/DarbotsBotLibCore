package org.darbots.corebotlib.calculations.valueholders;

import org.darbots.corebotlib.calculations.units.AngleUnit;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;

public class NormalizedAngle extends Angle {
    public NormalizedAngle(){
        super();
    }
    public NormalizedAngle(double angle, AngleUnit unit){
        this.angleUnit = unit;
        this.setAngle(angle);
    }
    public NormalizedAngle(Angle angle){
        this.angleUnit = angle.angleUnit;
        this.setAngle(angle.angle);
    }
    public NormalizedAngle(NormalizedAngle angle){
        this.angle = angle.angle;
        this.angleUnit = angle.angleUnit;
    }

    public void setAngle(double angle){
        AngleUnit unit = this.angleUnit == null ? AngleUnit.RADIAN : this.angleUnit;
        this.angle = unit.normalize(angle);
    }
    public NormalizedAngle negative(){
        return new NormalizedAngle(-this.angle,this.getAngleUnit());
    }
    public NormalizedAngle toRadian(){
        return new NormalizedAngle(this.asRadian(),AngleUnit.RADIAN);
    }
    public NormalizedAngle toDegree(){
        return new NormalizedAngle(this.asDegree(),AngleUnit.DEGREE);
    }
    public NormalizedAngle plus(NormalizedAngle ang){
        AngleUnit angleUnit = this.getAngleUnit();
        return new NormalizedAngle(
                this.angle + ang.asUnit(angleUnit),
                angleUnit
        );
    }
    public NormalizedAngle minus(NormalizedAngle ang){
        AngleUnit angleUnit = this.getAngleUnit();
        return new NormalizedAngle(
                this.angle - ang.asUnit(angleUnit),
                angleUnit
        );
    }
    public NormalizedAngle times(NormalizedAngle ang){
        AngleUnit angleUnit = this.getAngleUnit();
        return new NormalizedAngle(
                this.angle * ang.asUnit(angleUnit),
                angleUnit
        );
    }
    public NormalizedAngle div(NormalizedAngle ang){
        AngleUnit angleUnit = this.getAngleUnit();
        return new NormalizedAngle(
                this.angle / ang.asUnit(angleUnit),
                angleUnit
        );
    }
    public NormalizedAngle rem(NormalizedAngle ang){
        AngleUnit angleUnit = this.getAngleUnit();
        return new NormalizedAngle(
                this.angle % ang.asUnit(angleUnit),
                angleUnit
        );
    }
}
