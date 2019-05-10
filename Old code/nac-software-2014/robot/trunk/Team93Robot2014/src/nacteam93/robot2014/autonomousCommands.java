/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nacteam93.robot2014;
import edu.wpi.first.wpilibj.command.CommandGroup;
import nacteam93.robot2014.commands.*;
import nacteam93.robot2014.subsystems.AutonomousFunctions;
/**
 *
 * @author NEW Apple Corps Controls
 */
public class autonomousCommands extends CommandGroup 
{
    final static boolean Sequential = true;
    final static boolean Parallel = false;
    final int milisecondsToSleep = 500;
    final int milisecondsMoveRequires = 3000;
    //I don't know if the above time is correct. Completely Untested.
    public autonomousCommands()
    {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.
        //Now decide the plan for autonomous.
        
        loadPlan();
        
    }
    // To run multiple commands at the same time,
    // use addParallel()
    // e.g. addParallel(new Command1());
    //      addSequential(new Command2());
    // Command1 and Command2 will run in parallel.

    // A command group will require all of the subsystems that each member
    // would require.
    // e.g. if Command1 requires chassis, and Command2 requires arm,
    // a CommandGroup containing them would require both the chassis and the
    // arm.
    
    private void printSwitchStates()
    {
        System.out.println("In Position = " + AutonomousFunctions.getInPositionFunction());
        System.out.println("Attempting Hot Detection = " + AutonomousFunctions.getAttemptHotDetectionFunction());
        System.out.println("Shooting High = " + AutonomousFunctions.getShootHighFunction());
        System.out.println("Executing Autonomous = " + AutonomousFunctions.getIsShootingFunction());
        System.out.println("Waiting at certain points for timing = " + AutonomousFunctions.getSleepFunction());
        System.out.println("Plan Number = " + AutonomousFunctions.getPlanNumber());
        switch (AutonomousFunctions.getPlanNumber())
        {
            case (0):
            default:
                System.out.println("The order is Aim the tilter, Move the robot, Wait for the goal to become hot, Wait, Shoot, Finish.");
                break;
            case (1):
                System.out.println("The order is Move the robot, Wait for the goal to become hot, Aim the tilter, Shoot, Finish.");
                break;
            case (2):
                System.out.println("The order is Wait for the goal to become hot, Aim the tilter, Move the robot, Shoot, Finish.");
                break;
            case (3):
                System.out.println("The order is Wait for the goal to become hot, Move the robot while aiming the tilter, Shoot, Finish.");
                break;
        }
    }
    
    
    private void loadInitCommands()
    {
        loadCommandsToShiftGear();
        loadCommandsToPositionRobot();
        printSwitchStates();
    }
    
    
    private void loadCommandsToAimArm()
    {
        //Moves the robot tilter the amount to get ready for shooting.
        loadCommandsToAimArm(Sequential);
    }

    private void loadCommandsToAimArm(boolean addCommandType)
    {
        //Moves the robot tilter the amount to get ready for shooting.
        double pivoterAngle = 39.65;
        double pivoterMaxError = 1.0;        
        System.out.println("Attempting to pivot arm.");
        if (Sequential == addCommandType)
        {
            addSequential(new aimWithBrakeGroup(pivoterAngle, pivoterMaxError));
        }
        if (Parallel == addCommandType)
        {
            addParallel(new aimWithBrakeGroup(pivoterAngle, pivoterMaxError));
        }
    }
    private void loadCommandsToMoveRobot()
    {
        //Moves the robot the amount to get ready for shooting.
        int driveDistance = 3200;
        double driveError = 100;
        System.out.println("Attempting Mobility and moving robot into range.");
        addSequential(new DriveForwards(driveDistance, driveError));
    }
        
    private void loadCommandsToMoveRobotLow(){
        int driveDistance = 7900; //This Number Needs To Be Found
        double driveError = 50;
        System.out.println("Attempting Mobility and moving to low goal.");
        addSequential(new DriveForwards(driveDistance, driveError));
    }
    
    private void loadCommandsToAskHotDetection()
    {
        //This checks to see if the hotDetection switch is true, and checks for a hot zone is the switch is true.
        if(true == AutonomousFunctions.getAttemptHotDetectionFunction())
        {
            //Continually checks for a hot zone, or fires if it does not see one for 6 seconds.
            System.out.println("Checking for Hot Zone");
            //addSequential(new PeriodicallyCheckHot());
        }
        else
        {
            System.out.println("Not Attempting Hot Zone Detection");
        }
    }
    private void loadCommandsToShiftGear() 
    {
        //This loads commands to shift gears.
        System.out.println("Shifting Gear");
        addSequential(new shiftDrive(true));
    }
    private void loadCommandsToPositionRobot() 
    {
        //Checks to see if the robot is in position to shoot at the start of the match.
        if (true == AutonomousFunctions.getInPositionFunction())
        {
            System.out.println("Robot is in position. Not moving.");
            //Do nothing.
            //Already in position, do not need to move.
        }
        if (false == AutonomousFunctions.getInPositionFunction())
        {
            System.out.println("Robot is not in position. Moving.");
            //THESE WILL NEED TO BE CHANGED TO FIT PARAMETERS.
            //WARNING: UNTUNED VALUES
            //We do not know what these values should be currently.
            int driveDistance = 84;    // 84 in  (7 feet) used = 856 ticks
            double driveError = 12.0;  // 12 in used = 122 ticks
            addSequential(new DriveForwards(driveDistance,driveError));
        }
    }
    private void loadCommandsToAimLow(){
        final double armAngle = 18.0;
        final double maxError = 5.0;
        addSequential(new aimWithBrakeGroup(armAngle, maxError));
    }
    private void loadCommandsToFireLow(){
        addSequential(new FireLow());
    }
    private void loadCommandsToWait()
    {
        //This command loads the commands to wait.
        if (true == AutonomousFunctions.getSleepFunction())
        {
            System.out.println("Waiting.");
            addSequential(new sleepCommand(milisecondsToSleep));
        }
    }
    private void loadCommandsToShoot()
    {
        if (AutonomousFunctions.getIsShootingFunction())
        {
        //This command loads the commands to shoot the shooter and retract.
        System.out.println("Shooting");
        addSequential(new ShootAndRetractGroup());
        }
    }
    
    
    private void loadPlanToTiltDriveOutputLow()
    {
        if (true == AutonomousFunctions.getAttemptHotDetectionFunction())
        {
            //Continually checks for a hot zone, or fires if it does not see one for 6 seconds.
            //addSequential(new PeriodicallyCheckHot());
        }
        //THESE WILL NEED TO BE MODIFIED LATER TO FIT PARAMETERS
        //WARNING: UNTUNED VALUES
        double pivoterAngle = 90.0;
        double pivoterMaxError = 5.0;
        addSequential(new aimWithBrakeGroup(pivoterAngle, pivoterMaxError));
        int driveDistance = 1711;
        double driveError = 10.0;
        addSequential(new DriveForwards(driveDistance, driveError));
        addSequential(new OutputBall());
    }
    
    private void loadPlanToTiltMoveCheckShoot()
    {
        //PLAN 0:
        //This plan:
        //Aims the tilter,
        //Moves the robot,
        //Waits for the goal to become hot,
        //Waits,
        //Shoots,
        //Finishes.
        loadCommandsToMoveRobot();
        loadCommandsToAimArm();
        loadCommandsToAskHotDetection();
        loadCommandsToWait();
        loadCommandsToShoot();
    }
    private void loadPlanToMoveCheckTiltShoot()
    {
        //PLAN 1:
        //This plan:
        //Moves the robot,
        //Waits for the goal to become hot,
        //Aims the tilter,
        //Shoots,
        //Finishes.
        loadCommandsToMoveRobot();
        loadCommandsToAskHotDetection();
        loadCommandsToAimArm();
        loadCommandsToWait();
        loadCommandsToShoot();
    }
    private void loadPlanToCheckTiltMoveShoot()
    {
        //PLAN 2:
        //This plan:
        //Waits for the goal to become hot,
        //Aims the tilter,
        //Moves the robot,
        //Shoots,
        //Finishes.
        loadCommandsToAskHotDetection();
        loadCommandsToAimArm();
        loadCommandsToMoveRobot();
        loadCommandsToShoot();
    }
    private void loadPlanToShootLowGoal()
    {
        //PLAN 3:
        //This plan:
        //Shoots Low Goal
        loadCommandsToMoveRobotLow();
        loadCommandsToAimLow();
        for (int milisecondsPassed = 0; milisecondsPassed < milisecondsMoveRequires; milisecondsPassed += milisecondsToSleep)
        {
            loadCommandsToWait();
        }
        loadCommandsToFireLow();
    }
    
    
    private void loadPlan()
    {
        int planNumber = AutonomousFunctions.getPlanNumber();
        loadInitCommands();
        System.out.println("The Number of the Plan being Executed is " + planNumber);
        switch (planNumber)
        {
            case (0):
            default:
                loadPlanToTiltMoveCheckShoot();
                break;
            case (1):
                loadPlanToMoveCheckTiltShoot();
                break;
            case (2):
                loadPlanToCheckTiltMoveShoot();
                break;
            case (3):
                loadPlanToShootLowGoal();
                break;
        }
    }
}