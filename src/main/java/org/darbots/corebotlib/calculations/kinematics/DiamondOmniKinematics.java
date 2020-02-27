package org.darbots.corebotlib.calculations.kinematics;

import org.darbots.corebotlib.calculations.geometry.Vector2D;
import org.darbots.corebotlib.calculations.units.AngleUnit;
import org.darbots.corebotlib.calculations.valueholders.Angle;

public class DiamondOmniKinematics {
    public static class DiamondOmniWheelSpeeds{
        public double LF, RF, LB, RB;
        public DiamondOmniWheelSpeeds(double LF, double RF, double LB, double RB){
            this.LF = LF;
            this.LB = LB;
            this.RF = RF;
            this.RB = RB;
        }
        public DiamondOmniWheelSpeeds(MecanumKinematics.MecanumWheelSpeeds wheelSpeeds){
            this.LF = wheelSpeeds.LF;
            this.LB = wheelSpeeds.LB;
            this.RF = wheelSpeeds.RF;
            this.RB = wheelSpeeds.RB;
        }
    }
    /**
     * Calculate the speed of each wheel, given the supposed robot speed.
     * @param robotSpeed robotSpeed Vector
     * @param turnDist distance from wheel center to robotCenter
     * @param wheelRadius Radius of the Omni Wheel, measure from the center of the wheel to the very tip of the roller that is touching the ground
     * @return Angular Speed of each wheel, in rad/s, positive speed to rotate in counter-clockwise direction
     */
    public static DiamondOmniWheelSpeeds calculateWheelAngularSpeeds(Vector2D robotSpeed, double turnDist, double wheelRadius){
        double xComponent = robotSpeed.X / (wheelRadius * Math.sqrt(2.0));
        double yComponent = robotSpeed.Y / (wheelRadius * Math.sqrt(2.0));
        double rotComponent = (turnDist / wheelRadius) * robotSpeed.headingAngle.asRadian();
        return new DiamondOmniWheelSpeeds(
                -xComponent + yComponent + rotComponent,
                xComponent + yComponent + rotComponent,
                -xComponent - yComponent + rotComponent,
                xComponent - yComponent + rotComponent
        );
    }

    /**
     * Calculate the speed of the robot, given the speed of each wheel
     * @param wheelSpeeds speed of each wheel, in rad/s
     * @param turnDist distance from wheel center to robotCenter
     * @param wheelRadius Radius of the Omni Wheel
     * @return Robot Speed, angular speed in rad/s
     */
    public static Vector2D calculateRobotSpeed(DiamondOmniWheelSpeeds wheelSpeeds, double turnDist, double wheelRadius){
        return new Vector2D(
                ((Math.sqrt(2.0) * wheelRadius) / 4.0) * (-wheelSpeeds.LF + wheelSpeeds.RF - wheelSpeeds.LB + wheelSpeeds.RB),
                ((Math.sqrt(2.0) * wheelRadius) / 4.0) * (wheelSpeeds.LF + wheelSpeeds.RF - wheelSpeeds.LB - wheelSpeeds.RB),
                new Angle((wheelRadius / (4.0 * turnDist)) * (wheelSpeeds.LF + wheelSpeeds.RF + wheelSpeeds.LB + wheelSpeeds.RB), AngleUnit.RADIAN)
        );
    }
}
