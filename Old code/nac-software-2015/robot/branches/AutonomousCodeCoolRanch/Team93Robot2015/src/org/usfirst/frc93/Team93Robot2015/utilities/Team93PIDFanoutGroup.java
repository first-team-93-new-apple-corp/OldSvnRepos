package org.usfirst.frc93.Team93Robot2015.utilities;

import edu.wpi.first.wpilibj.PIDOutput;

import java.util.ArrayList;

/**
 * @codereview ColbyMcKinley: In this area you should write a brief description
 *             of the utility, within the comments. Also this class should be
 *             renamed to a more appropriate name for its use.
 */
public class Team93PIDFanoutGroup implements PIDOutput {

    class PairPIDOutputGain {
        public PIDOutput m_pid;
        public double m_gain;

        public PairPIDOutputGain(PIDOutput pid) {
            // stuff here too
        }
    }

    ArrayList<PairPIDOutputGain> m_pidOutput = new ArrayList<PairPIDOutputGain>();

    // Initialize your subsystem here
    public Team93PIDFanoutGroup() {
        // Use these to get going:
        // setSetpoint() - Sets where the PID controller should move the system
        // to
        // enable() - Enables the PID controller.
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
        return 0.0;
    }

    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.wpi.first.wpilibj.PIDOutput#pidWrite(double)
     */
    @Override
    public void pidWrite(double output) {
        // TODO Auto-generated method stub

    }

    public void pidOutputList() {

    }

    public void addPIDOutputToList(PIDOutput pidOutput, double gain) {
        // m_pidOutput.add(new PairPIDOutputGain(stuff here));
    }
}
