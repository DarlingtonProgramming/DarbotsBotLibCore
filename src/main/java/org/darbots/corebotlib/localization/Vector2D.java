package org.darbots.corebotlib.localization;

import org.darbots.corebotlib.calculations.valueholders.Angle;
import org.darbots.corebotlib.calculations.valueholders.NormalizedAngle;

import java.io.Serializable;

public class Vector2D extends Point2D implements Serializable,Cloneable {
    public static final long serialVersionUID = 0L;
    public Angle headingAngle;
    public Vector2D(){
        this(0,0,new NormalizedAngle());
    }
    public Vector2D(double X, double Y, Angle headingAngle){
        super(X,Y);
        this.headingAngle = headingAngle;
    }
    public Vector2D(Point2D point, Angle headingAngle){
        super(point);
        this.headingAngle = headingAngle;
    }
    public Vector2D(Vector2D vec2D){
        super(vec2D);
        this.headingAngle = new Angle(vec2D.headingAngle);
    }
    public Vector2D(Pose2D pose2D){
        this(pose2D,new Angle(pose2D.headingNormalizedAngle));
    }
    public Pose2D toPose2D(){
        return new Pose2D(this);
    }
    public Vector2D clone(){
        return new Vector2D(this);
    }
}
