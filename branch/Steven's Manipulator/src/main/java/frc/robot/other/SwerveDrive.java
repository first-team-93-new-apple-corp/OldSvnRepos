package frc.robot.other;

import java.util.List;

import frc.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SwerveDrive {

    // Robot dimensions. Units are of no importance. Required
    private static final double length = 1.1;
    private static final double width = 1.75;

    // The diagonal of the robot dimensions. Internal
    private static final double diagonal = 1.414;

    // The scale factor to control robot maximum speed. Optional.
    private static final double SCALE_SPEED = 1.00;

    // The "Centric" mode for the robot
    private static CentricMode centricMode = CentricMode.ROBOT;

    public enum CentricMode {
        ROBOT, FIELD
    }

    /**
     * move Moves the robot based on 3 inputs - fwd (forward), str(strafe), and
     * rcw(rotation clockwise) Inputs are between -1 and 1, with 1 being full power,
     * -1 being full reverse, and 0 being neutral. The method uses gyro for field
     * centric driving, if it is enabled.
     * 
     * @param fwd       the forward power value range -1.0(back) - 1.0(fwd)
     * @param str       the strafe power value range -1.0(left) - 1.0(right)
     * @param rcw       the rotation power value range -1.0(ccw) - 1.0(cw)
     * @param gyroValue the value of the gyro input to be used by the calculation.
     *                  Optional. Only used when the robot is in field-centric mode.
     *                  Values are 0-360
     * @return List of wheel movement directives. The list indices correspond to the
     *         wheel numbering scheme as above, zero-based: FR 0, FL 1, BL 2, BR 3
     */
    public static void move(double fwd, double str, double rcw, Double gyroValue) {

        if ((gyroValue == null) && centricMode.equals(CentricMode.FIELD)) {
            throw new IllegalStateException("Cannot use field centric mode without a Gyro value");
        }

        // Adjust for Gyro (if wanted)
        if (isFieldCentric()) {
            // Convert the gyro angle (in degrees) to radians.
            double gyro = (gyroValue * Math.PI) / 180;

            double temp = fwd * Math.cos(gyro) + str * Math.sin(gyro);
            str = -fwd * Math.sin(gyro) + str * Math.cos(gyro);
            fwd = temp;
        }

        // These 4 variables are used in the swerve drive calculations.
        double backXComponent = str - rcw * (length / diagonal);
        double frontXComponent = str + rcw * (length / diagonal);
        double leftYComponent = fwd - rcw * (width / diagonal);
        double rightYComponent = fwd + rcw * (width / diagonal);

        // These are the equations for the wheel speed, for motors 1-4.
        double FLSpeed = Math.sqrt(Math.pow(frontXComponent, 2) + Math.pow(leftYComponent, 2));
        double FRSpeed = Math.sqrt(Math.pow(frontXComponent, 2) + Math.pow(rightYComponent, 2));
        double BRSpeed = Math.sqrt(Math.pow(backXComponent, 2) + Math.pow(rightYComponent, 2));
        double BLSpeed = Math.sqrt(Math.pow(backXComponent, 2) + Math.pow(leftYComponent, 2));

        // These are the equations for the wheel angle, for motors 1-4
        double FLAngle = Math.atan2(frontXComponent, leftYComponent) * 180 / Math.PI;
        double FRAngle = Math.atan2(frontXComponent, rightYComponent) * 180 / Math.PI;
        double BRAngle = Math.atan2(backXComponent, rightYComponent) * 180 / Math.PI;
        double BLAngle = Math.atan2(backXComponent, leftYComponent) * 180 / Math.PI;

        // This is to normalize the speed (if the largest speed is greater than 1,
        // change accordingly).
        double max = FRSpeed;
        if (FLSpeed > max)
            max = FLSpeed;
        if (BRSpeed > max)
            max = BRSpeed;
        if (BLSpeed > max)
            max = BLSpeed;
        if (max > 1) {
            FRSpeed /= max;
            FLSpeed /= max;
            BRSpeed /= max;
            BLSpeed /= max;
        }

        // Used to scale the movement speeds for testing (so you don't crash into walls)
        FRSpeed *= SCALE_SPEED;
        FLSpeed *= SCALE_SPEED;
        BRSpeed *= SCALE_SPEED;
        BLSpeed *= SCALE_SPEED;

        Drive.FLModule.set(FLAngle, FLSpeed);
        SmartDashboard.putNumber("FLANGLE 2", FLAngle);
        Drive.FRModule.set(FRAngle, FRSpeed);
        SmartDashboard.putNumber("FRANGLE 2", FRAngle);
        Drive.BLModule.set(BLAngle, BLSpeed);
        SmartDashboard.putNumber("BLANGLE 2", BLAngle);
        Drive.BRModule.set(BRAngle, BRSpeed);
        SmartDashboard.putNumber("BRANGLE 2", BRAngle);

    }

    private static boolean isFieldCentric() {
        return centricMode.equals(CentricMode.FIELD);
    }
}

class SwerveDirective {
    private double angle;
    private double speed;

    public SwerveDirective(double angle, double speed) {
        this.angle = angle;
        this.speed = speed;
    }

    public double getAngle() {
        return angle;
    }

    public double getSpeed() {
        return speed;
    }

    @Override
    public String toString() {
        return speed + "," + angle;
    }
}