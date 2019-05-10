/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.utilities;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import frc.robot.subsystems.Elevator;

/**
 * This averages the encoders so only one PID has to be used
 */
public class ElevatorPIDSource implements PIDSource {

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {

    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return null;
    }

    @Override
    public double pidGet() {
        //averages encoder values
        double leftEncoder = Elevator.elevatorEncoderLeft.getDistance();
        double rightEncoder = Elevator.elevatorEncoderRight.getDistance();
        double encoderAverage = leftEncoder + rightEncoder / 2;
        return encoderAverage;
	}
}
