package org.darbots.corebotlib.calculations.valueholders;

import org.darbots.corebotlib.calculations.units.AngleUnit;
import org.darbots.corebotlib.calculations.units.DistanceUnit;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;

public class Distance implements Serializable,Cloneable {
    public static final long serialVersionUID = 0L;
    public double distance = 0;
    private DistanceUnit distanceUnit;
    public Distance(){
        this(0,DistanceUnit.CM);
    }
    public Distance(double distance, DistanceUnit unit){
        this.distance = distance;
        this.distanceUnit = unit;
    }
    public Distance(Distance dist){
        this.distance = dist.distance;
        this.distanceUnit = dist.distanceUnit;
    }
    public DistanceUnit getDistanceUnit(){
        if(this.distanceUnit == null){
            return DistanceUnit.CM;
        }else{
            return this.distanceUnit;
        }
    }
    public void setDistanceUnit(DistanceUnit unit){
        this.distanceUnit = unit;
    }
    public Distance clone(){
        return new Distance(this);
    }
    public double asUnit(DistanceUnit distanceUnit){
        return distanceUnit.fromDistanceUnit(this.getDistanceUnit(),this.distance);
    }
    public double asMM(){
        return this.asUnit(DistanceUnit.MM);
    }
    public double asCM(){
        return this.asUnit(DistanceUnit.CM);
    }
    public double asM(){
        return this.asUnit(DistanceUnit.M);
    }
    public double asINCH(){
        return this.asUnit(DistanceUnit.INCH);
    }
    public double asFeet(){
        return this.asUnit(DistanceUnit.FEET);
    }
    public boolean equals(Distance distance){
        return this.asCM() == distance.asCM();
    }
    private void writeObject(java.io.ObjectOutputStream out)
            throws IOException{
        DistanceUnit unit = this.getDistanceUnit();
        double distCM = unit.toDistanceUnit(DistanceUnit.CM,this.distance);
        out.writeDouble(distCM);
    }
    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException{
        double distCM = in.readDouble();
        this.distanceUnit = DistanceUnit.CM;
        this.distance = distCM;
    }
    private void readObjectNoData()
            throws ObjectStreamException {
        this.distance = 0;
        this.distanceUnit = DistanceUnit.CM;
    }
}
