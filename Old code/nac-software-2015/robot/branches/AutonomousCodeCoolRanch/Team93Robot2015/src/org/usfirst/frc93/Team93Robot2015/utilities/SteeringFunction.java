package org.usfirst.frc93.Team93Robot2015.utilities;

import edu.wpi.first.wpilibj.PIDOutput;

/**
 * The convention for steering is that left is positive and right is negative
 * This is because that that when the angle is positive it is going left think
 * of it like a clock, when the hand goes clockwise, the angle is positive, when
 * the hand goes counter clockwise, the angle is negative
 * 
 * @author Colby McKinley
 */
public class SteeringFunction implements PIDOutput {
    // Start of Initialization of Variables
    // Doubles
    private double m_gain;
    private double m_steering;
    private double m_leftPIDOutput;
    private double m_rightPIDOutput;
    private double m_speed;
    // PIDOutputs
    private PIDOutput m_outLeft;
    private PIDOutput m_outRight;

    // End of Initialization of Variables
    // Enums
    public enum PIDReceiverType_t {
        RECEIVER_TYPE_SPEED, RECEIVER_TYPE_STEERING
    };

    private class PIDReceiver implements PIDOutput {

        PIDReceiverType_t m_type;
        SteeringFunction m_master;

        /**
         * This function stores what type the PIDRecieerType_t is and it stores
         * a reference from the SteeringFunction
         * 
         * @param type
         *            The type of PID Receiver. Use Receiver_Type_Speed for
         *            speed, and Receiver_Type_Steering for steering.
         * @param master
         *            The master is the reference to the class SteeringFunction
         */
        public PIDReceiver(PIDReceiverType_t type, SteeringFunction master) {
            // store what type of receiver I am
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
        /**
         * This function checks to see if that the reference to SteeringFunction, m_master, is null in order to execute the code.  
         * If the m_type, defined in the PIDReciver constructor is for speed or for steering.  
         * Once m_type is determine, it is set to the output given from the parameters list
         * @param output  The output sets either the speed or the steering once it has been determine what m_type is from the PIDReciver constructor
         */
        public void pidWrite(double output) {
            // TODO Auto-generated method stub
            // validate that SteeringFunction reference is not null
            if (m_master != null) {
                // Determine if we are a speed receiver
                if (m_type == PIDReceiverType_t.RECEIVER_TYPE_SPEED) {
                    // Set the speed to the output (given from the parameter)
                    m_master.setSpeed(output);
                }
                // Determine if we are steering receiver
                else if (m_type == PIDReceiverType_t.RECEIVER_TYPE_STEERING) {
                    // Set the steering to the output (given from the parameter)
                    m_master.setSteering(output);
                }
            }
            else {
            }
        }
    }

    // This is the intended way for you to call the class
    /**
     * This public instance is the intended way to use the TODO class. The
     * client should pass SpeedReceiver to a PIDController. SpeedReceiver will
     * receive pidWrite calls from a PIDController and then call setSpeed on the
     * SteeringFunction, which will in turn update the output commands to the
     * associated PIDOutputs.
     */
    public PIDReceiver SpeedReceiver = new PIDReceiver(
            PIDReceiverType_t.RECEIVER_TYPE_SPEED, this);
    /**
     * This method is the intended way to use the PIDReciver class
     */
    public PIDReceiver SteeringReceiver = new PIDReceiver(
            PIDReceiverType_t.RECEIVER_TYPE_STEERING, this);

    /**
     * The constructor is here to store the PIDOutputs the user wishes to use.
     * The PIDOutputs are then used in updateOutputs to update commands to the
     * PIDOutputs. This occurs whenever new data is received from the
     * PIDControllers associated with SteeringReceiver and SpeedReceiver.
     * 
     * @param leftPIDOutput
     *            The leftPIDOutput is sets the local variable m_outLeft
     * @param rightPIDOutput
     * 
     */
    public SteeringFunction(PIDOutput leftPIDOutput, PIDOutput rightPIDOutput) {
        // Store the PIDOutput locally in the class
        m_outLeft = leftPIDOutput;
        m_outRight = rightPIDOutput;
        // call the updateOutputs function
        updateOutputs();
    }

    /**
     * 
     * @see edu.wpi.first.wpilibj.PIDOutput#pidWrite(double)
     */
    @Override
    public void pidWrite(double output) {
        // TODO Auto-generated method stub
    }

    /**
     * This function uses the inputed speed to update the function updateOutputs
     * 
     * @param speed
     *            The speed sets the PID speed This function sets the speed and
     *            then updates the updateOutputs function
     */
    public void setSpeed(double speed) {
        // set m_speed to speed
        m_speed = speed;
        // call updateOutputs to refresh the equation in updateOutputs
        updateOutputs();
    }

    /**
     * This function uses the inputed steering to update the function
     * updateOutputs
     * 
     * @param steering
     *            The steering set the PID steering This function set the
     *            steering and the updates the updateOutput function
     */
    public void setSteering(double steering) {
        // set m_steering to steering
        m_steering = steering;
        // call update Outputs to refresh the equations in updateOutputs
        updateOutputs();
    }

    /**
     * While you can access and you this function, you should not directly use
     * this function, the public PIDReceiver SpeedReceiver = new
     * PIDReceiver(PIDReceiverType_t.RECEIVER_TYPE_SPEED, this) and the public
     * PIDReceiver SteeringReceiver = new
     * PIDReceiver(PIDReceiverType_t.RECEIVER_TYPE_STEERING, this) is the
     * intended way to do this
     */
    public void updateOutputs() {
        // set a double to the equations below

        // in order to turn left, you should add (m_gain * m_steering)
        // in order to turn right, you should subtract (m_gain * m_steering)

        m_leftPIDOutput = m_speed + (m_gain * m_steering);
        m_rightPIDOutput = m_speed - (m_gain * m_steering);
        // now do a pidWrite with the parameter of the double you set above
        m_outLeft.pidWrite(m_leftPIDOutput);
        m_outRight.pidWrite(m_rightPIDOutput);
    }
}
