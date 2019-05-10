/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.utilities;

import edu.wpi.first.wpilibj.PIDOutput;
import java.util.ArrayList;

/**
 * This makes sure that the PIDOutput is within range of -1 to 1 and then averages past outputs to save as a new output that gets put into the motors
 */

public class ElevatorPIDOutput implements PIDOutput {

    private ArrayList<Double> outputAverage = new ArrayList<Double>();
    private PIDOutput m_rightMotor;
    private PIDOutput m_leftMotor;

    public ElevatorPIDOutput(int numOfAverages, PIDOutput rightMotor, PIDOutput leftMotor) {
        m_rightMotor = rightMotor;
        m_leftMotor = leftMotor;
        for (int i = 0; i < numOfAverages; i++) {
            outputAverage.add(0.0);
        }
    }

    @Override
    public void pidWrite(double output) {

        if (output > 1) {
            output = 1;
        } else if (output < -1) {
            output = -1;
        }
        outputAverage.add(output);
        outputAverage.remove(0);
        double sum = 0;
        for (int i = 0; i < outputAverage.size(); i++) {
            sum += outputAverage.get(i);
        }
        output = sum / outputAverage.size();
        m_rightMotor.pidWrite(output);
        m_leftMotor.pidWrite(output);
        // puts output into range of -1 to 1 and then sets the motor speed
    }
}
