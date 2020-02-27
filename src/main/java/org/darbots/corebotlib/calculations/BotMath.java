package org.darbots.corebotlib.calculations;

import org.darbots.corebotlib.calculations.units.AngleUnit;
import org.darbots.corebotlib.calculations.valueholders.Angle;
import org.darbots.corebotlib.calculations.valueholders.NormalizedAngle;
import org.darbots.corebotlib.calculations.geometry.Point2D;
import org.darbots.corebotlib.calculations.geometry.Pose2D;
import org.darbots.corebotlib.calculations.geometry.Vector2D;
import java.util.ArrayList;
import java.util.List;

public final class BotMath {
    public static final double INFINITELY_SMALL = 1e-8;
    public static final Point2D ORIGIN_POINT = new Point2D(0,0);

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

    public static boolean isPointAtCircleEdge(Point2D circleCenter, double circleRadius, Point2D point){
        if(point.distTo(circleCenter) <= INFINITELY_SMALL){
            return true;
        }else{
            return false;
        }
    }

    public static ArrayList<Point2D> verticalLineCircleIntersections(Point2D circleCenter, double circleRadius, double fixedX){
        double x1 = fixedX - circleCenter.X;
        double ySquared = Math.pow(circleRadius,2) - Math.pow(x1,2);
        ArrayList<Point2D> allPoints = new ArrayList<Point2D>();
        if(ySquared < 0){
            return allPoints;
        }
        if(ySquared == 0){
            Point2D root = new Point2D(fixedX,circleCenter.Y);
            allPoints.add(root);
        }else{
            double sqrtYSquared = Math.sqrt(ySquared);
            Point2D root1 = new Point2D(fixedX,sqrtYSquared + circleCenter.Y);
            allPoints.add(root1);
            Point2D root2 = new Point2D(fixedX,-sqrtYSquared + circleCenter.Y);
            allPoints.add(root2);
        }
        return allPoints;
    }

    public static ArrayList<Point2D> horizontalLineCircleIntersections(Point2D circleCenter, double circleRadius, double fixedY){
        double y1 = fixedY - circleCenter.Y;
        double xSquared = Math.pow(circleRadius,2) - Math.pow(y1,2);
        ArrayList<Point2D> allPoints = new ArrayList<Point2D>();
        if(xSquared < 0){
            return allPoints;
        }
        if(xSquared == 0){
            Point2D root = new Point2D(circleCenter.X,fixedY);
            allPoints.add(root);
        }else{
            double sqrtXSquared = Math.sqrt(xSquared);
            Point2D root1 = new Point2D(sqrtXSquared + circleCenter.X,fixedY);
            allPoints.add(root1);
            Point2D root2 = new Point2D(-sqrtXSquared + circleCenter.X,fixedY);
            allPoints.add(root2);
        }
        return allPoints;
    }

    public static ArrayList<Point2D> lineSegmentCircleIntersections(Point2D circleCenter, double circleRadius, Point2D linePoint1, Point2D linePoint2){
        Point2D boundingBoxMin = new Point2D(
                Math.min(linePoint1.X,linePoint2.X),
                Math.min(linePoint1.Y,linePoint2.Y)
        );
        Point2D boundingBoxMax = new Point2D(
                Math.max(linePoint1.X,linePoint2.X),
                Math.max(linePoint1.Y,linePoint2.Y)
        );

        ArrayList<Point2D> lineCircleIntersections = lineCircleIntersections(circleCenter,circleRadius,linePoint1,linePoint2);
        ArrayList<Point2D> lineSegmentCircleIntersections = new ArrayList<>();
        for(Point2D intersect : lineCircleIntersections){
            if(isInBoundingBox(intersect,boundingBoxMin,boundingBoxMax)){
                lineSegmentCircleIntersections.add(intersect);
            }
        }
        return lineSegmentCircleIntersections;
    }

    public static ArrayList<Point2D> lineCircleIntersections(Point2D circleCenter, double circleRadius, Point2D linePoint1, Point2D linePoint2){
        if(linePoint1.X == linePoint2.X || linePoint1.Y == linePoint2.Y){
            ArrayList<Point2D> resultArray = null;
            if(linePoint1.X == linePoint2.X && linePoint1.Y == linePoint2.Y){
                resultArray = new ArrayList<Point2D>();
                if(isPointAtCircleEdge(circleCenter,circleRadius,linePoint1)){
                    resultArray.add(linePoint1);
                }
            }else if(linePoint1.X == linePoint2.X){
                resultArray = verticalLineCircleIntersections(circleCenter,circleRadius,linePoint1.X);
            }else { //linePoint1.Y == linePoint2.Y
                resultArray = horizontalLineCircleIntersections(circleCenter,circleRadius,linePoint1.Y);
            }
            return resultArray;
        }

        double x1 = linePoint1.X - circleCenter.X;
        double y1 = linePoint1.Y - circleCenter.Y;

        double m1 = (linePoint2.Y - linePoint1.Y) / (linePoint2.X - linePoint1.X);
        double quadraticA = 1.0 + Math.pow(m1,2);
        double quadraticB = (2.0 * m1 * y1) - (2.0 * Math.pow(m1,2) * x1);
        double quadraticC = ((Math.pow(m1,2) * Math.pow(x1,2))) - (2.0 * y1 * m1 * x1) + Math.pow(y1,2) - Math.pow(circleRadius,2);
        double quadraticDelta = Math.pow(quadraticB,2) - (4.0 * quadraticA * quadraticC);

        ArrayList<Point2D> allPoints = new ArrayList<Point2D>();
        if(quadraticDelta > 0) {
            double xRoot1 = (-quadraticB + Math.sqrt(quadraticDelta)) / (2.0 * quadraticA);
            double yRoot1 = m1 * (xRoot1 - x1) + y1;
            xRoot1 += circleCenter.X;
            yRoot1 += circleCenter.Y;
            Point2D root1 = new Point2D(xRoot1, yRoot1);
            allPoints.add(root1);

            double xRoot2 = (-quadraticB - Math.sqrt(quadraticDelta)) / (2.0 * quadraticA);
            double yRoot2 = m1 * (xRoot2 - x1) + y1;
            xRoot2 += circleCenter.X;
            yRoot2 += circleCenter.Y;
            Point2D root2 = new Point2D(xRoot2,yRoot2);
            allPoints.add(root2);
        }else if(quadraticDelta == 0){
            double xRoot1 = (-quadraticB) / (2.0 * quadraticA);
            double yRoot1 = m1 * (xRoot1 - x1) + y1;
            xRoot1 += circleCenter.X;
            yRoot1 += circleCenter.Y;
            Point2D root1 = new Point2D(xRoot1, yRoot1);
            allPoints.add(root1);
        }
        return allPoints;
    }

    public static Point2D lineCircleIntersection_CLOSEST_TO_POINT_2(Point2D circleCenter, double circleRadius, Point2D linePoint1, Point2D linePoint2){
        ArrayList<Point2D> allIntersections = lineCircleIntersections(circleCenter,circleRadius,linePoint1,linePoint2);
        int listSize = allIntersections.size();
        if(listSize >= 2){
            if (allIntersections.get(0).distTo(linePoint2) > allIntersections.get(1).distTo(linePoint2)) {
                return allIntersections.get(1);
            }else{
                return allIntersections.get(0);
            }
        }else if(listSize > 0){
            return allIntersections.get(0);
        }else{
            return null;
        }
    }

    public static Point2D getNearestPoint(List<Point2D> allPoints, Point2D point){
        double smallestDistance = -1;
        Point2D smallestPoint = null;
        for(Point2D thisPoint : allPoints){
            double currentDistance = thisPoint.distTo(point);
            if(smallestPoint == null || currentDistance < smallestDistance){
                smallestPoint = thisPoint;
                smallestDistance = currentDistance;
            }
        }
        return smallestPoint;
    }

    public static boolean isInBoundingBox(Point2D point, Point2D minPoint, Point2D maxPoint){
        if(point.X >= minPoint.X && point.X <= maxPoint.X && point.Y >= minPoint.Y && point.Y <= maxPoint.Y){
            return true;
        }else{
            return false;
        }
    }

    public static Point2D nearestPointOnLineSegment(Point2D point, Point2D linePoint1, Point2D linePoint2){
        Point2D pointResult = nearestPointOnLine(point,linePoint1,linePoint2);
        Point2D boundingBoxMin = new Point2D(
                Math.min(linePoint1.X,linePoint2.X),
                Math.min(linePoint1.Y,linePoint2.Y)
        );
        Point2D boundingBoxMax = new Point2D(
                Math.max(linePoint1.X,linePoint2.X),
                Math.max(linePoint1.Y,linePoint2.Y)
        );
        if(isInBoundingBox(pointResult,boundingBoxMin,boundingBoxMax)){
            return pointResult;
        }else{
            return null;
        }
    }

    public static Point2D nearestPointOnLine(Point2D point, Point2D linePoint1, Point2D linePoint2){
        if(linePoint1.X == linePoint2.X && linePoint1.Y == linePoint2.Y){
            return linePoint1;
        }
        Point2D pointResult;
        if(linePoint1.Y == linePoint2.Y){
            pointResult = new Point2D(point.X,linePoint1.Y);
        }else if(linePoint1.X == linePoint2.X){
            pointResult = new Point2D(linePoint1.X,point.Y);
        }else{
            double m = (linePoint2.Y - linePoint1.Y) / (linePoint2.X - linePoint1.X);
            double b = linePoint1.Y - linePoint1.X * m;
            double n = -1.0 / m;
            double c = point.Y - point.X * n;
            double nearestPointX = (c-b) / (m-n);
            pointResult = new Point2D(nearestPointX,m * nearestPointX + b);
        }
        return pointResult;
    }
}
