/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc93.FinalRobot;

import edu.wpi.first.wpilibj.PIDOutput;

public class PIDOutputAdapter implements PIDOutput {

    private double m_pidOutputValue;

    PIDOutputAdapter() {
        m_pidOutputValue = 0;
    }

    public void pidWrite(double pidOutputValue) {
        m_pidOutputValue = pidOutputValue;
    }

    public double getOutput() {
        return m_pidOutputValue;
    }
}
