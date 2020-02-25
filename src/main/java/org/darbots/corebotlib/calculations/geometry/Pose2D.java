package org.darbots.corebotlib.calculations.geometry;

import org.darbots.corebotlib.calculations.valueholders.NormalizedAngle;

import java.io.Serializable;

public class Pose2D extends Point2D implements Serializable,Cloneable {
    public static final long serialVersionUID = 0L;
    public NormalizedAngle headingNormalizedAngle;
    public Pose2D(){
        this(0,0,new NormalizedAngle());
    }
    public Pose2D(double X, double Y, NormalizedAngle headingNormalizedAngle){
        super(X,Y);
        this.headingNormalizedAngle = headingNormalizedAngle;
    }
    public Pose2D(Point2D point, NormalizedAngle headingNormalizedAngle){
        super(point);
        this.headingNormalizedAngle = headingNormalizedAngle;
    }
    public Pose2D(Pose2D pose2D){
        super(pose2D);
        this.headingNormalizedAngle = new NormalizedAngle(pose2D.headingNormalizedAngle);
    }
    public Pose2D(Vector2D vec2D){
        this(vec2D,new NormalizedAngle(vec2D.headingAngle));
    }
    public Pose2D clone(){
        return new Pose2D(this);
    }
    public Vector2D toVector2D(){
        return new Vector2D(this);
    }
}
