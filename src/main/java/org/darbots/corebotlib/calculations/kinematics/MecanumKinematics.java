package org.darbots.corebotlib.calculations.kinematics;

import org.darbots.corebotlib.calculations.geometry.Vector2D;
import org.darbots.corebotlib.calculations.units.AngleUnit;
import org.darbots.corebotlib.calculations.valueholders.Angle;

import java.util.List;

public class MecanumKinematics {
    public static class MecanumWheelSpeeds{
        public double LF, RF, LB, RB;
        public MecanumWheelSpeeds(double LF, double RF, double LB, double RB){
            this.LF = LF;
            this.LB = LB;
            this.RF = RF;
            this.RB = RB;
        }
        public MecanumWheelSpeeds(MecanumWheelSpeeds wheelSpeeds){
            this.LF = wheelSpeeds.LF;
            this.LB = wheelSpeeds.LB;
            this.RF = wheelSpeeds.RF;
            this.RB = wheelSpeeds.RB;
        }
    }
    /**
     * Calculate the speed of each wheel, given the supposed robot speed.
     * @param robotSpeed robotSpeed Vector
     * @param turnDist distance to turn the robot, usually turnDist = (Chassis Width + Chassis Height) / 2.0
     * @param wheelRadius Radius of the Mecanum Wheel, measure from the center of the wheel to the very tip of the roller that is touching the ground
     * @return Angular Speed of each wheel, in rad/s, positive speed to rotate in counter-clockwise direction
     */
    public static MecanumWheelSpeeds calculateWheelAngularSpeeds(Vector2D robotSpeed, double turnDist, double wheelRadius){
        double xComponent = robotSpeed.X / wheelRadius; //(1.0/wheelRadius) * robotSpeed.X;
        double yComponent = robotSpeed.Y / wheelRadius; //(1.0/wheelRadius) * robotSpeed.Y;
        double rotComponent = (turnDist / wheelRadius) * robotSpeed.headingAngle.asRadian();
        return new MecanumWheelSpeeds(
                -xComponent + yComponent + rotComponent,
                xComponent + yComponent + rotComponent,
                -xComponent - yComponent + rotComponent,
                xComponent - yComponent + rotComponent
        );
    }

    /**
     * Calculate the speed of the robot, given the speed of each wheel
     * @param wheelSpeeds speed of each wheel, in rad/s
     * @param turnDist distance to turn the robot, usually turnDist = (Chassis Width + Chassis Height) / 2.0
     * @param wheelRadius Radius of the Mecanum Wheel
     * @return Robot Speed, angular speed in rad/s
     */
    public static Vector2D calculateRobotSpeed(MecanumWheelSpeeds wheelSpeeds, double turnDist, double wheelRadius){
        return new Vector2D(
                (wheelRadius / 4.0) * (-wheelSpeeds.LF + wheelSpeeds.RF - wheelSpeeds.LB + wheelSpeeds.RB),
                (wheelRadius / 4.0) * (wheelSpeeds.LF + wheelSpeeds.RF - wheelSpeeds.LB - wheelSpeeds.RB),
                new Angle((wheelRadius / (4.0 * turnDist)) * (wheelSpeeds.LF + wheelSpeeds.RF + wheelSpeeds.LB + wheelSpeeds.RB), AngleUnit.RADIAN)
        );
    }
}
