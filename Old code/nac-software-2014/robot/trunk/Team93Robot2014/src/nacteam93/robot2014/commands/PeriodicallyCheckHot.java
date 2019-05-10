///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package nacteam93.robot2014.commands;
//
///**
// * @author NAC Controls
// */
//
////This command Makes the robot wait until the goal is hot.
//public class PeriodicallyCheckHot extends CommandBase {
//    
//    public PeriodicallyCheckHot() {
//        requires(CommandBase.hotDetection);
//    }
//    long oldTime;
//    // Called just before this Command runs the first time
//    protected void initialize() {
//        oldTime = System.currentTimeMillis();
//    }
//    
//    // Called repeatedly when this Command is scheduled to run
//    boolean isHot = false;
//    boolean timeout = false;
//    protected void execute() {
//        //Checks whether the goal is hot or not
//        CommandBase.hotDetection.detectHotZone();
//        if (true == CommandBase.hotDetection.isHot)
//        {
//            isHot = true; //If the goal is hot, then this is true.
//        }
//        if (System.currentTimeMillis()-oldTime >= 4750) {
//            System.out.println("Timeout activated. Shooting Anyway.");
//            timeout = true;
//        }
//    }
//    
//    // Make this return true when this Command no longer needs to run execute()
//    protected boolean isFinished() {
//        return (isHot || timeout); //If the goal is hot, then the command finishes.
//    }
//
//    // Called once after isFinished returns true
//    protected void end() {
//    }
//
//    // Called when another command which requires one or more of the same
//    // subsystems is scheduled to run
//    protected void interrupted() {
//        this.end();
//    }
//}