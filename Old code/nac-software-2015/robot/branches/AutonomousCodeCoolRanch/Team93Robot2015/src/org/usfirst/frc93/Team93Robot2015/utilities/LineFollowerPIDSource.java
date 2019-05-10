/**
 * 
 */
package org.usfirst.frc93.Team93Robot2015.utilities;

import edu.wpi.first.wpilibj.PIDSource;

import org.usfirst.frc93.Team93Robot2015.RobotMap;

/**
 * @author Admin93
 * 
 * @codereview ColbyMcKinley: In this area you should write a brief description
 *             of the utility, within the comments.
 *
 */
public class LineFollowerPIDSource implements PIDSource {

    private final double EASY_LEFT = -.33;
    private final double MEDIUM_LEFT = -.66;
    private final double HARD_LEFT = -1;

    private final double EASY_RIGHT = .33;
    private final double MEDIUM_RIGHT = .66;
    private final double HARD_RIGHT = 1;

    private final double STRAIGHT = 0;

    /*
     * (non-Javadoc)
     * 
     * @see edu.wpi.first.wpilibj.PIDSource#pidGet()
     */
    @Override
    public double pidGet() {
        double offset = 0;

        boolean mediumOffsetLeft = false;
        boolean mediumOffsetRight = false;

        double lineFollowerLeftValue = RobotMap.LINE_FOLLOWER_LEFT.getValue();
        double lineFollowerMiddleValue = RobotMap.LINE_FOLLOWER_MIDDLE
                .getValue();
        double lineFollowerRightValue = RobotMap.LINE_FOLLOWER_RIGHT.getValue();

        // PLACEHOLDER VALUES, NEED TUNING ON ACTUAL VALUE
        if (lineFollowerLeftValue == 1 && lineFollowerMiddleValue == 1
                && lineFollowerRightValue == 1) {
            offset = STRAIGHT;
        }

        // Left offsets.
        if (lineFollowerLeftValue == 0 && lineFollowerMiddleValue == 1
                && lineFollowerRightValue == 1) {
            offset = EASY_LEFT;
        }

        if (lineFollowerLeftValue == 0 && lineFollowerMiddleValue == 0
                && lineFollowerRightValue == 1) {
            offset = MEDIUM_LEFT;
            mediumOffsetLeft = true;
        }

        // Right offsets.
        if (lineFollowerLeftValue == 1 && lineFollowerMiddleValue == 1
                && lineFollowerRightValue == 0) {
            offset = EASY_RIGHT;
        }

        if (lineFollowerLeftValue == 1 && lineFollowerMiddleValue == 0
                && lineFollowerRightValue == 0) {
            offset = MEDIUM_RIGHT;
            mediumOffsetRight = true;
        }

        // Determines a hard offset in either direction.
        if (lineFollowerLeftValue == 1 && lineFollowerMiddleValue == 1
                && lineFollowerRightValue == 1) {
            if (mediumOffsetLeft == true) {
                offset = HARD_LEFT;
            }

            if (mediumOffsetRight == true) {
                offset = HARD_RIGHT;
            }
        }
        // TODO Auto-generated method stub
        return offset;
    }

}
