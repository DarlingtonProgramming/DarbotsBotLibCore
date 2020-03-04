package org.darbots.corebotlib.calculations.valueholders;

import org.darbots.corebotlib.calculations.units.AngleUnit;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;

public class Angle implements Serializable,Cloneable {
    public static final long serialVersionUID = 0L;
    protected double angle = 0;
    protected AngleUnit angleUnit;
    public Angle(){
        this(0,AngleUnit.RADIAN);
    }
    public Angle(double angle, AngleUnit unit){
        this.angle = angle;
        this.angleUnit = unit;
    }
    public Angle(Angle angle){
        this.angle = angle.angle;
        this.angleUnit = angle.angleUnit;
    }
    public double getAngle(){
        return this.angle;
    }
    public void setAngle(double angle){
        this.angle = angle;
    }
    public AngleUnit getAngleUnit(){
        if(this.angleUnit == null){
            return AngleUnit.RADIAN;
        }else{
            return this.angleUnit;
        }
    }
    public void setAngleUnit(AngleUnit unit){
        this.angleUnit = unit;
    }
    public Angle clone(){
        return new Angle(this);
    }
    public Angle negative(){
        return new Angle(-this.angle,this.getAngleUnit());
    }
    public double asRadian(){
        return this.asUnit(AngleUnit.RADIAN);
    }
    public double asDegree(){
        return this.asUnit(AngleUnit.DEGREE);
    }
    public double asUnit(AngleUnit targetUnit){
        return this.getAngleUnit().toAngleUnit(targetUnit,this.getAngle());
    }
    public Angle toRadian(){
        return new Angle(this.asRadian(),AngleUnit.RADIAN);
    }
    public Angle toDegree(){
        return new Angle(this.asDegree(),AngleUnit.DEGREE);
    }
    public Angle plus(Angle ang){
        AngleUnit angleUnit = this.getAngleUnit();
        return new Angle(
                this.angle + ang.asUnit(angleUnit),
                angleUnit
        );
    }
    public Angle minus(Angle ang){
        AngleUnit angleUnit = this.getAngleUnit();
        return new Angle(
                this.angle - ang.asUnit(angleUnit),
                angleUnit
        );
    }
    public Angle times(Angle ang){
        AngleUnit angleUnit = this.getAngleUnit();
        return new Angle(
                this.angle * ang.asUnit(angleUnit),
                angleUnit
        );
    }
    Angle div(Angle ang){
        AngleUnit angleUnit = this.getAngleUnit();
        return new Angle(
                this.angle / ang.asUnit(angleUnit),
                angleUnit
        );
    }
    public Angle rem(Angle ang){
        AngleUnit angleUnit = this.getAngleUnit();
        return new Angle(
                this.angle % ang.asUnit(angleUnit),
                angleUnit
        );
    }
    public Angle abs(){
        return new Angle(
                Math.abs(this.angle),
                this.getAngleUnit()
        );
    }
    public boolean equals(Angle angle){
        return this.asRadian() == angle.asRadian();
    }
    public NormalizedAngle getSquaredAngle(){
        AngleUnit currentAngleUnit = this.getAngleUnit();
        return new NormalizedAngle(currentAngleUnit.toSquareAngle(currentAngleUnit.normalize(this.angle)),currentAngleUnit);
    }
    private void writeObject(java.io.ObjectOutputStream out)
            throws IOException {
        AngleUnit unit = this.getAngleUnit();
        double angleRad = unit.toAngleUnit(AngleUnit.RADIAN,this.angle);
        out.writeDouble(angleRad);
    }
    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException{
        double angleRad = in.readDouble();
        this.angleUnit = AngleUnit.RADIAN;
        this.angle = angleRad;
    }
    private void readObjectNoData()
            throws ObjectStreamException {
        this.angle = 0;
        this.angleUnit = AngleUnit.RADIAN;
    }
}
