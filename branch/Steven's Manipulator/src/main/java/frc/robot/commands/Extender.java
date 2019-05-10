package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.HatchManipulator;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;

public class Extender extends Command {

    private Timer timer;

    public Extender() {
        requires(Robot.hatchManipulator);
      }
    
      // Called just before this Command runs the first time
      @Override
      protected void initialize() {
          timer = new Timer();
          timer.reset();
          timer.start();
          if(HatchManipulator.pastPosition == DoubleSolenoid.Value.kReverse){
              HatchManipulator.extender.set(DoubleSolenoid.Value.kForward);
              HatchManipulator.pastPosition = DoubleSolenoid.Value.kForward;
          }
          else if(HatchManipulator.pastPosition == DoubleSolenoid.Value.kForward){
              HatchManipulator.extender.set(DoubleSolenoid.Value.kReverse);
              HatchManipulator.pastPosition = DoubleSolenoid.Value.kReverse;
          }
          else{
              System.out.println("something went wrong");
          }
      }
    
      // Called repeatedly when this Command is scheduled to run
      @Override
      protected void execute() {
         
      }
    
      // Make this return true when this Command no longer needs to run execute()
      @Override
      protected boolean isFinished() {
         if(timer.hasPeriodPassed(0.5) == true){
             return true;
         }
         else{
             return false;
         }
      }
    
      // Called once after isFinished returns true
      @Override
      protected void end() {
          HatchManipulator.extender.set(DoubleSolenoid.Value.kOff);
          timer.stop();
      }
    
      // Called when another command which requires one or more of the same
      // subsystems is scheduled to run
      @Override
      protected void interrupted() {
      }
}
