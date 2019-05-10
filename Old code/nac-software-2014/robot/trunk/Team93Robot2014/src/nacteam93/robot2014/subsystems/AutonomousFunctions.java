/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nacteam93.robot2014.subsystems;

import nacteam93.robot2014.commands.CommandBase;

/**
 *
 * @author NAC Controls
 */

public class AutonomousFunctions 
{
    
    
    //The following are "settings" that the autonomous switches can set.
        public static boolean getInPositionFunction()
        {
            return (CommandBase.switches.autonomousSelectorOne());
            //Whether ot not the robot is in the position we want it to be in in the beginning of the match.
            //True: The robot does not move an extra amount to get in position. ALWAYS use this setting unless we are unable to place our robot where we want.
            //False: The robot moves an extra amount to et in position. NEVER use this setting unless we can't place the robot where we want it.
            //WARNING: Untuned values for moving the robot with this switch.
        }
        public static boolean getAttemptHotDetectionFunction()
        {
            return (CommandBase.switches.autonomousSelectorTwo());
            //Whether ot not we want the camera to attempt hot detection.
            //True: Attempt Hot Detection. Always use this setting unless the camera is broken or bugging out and using all of our CPU power.
            //False: Do not attempt Hot Detection. Never use this setting unless the camera is not functional.
        }
        public static boolean getShootHighFunction()
        {
            return (true);
            //Whether we want the robot to shoot high or shoot low.
            //True: Shoot High. ALWAYS use this setting unless we have no shooter.
            //False: Shoot Low. NEVER use this setting unless we have no shooter.
            //WARNING: Untested values for shooting low.
        }
        public static boolean getIsShootingFunction()
        {
            return (CommandBase.switches.autonomousSelectorThree());
            //Whether or not we want the robot to try to score at all in Autonomous.
            //True: Trying to score in Autonomous normally. ALWAYS use this setting unless autonomous could break something.
            //False: Doing Moblility Only. NEVER use this setting unless our autonomous code is broken beyond use.
        }
        public static boolean getSleepFunction()
        {
            return (true);
            //Whether or not we want to sleep for "milisecondsToSleep" miliseconds.
            //True: Wait for "milisecondsToSleep" miliseconds before shooting. Almost Always let this be true, as this is almost always essential for timing with Hot Detection.
            //False: Do not wait at all before shooting. Almost never let the option be false, as waiting is important for Hot Detection Timing.
        }


        public static boolean planChooserSwitchOne()
        {
            return (CommandBase.switches.autonomousSelectorFour());
        }
        public static boolean planChooserSwitchTwo()
        {
            return (CommandBase.switches.autonomousSelectorFive());
        }
        public static boolean planChooserSwitchThree()
        {
            return (CommandBase.switches.autonomousSelectorSix());
        }
        public static boolean planChooserSwitchFour()
        {
            return (false);
        }
        public static boolean planChooserSwitchFive()
        {
            return (false);
        }
        public static boolean planChooserSwitchSix()
        {
            return (false);
        }
        
                
        public static int getPlanNumber()
        {
            int planNumber = 0;
            if (planChooserSwitchOne())
            {
                planNumber += 1;
            }
            if (planChooserSwitchTwo())
            {
                planNumber += 2;
            }
            if (planChooserSwitchThree())
            {
                planNumber += 4;
            }
            if (planChooserSwitchFour())
            {
                planNumber += 8;
            }
            if (planChooserSwitchFive())
            {
                planNumber += 16;
            }
            if (planChooserSwitchSix())
            {
                planNumber += 32;
            }
            return (planNumber);
        }
        
    public AutonomousFunctions()
    {
        
    }
}