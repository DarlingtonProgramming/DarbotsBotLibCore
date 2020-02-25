package org.darbots.corebotlib.calculations;

import org.darbots.corebotlib.calculations.units.AngleUnit;
import org.darbots.corebotlib.calculations.valueholders.Angle;
import org.darbots.corebotlib.calculations.valueholders.NormalizedAngle;
import org.darbots.corebotlib.calculations.geometry.Point2D;
import org.darbots.corebotlib.calculations.geometry.Pose2D;
import org.darbots.corebotlib.calculations.geometry.Vector2D;

public final class BotMath {
    public static final double INFINITELY_SMALL = 1e-8;
    public static final Point2D ORIGIN_POINT = new Point2D(0,0);
    public static int map(int number, int originalMin, int originalMax, int newMin, int newMax){
        int oldDelta = originalMax - originalMin;
        int newDelta = newMax - newMin;
        float startDelta = (number - originalMin);
        int scaled = newMin + Math.round(startDelta / oldDelta * newDelta);
        return scaled;
    }
    public static float map(float number, float originalMin, float originalMax, float newMin, float newMax){
        return newMin + ((number-originalMin) / (originalMax - originalMin) * (newMax - newMin));
    }
    public static double map(double number, double originalMin, double originalMax, double newMin, double newMax){
        return newMin + ((number-originalMin) / (originalMax - originalMin) * (newMax - newMin));
    }
    public static Point2D rotatePointAroundFixedPoint(Point2D fixedPoint, Point2D rotatePoint, Angle counterClockwiseAngle){
        double angleRad = counterClockwiseAngle.asRadian();
        double cosAng = Math.cos(angleRad), sinAng = Math.sin(angleRad);
        double[][] ccMatrix = {
                {cosAng,-sinAng},
                {sinAng,cosAng}
        };
        double relativeX = rotatePoint.X - fixedPoint.X, relativeY = rotatePoint.Y - fixedPoint.Y;
        double rotatedRelativeX = ccMatrix[0][0] * relativeX + ccMatrix[0][1] * relativeY;
        double rotatedRelativeY = ccMatrix[1][0] * relativeX + ccMatrix[1][1] * relativeY;
        return new Point2D(rotatedRelativeX + fixedPoint.X,rotatedRelativeY + fixedPoint.Y);
    }
    public static Point2D getRelativePosition(Pose2D PerspectiveOrigin, Point2D Target){
        //First step - move the Perspective Origin to the Origin of the Axis.
        Point2D targetPointOffset = new Point2D(Target.X - PerspectiveOrigin.X,Target.Y - PerspectiveOrigin.Y);
        //Second step - rotate the targetPoint so that the coordinate system (X and Z scalars) of the Perspective Origin overlaps with the Field Coordinate.
        //We are basically rotating field coordinate here.
        Point2D rotatedTargetPoint = rotatePointAroundFixedPoint(ORIGIN_POINT,targetPointOffset,PerspectiveOrigin.headingNormalizedAngle.negative());

        return rotatedTargetPoint;
    }

    public static Point2D getAbsolutePosition(Pose2D PerspectiveOrigin, Point2D RelativePosition){
        //First Step - rotate the coordinates back.
        double[] origin = {0,0};
        Point2D rotatedTargetPoint = rotatePointAroundFixedPoint(ORIGIN_POINT,RelativePosition,PerspectiveOrigin.headingNormalizedAngle);
        //Second Step - move the PerspectiveOrigin back to the Absolute Point on the Field.
        return new Point2D(rotatedTargetPoint.X + PerspectiveOrigin.X,rotatedTargetPoint.Y + PerspectiveOrigin.Y);
    }

    public static Pose2D getRelativePosition(Pose2D PerspectiveOrigin, Pose2D Target){
        //First step - move the Perspective Origin to the Origin of the Axis.
        Point2D targetPoint = new Point2D(Target.X - PerspectiveOrigin.X,Target.Y - PerspectiveOrigin.Y);
        //Second step - rotate the targetPoint so that the coordinate system (X and Z scalars) of the Perspective Origin overlaps with the Field Coordinate.
        //We are basically rotating field coordinate here.
        Point2D rotatedTargetPoint = rotatePointAroundFixedPoint(ORIGIN_POINT,targetPoint,PerspectiveOrigin.headingNormalizedAngle.negative());
        //Third step - calculate relative delta Rotation Y;
        double deltaRotZ = Target.headingNormalizedAngle.asRadian() - PerspectiveOrigin.headingNormalizedAngle.asRadian();

        return new Pose2D(rotatedTargetPoint,new NormalizedAngle(deltaRotZ,AngleUnit.RADIAN));
    }

    public static Pose2D getAbsolutePosition(Pose2D PerspectiveOrigin, Pose2D RelativePosition){
        //First Step - calculate absolute Rotation Y.
        double absRotZ = RelativePosition.headingNormalizedAngle.asRadian() + PerspectiveOrigin.headingNormalizedAngle.asRadian();
        //Second Step - rotate the coordinates back.
        Point2D rotatedTargetPoint = rotatePointAroundFixedPoint(ORIGIN_POINT,RelativePosition,PerspectiveOrigin.headingNormalizedAngle);
        //Third Step - move the PerspectiveOrigin back to the Absolute Point on the Field.
        return new Pose2D(rotatedTargetPoint.X + PerspectiveOrigin.X,rotatedTargetPoint.Y + PerspectiveOrigin.Y,new NormalizedAngle(absRotZ,AngleUnit.RADIAN));
    }

    public static Pose2D getAbsolutePosition(NormalizedAngle robotRotationOnField, Point2D relativeObjectOnBot, Point2D relativeObjectOnField){
        Point2D relativeObjectOnBot_RotatedToField = rotatePointAroundFixedPoint(ORIGIN_POINT,relativeObjectOnBot,robotRotationOnField);
        return new Pose2D(relativeObjectOnField.X - relativeObjectOnBot_RotatedToField.X, relativeObjectOnField.Y - relativeObjectOnBot_RotatedToField.Y, robotRotationOnField);
    }

    public static Vector2D getRelativePosition(Vector2D PerspectiveOrigin, Vector2D Target){
        //First step - move the Perspective Origin to the Origin of the Axis.
        Point2D targetPoint = new Point2D(Target.X - PerspectiveOrigin.X,Target.Y - PerspectiveOrigin.Y);
        //Second step - rotate the targetPoint so that the coordinate system (X and Z scalars) of the Perspective Origin overlaps with the Field Coordinate.
        //We are basically rotating field coordinate here.
        Point2D rotatedTargetPoint = rotatePointAroundFixedPoint(ORIGIN_POINT,targetPoint,PerspectiveOrigin.headingAngle.negative());
        //Third step - calculate relative delta Rotation Y;
        double deltaRotZ = Target.headingAngle.asRadian() - PerspectiveOrigin.headingAngle.asRadian();

        return new Vector2D(rotatedTargetPoint,new Angle(deltaRotZ,AngleUnit.RADIAN));
    }

    public static Vector2D getAbsolutePosition(Vector2D PerspectiveOrigin, Vector2D RelativePosition){
        //First Step - calculate absolute Rotation Y.
        double absRotZ = RelativePosition.headingAngle.asRadian() + PerspectiveOrigin.headingAngle.asRadian();
        //Second Step - rotate the coordinates back.
        Point2D rotatedTargetPoint = rotatePointAroundFixedPoint(ORIGIN_POINT,RelativePosition,PerspectiveOrigin.headingAngle);
        //Third Step - move the PerspectiveOrigin back to the Absolute Point on the Field.
        return new Vector2D(rotatedTargetPoint.X + PerspectiveOrigin.X,rotatedTargetPoint.Y + PerspectiveOrigin.Y,new Angle(absRotZ,AngleUnit.RADIAN));
    }
}
