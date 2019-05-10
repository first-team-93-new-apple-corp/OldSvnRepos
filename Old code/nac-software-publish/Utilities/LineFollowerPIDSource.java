/**
 * 
 */
package org.usfirst.frc93.Team93Robot2015.utilities;

import edu.wpi.first.wpilibj.PIDSource;

import org.usfirst.frc93.Team93Robot2015.RobotMap;

/**
 * This utility determines where we are at on the tape, and what type of offset
 * we should take in order to continue on a straight path.
 * 
 * @author Admin93
 */
public class LineFollowerPIDSource implements PIDSource {

    private final static double EASY_LEFT = -.33;
    private final static double MEDIUM_LEFT = -.66;
    private final static double HARD_LEFT = -1;

    private final static double EASY_RIGHT = .33;
    private final static double MEDIUM_RIGHT = .66;
    private final static double HARD_RIGHT = 1;

    private final static double STRAIGHT = 0;

    private boolean lastTimeWeWereTooFarLeft = false;

    /**
     * 
     * @see edu.wpi.first.wpilibj.PIDSource#pidGet()
     */
    @Override
    /**
     * This function determines what type of offset we want to do, based on where we are located along the tape.
     */
    public double pidGet() {
        double offset = STRAIGHT;

        // PLACEHOLDER VALUES, NEED TUNING ON ACTUAL VALUE
        if (lightsAreOnTarget()) {
            offset = STRAIGHT;
            lastTimeWeWereTooFarLeft = false;
        }

        // Left offsets.
        if (lightsAreRight()) {
            offset = EASY_LEFT;
            lastTimeWeWereTooFarLeft = false;
        }

        if (lightsAreMediumRight()) {
            offset = MEDIUM_LEFT;
            lastTimeWeWereTooFarLeft = false;
        }

        // Right offsets.
        if (lightsAreLeft()) {
            offset = EASY_RIGHT;
            lastTimeWeWereTooFarLeft = true;
        }

        if (lightsAreMediumLeft()) {
            offset = MEDIUM_RIGHT;
            lastTimeWeWereTooFarLeft = true;
        }

        // Determines a hard offset in either direction.
        if (lightsAreNotOnTarget()) {
            if (lastTimeWeWereTooFarLeft == true) {
                offset = HARD_RIGHT;
            }
            else {
                // since mediumOffsetLeft is false, we are assuming that we will
                // want to turn hard left.
                offset = HARD_LEFT;
            }
        }
        // TODO Auto-generated method stub
        return offset;
    }

    public enum LINE_FOLLOWER_VALUE {
        NO_TAPE, TAPE, NO_TYPE
    }

    /**
     * @return Returns what side of the tape we are on
     */
    private boolean lightsAreMediumRight() {
        if (getLineFollowerLeftValue() == LINE_FOLLOWER_VALUE.TAPE
                && getLineFollowerMiddleValue() == LINE_FOLLOWER_VALUE.TAPE
                && getLineFollowerRightValue() == LINE_FOLLOWER_VALUE.NO_TAPE) {
            // Check these values to ensure that they are correct
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * @return Returns what side of the tape we are on
     */
    private boolean lightsAreLeft() {
        if (getLineFollowerLeftValue() == LINE_FOLLOWER_VALUE.NO_TYPE
                && getLineFollowerMiddleValue() == LINE_FOLLOWER_VALUE.NO_TAPE
                && getLineFollowerRightValue() == LINE_FOLLOWER_VALUE.TAPE) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * @return Returns what side of the tape we are on
     */
    private boolean lightsAreRight() {
        if (getLineFollowerLeftValue() == LINE_FOLLOWER_VALUE.NO_TAPE
                && getLineFollowerMiddleValue() == LINE_FOLLOWER_VALUE.NO_TAPE
                && getLineFollowerRightValue() == LINE_FOLLOWER_VALUE.TAPE) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * @return Returns what side of the tape we are on
     */
    private boolean lightsAreOnTarget() {
        if (getLineFollowerLeftValue() == LINE_FOLLOWER_VALUE.TAPE
                && getLineFollowerMiddleValue() == LINE_FOLLOWER_VALUE.TAPE
                && getLineFollowerRightValue() == LINE_FOLLOWER_VALUE.TAPE) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * @return Returns what side of the tape we are on
     */
    private boolean lightsAreNotOnTarget() {
        if (getLineFollowerLeftValue() == LINE_FOLLOWER_VALUE.NO_TAPE
                && getLineFollowerMiddleValue() == LINE_FOLLOWER_VALUE.NO_TAPE
                && getLineFollowerRightValue() == LINE_FOLLOWER_VALUE.NO_TAPE) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * @return Returns what side of the tape we are on
     */
    private boolean lightsAreMediumLeft() {
        if (getLineFollowerLeftValue() == LINE_FOLLOWER_VALUE.TAPE
                && getLineFollowerMiddleValue() == LINE_FOLLOWER_VALUE.NO_TAPE
                && getLineFollowerRightValue() == LINE_FOLLOWER_VALUE.NO_TAPE) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * @return Returns what side of the tape we are on
     */
    private LINE_FOLLOWER_VALUE getLineFollowerLeftValue() {
        double lineFollowerLeftValue = RobotMap.LINE_FOLLOWER_LEFT.getValue();
        if (lineFollowerLeftValue == 1) {
            return LINE_FOLLOWER_VALUE.TAPE;
        }
        else {
            return LINE_FOLLOWER_VALUE.NO_TAPE;
        }
    }

    /**
     * @return Returns what side of the tape we are on
     */
    private LINE_FOLLOWER_VALUE getLineFollowerMiddleValue() {
        double lineFollowerMiddleValue = RobotMap.LINE_FOLLOWER_MIDDLE
                .getValue();
        if (lineFollowerMiddleValue == 1) {
            return LINE_FOLLOWER_VALUE.TAPE;
        }
        else {
            return LINE_FOLLOWER_VALUE.NO_TAPE;
        }
    }

    /**
     * @return Returns what side of the tape we are on
     */
    private LINE_FOLLOWER_VALUE getLineFollowerRightValue() {
        double lineFollowerRightValue = RobotMap.LINE_FOLLOWER_RIGHT.getValue();
        if (lineFollowerRightValue == 1) {
            return LINE_FOLLOWER_VALUE.TAPE;
        }
        else {
            return LINE_FOLLOWER_VALUE.NO_TAPE;
        }
    }

}
