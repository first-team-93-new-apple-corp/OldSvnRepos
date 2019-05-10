/**
 * ':>;ll,,mmmmmmmmmmmllllllllllllllllllllllllllllllllllllllllllllllllllllllllllmkkkkkkkkkkkkkkllcfffffffffffffffvvvmllnbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbhhhhhhhhhhhhhhhhhhhhhhhhhhhb.gbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb,vvvvvvvvvvvvvvvvvvvvvvvvvvvv,,,,b,,bbbbbbbbbbbbbbbbbbbbf,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff,,v,v,,,,,,,,,,,,,,v,v,,lllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllffmmmmkfmkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk[;'.Lllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllkkkkkkkkkkkkkkkkkkkfkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkmmmmmmmmohhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhgffffffffffffffffffffffffffffffffffffffvvvvvfffffffffffffffffffffffffffffffffffffffffffffffffffffffffddddfffggggggggggggggggggggggggggggggggffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffgggfffffffffffffffffffffffffssffsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssrfydrrrrrrrrrrrc
 * kvvvvvvvcccccccvvcvvcvcvvvvvvvvvvvvvvvvvvvcccccccccccccccccccvcvccccccccccvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvcccccccccccvcvc
 * 
 */
package org.usfirst.frc93.Team93Robot2015.utilities;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;

/**
 * This utility coordinates two pidControllers
 * 
 * @author conor.mares
 * @version 1.1
 * @since 02-10-2015
 */

public class PIDCoordinator {
    /*
     * Make sure to call the update function in the execute() method of the
     * command where it is used.
     * 
     * This is because it does its own calculations and will not automatically
     * update like a PIDController will.
     */

    /**
     * 
     */
    public PIDCoordinator() {
        super();
        // TODO Auto-generated constructor stub
    }

    private PIDController m_controllerOne;
    private PIDController m_controllerTwo;

    private PIDSource m_sourceOne;
    private PIDSource m_sourceTwo;

    private double m_stepSize;
    private double m_setpoint;
    private double m_maxError;

    private double m_direction;

    /**
     * @param controllerOne
     *            the first PID controller to coordinate
     * 
     * @param controllerTwo
     *            the second PID controller to coordinate
     * 
     * @param sourceOne
     *            the PID source used by controllerOne
     * 
     * @param sourceTwo
     *            the PID source used by controllerTwo
     * 
     * @param stepSize
     *            the increment at which coordination is ensured
     * 
     * @param maxError
     *            the maximum error accepted before moving to the next increment
     */
    public PIDCoordinator(PIDController controllerOne,
            PIDController controllerTwo, PIDSource sourceOne,
            PIDSource sourceTwo, double stepSize, double maxError) {
        m_controllerOne = controllerOne;
        m_controllerTwo = controllerTwo;
        m_sourceOne = sourceOne;
        m_sourceTwo = sourceTwo;
        m_stepSize = stepSize;
        m_maxError = maxError;
    }

    /**
     * Prepares the coordinator for work once a setpoint has been passed. Both
     * PID controllers are enabled, and the incremental setpoint is set to the
     * current position.
     * 
     * @exception nullSetpointException
     *                if a setpoint has not been passed
     */
    public void initialize() throws nullSetpointException {

        if ((Double) m_setpoint != null) {

            m_direction = (m_sourceOne.pidGet() <= m_setpoint) ? 1 : -1;

            m_controllerOne.enable();
            m_controllerTwo.enable();

            m_controllerOne.setSetpoint(m_sourceOne.pidGet());
            m_controllerTwo.setSetpoint(m_sourceTwo.pidGet());
        }
        else {
            throw new nullSetpointException("There is no setpoint set");
        }
    }

    /**
     * Checks the current position of each PID controller, and increments the
     * setpoint if both controllers are at the proper position.
     */
    public void update() {

        boolean oneFinished = m_controllerOne.getError() <= m_maxError;
        boolean twoFinished = m_controllerTwo.getError() <= m_maxError;

        if (oneFinished) {
            m_controllerOne.disable();
        }
        if (twoFinished) {
            m_controllerTwo.disable();
        }
        if (oneFinished && twoFinished) {
            m_controllerOne.enable();
            m_controllerTwo.enable();
            incrementSetpoint();
        }
    }

    /**
     * Increments the setpoint to the next applicable value, or the final goal
     * if the next increment would overshoot.
     */
    private void incrementSetpoint() {
        double distanceToGoOne = m_setpoint - m_sourceOne.pidGet();
        double distanceToGoTwo = m_setpoint - m_sourceTwo.pidGet();

        double incrementOne;
        double incrementTwo;

        if (m_direction == 1) {
            incrementOne = (distanceToGoOne >= m_stepSize) ? m_stepSize
                    : distanceToGoOne;
            incrementTwo = (distanceToGoTwo >= m_stepSize) ? m_stepSize
                    : distanceToGoTwo;
        }
        else {
            incrementOne = (distanceToGoOne <= m_stepSize) ? -1 * m_stepSize
                    : distanceToGoOne;
            incrementTwo = (distanceToGoTwo <= m_stepSize) ? -1 * m_stepSize
                    : distanceToGoTwo;
        }

        m_controllerOne.setSetpoint(m_controllerOne.getSetpoint() + incrementOne);
        m_controllerTwo.setSetpoint(m_controllerTwo.getSetpoint() + incrementTwo);
    }

    /**
     * @return whether the coordinator has reached its setpoint
     */
    public boolean isFinished() {

        boolean oneFinished = (m_controllerOne.getError() <= m_maxError)
                && (m_controllerOne.getSetpoint() == m_setpoint);
        boolean twoFinished = m_controllerTwo.getError() <= m_maxError
                && (m_controllerOne.getSetpoint() == m_setpoint);

        return oneFinished && twoFinished;
    }

    /**
     * Disables both member PIDControllers
     */
    public void disable() {
        m_controllerOne.disable();
        m_controllerTwo.disable();
    }

    /**
     * @param setpoint
     *            the new overall setpoint goal
     */
    public void setSetpoint(double setpoint) {
        m_setpoint = setpoint;
    }

    /**
     * @return the current overall setpoint
     */
    public double getSetpoint() {
        return m_setpoint;
    }

    /**
     * @param stepSize
     *            the increment at which coordination is ensured
     */
    public void setStepSize(double stepSize) {
        m_stepSize = stepSize;
    }

    /**
     * @return the increment at which coordination is ensured
     */
    public double getStepSize() {
        return m_stepSize;
    }

    /**
     * @param maxError
     *            the maximum error accepted before moving to the next increment
     */
    public void setMaxError(double maxError) {
        m_maxError = maxError;
    }

    /**
     * @return the maximum error accepted before moving to the next increment
     */
    public double getMaxError() {
        return m_maxError;
    }

    /**
     * This exception is thrown
     */
    public class nullSetpointException extends Exception {

        private static final long serialVersionUID = 1L;

        public nullSetpointException() {
        }

        public nullSetpointException(String message) {
            super(message);
        }
    }

    public void setPIDControllerOne(double p, double i, double d) {
        m_controllerOne.setPID(p, i, d);
    }

    public void setPIDControllerTwo(double p, double i, double d) {
        m_controllerTwo.setPID(p, i, d);
    }
}
