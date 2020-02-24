package org.darbots.corebotlib.localization;

import java.awt.*;
import java.io.Serializable;
import java.util.Comparator;

public class Point2D implements Serializable,Comparable<Point2D>,Cloneable {
    public static final long serialVersionUID = 0L;
    public double X = 0;
    public double Y = 0;
    public Point2D(){
        this(0,0);
    }
    public Point2D(double X, double Y){
        this.X = X;
        this.Y = Y;
    }
    public Point2D(Point2D point){
        this.X = point.X;
        this.Y = point.Y;
    }
    public double hypot(){
        return Math.hypot(this.X,this.Y);
    }
    public double distTo(Point2D point){
        return Math.hypot(this.X - point.X,this.Y - point.Y);
    }
    public Point2D normalize(){
        double hypot = this.hypot();
        return new Point2D(this.X / hypot, this.Y / hypot);
    }
    public Point2D clone(){
        return new Point2D(this);
    }
    @Override
    public int compareTo(Point2D o2) {
        double o1Hypot = this.hypot();
        double o2Hypot = o2.hypot();
        if(o1Hypot < o2Hypot){
            return -1;
        }else if(o1Hypot > o2Hypot){
            return 1;
        }else{
            return 0;
        }
    }
    public static class XComparator implements Comparator<Point2D>{
        @Override
        public int compare(Point2D o1, Point2D o2) {
            if(o1.X < o2.X){
                return -1;
            }else if(o1.X > o2.X){
                return 1;
            }else{
                return 0;
            }
        }
    }
    public static class YComparator implements Comparator<Point2D>{
        @Override
        public int compare(Point2D o1, Point2D o2) {
            if(o1.Y < o2.Y){
                return -1;
            }else if(o1.Y > o2.Y){
                return 1;
            }else{
                return 0;
            }
        }
    }
}
