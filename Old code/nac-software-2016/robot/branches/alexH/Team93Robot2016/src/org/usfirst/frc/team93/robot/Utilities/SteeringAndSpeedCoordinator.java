package org.usfirst.frc.team93.robot.Utilities;

import edu.wpi.first.wpilibj.PIDOutput;

/**
 * This utility takes input from a SpeedReceiver to control speed and distance,
 * and a SteeringReceiver to control how it steers. Then, it sets two
 * PIDOutputs, right and left, to values based on input from the Receivers.
 * Basically, Steering and Speed input go in, Motor output goes out.
 * 
 * @author colby.mckinley
 */
public class SteeringAndSpeedCoordinator 
{

    /**
     * 
     */

    private double m_steeringGain;
    private double m_steering;
    private double m_leftPIDOutput;
    private double m_rightPIDOutput;
    private double m_speed;

    // The outputs that the SteeringAndApeedCoordinator outputs to.
    private PIDOutput m_outLeft;
    private PIDOutput m_outRight;

    // The enum that is used in PIDReceivers to tell whether it is for Steering
    // or Speed.
    public enum PIDReceiverType_t 
    {
        RECEIVER_TYPE_SPEED, RECEIVER_TYPE_STEERING
    };

    // This class, PIDReceiver, acts as an input for the
    // SteeringAndSpeedController.
    // Use this class by having PID Controllers output into these receivers.
    // The SteeringAndSpeedCoordinator will then use the input to set the output
    // motors.
    private class PIDReceiver implements PIDOutput 
    {

        PIDReceiverType_t m_type;
        SteeringAndSpeedCoordinator m_master;

        /**
         * This function is the constructor for a PIDReceiver. This constructor
         * stores the type of the PIDRecieerType_t and stores a reference from
         * the SteeringFunction.
         * 
         * @param type
         *            The type of PID Receiver. Use Receiver_Type_Speed for
         *            speed, and Receiver_Type_Steering for steering.
         * @param master
         *            The master is the reference to the class SteeringFunction
         */
        public PIDReceiver(PIDReceiverType_t type,
                SteeringAndSpeedCoordinator master) 
        {
            m_type = type;
            // store reference to the SteeringFunction who is my parent;
            m_master = master;
        }

        /*
         * (non-Javadoc)
         * 
         * @see edu.wpi.first.wpilibj.PIDOutput#pidWrite(double)
         */
        @Override
        public void pidWrite(double output) 
        {
            // If the receiver is a Speed Receiver,
            if (m_type == PIDReceiverType_t.RECEIVER_TYPE_SPEED) 
            {
                // Input the speed to the SteeringAndSpeedCoordinator.
                m_master.setSpeed(output);
            }
            // If the receiver is a Steering Receiver,
            else if (m_type == PIDReceiverType_t.RECEIVER_TYPE_STEERING)
            {
                // Input the Steering to the SteeringAndSpeedCoordinator.
                m_master.setSteering(output);
            }
        }
    }

    public PIDReceiver SpeedReceiver;
    public PIDReceiver SteeringReceiver;

    /**
     * The constructor for the SteeringAndSpeedController.
     * 
     * @param leftPIDOutput
     *            The left output.
     * @param rightPIDOutput
     *            The right output.
     */
    public SteeringAndSpeedCoordinator(PIDOutput leftPIDOutput,
            PIDOutput rightPIDOutput) 
    {
        // Store the PIDOutput locally in the class
        m_outLeft = leftPIDOutput;
        m_outRight = rightPIDOutput;

        // Creates the PIDReceivers, Steering and Speed, that are used as
        // inputs.
        SpeedReceiver = new PIDReceiver(PIDReceiverType_t.RECEIVER_TYPE_SPEED,
                this);
        SteeringReceiver = new PIDReceiver(
                PIDReceiverType_t.RECEIVER_TYPE_STEERING, this);
    }

    /**
     * This function should only be called through the pidWrite of a constituent
     * receiver. This moves the speed input from the PIDReceiver to the
     * SteeringAndSpeedCoordinator.
     */
    private void setSpeed(double speed) 
    {
        m_speed = speed;
        updateOutputs();
    }

    /**
     * This function should only be called through the pidWrite of a constituent
     * receiver. This moves the steering input from the PIDReceiver to the
     * SteeringAndSpeedCoordinator.
     */
    private void setSteering(double steering) 
    {
        m_steering = steering;
        updateOutputs();
    }

    /**
     * Set a new steering gain. Use 0.0 when steering control is not desired.
     * The gain is the multiplier that determines how much the steering input
     * will affect the outputs. A small gain will make the steering affect the
     * outputs less, while a large gain will make the steering input affect the
     * outputs more.
     */
    public void setSteeringGain(double newGain) 
    {
        m_steeringGain = newGain;
        updateOutputs();
    }

    /**
     * This method is called by setSteering and setSpeed through pidWrite of
     * constituent receivers. Thus, this will be called whenever pidWrite is
     * called, and thus will be called automatically whenever it is needed.
     * There is no need to call this method directly.
     */
    private void updateOutputs() 
    {
        // Combines the speed and steering vectors into left and right outputs
        m_leftPIDOutput = m_speed + (m_steeringGain * m_steering);
        m_rightPIDOutput = m_speed - (m_steeringGain * m_steering);

        // writes the result to the member PIDOutputs
        m_outLeft.pidWrite(m_leftPIDOutput);
        m_outRight.pidWrite(m_rightPIDOutput);
    }
}