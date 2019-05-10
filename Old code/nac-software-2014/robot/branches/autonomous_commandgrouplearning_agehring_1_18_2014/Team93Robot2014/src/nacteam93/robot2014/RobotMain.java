/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package nacteam93.robot2014;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import nacteam93.robot2014.commands.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotMain extends IterativeRobot {

    Command command1;
//    shoot baquisition shoot
    Command command2;
//    shoot driver (both motor1 and motor2)
    Command command3;
//    drive turn drive
    Command command4;
//    stop errything
    commandGroup1 script1;
    CommandGroup2 script2;
    
    EncoderTest myTest; // debug
   
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // instantiate the command used for the autonomous period
        //autonomousCommand = new ExampleCommand();
        RobotMap.initialize();
        OI.initialize();
        myTest = new EncoderTest();
        myTest.robotInit();

        command1 = new ExampleCommand();
        command2 = new FireShooter();
        command3 = new OutputBall();
        command4 = new IntakeBall();
        script1 = new commandGroup1();
        script2 = new CommandGroup2();
        //RobotMap.motor1 = new Victor(1);
        //RobotMap.motor2 = new Victor(2);
        //RobotMap.switch1= new DigitalInput(2);
        //RobotMap.switch2= new DigitalInput(3);
         
        // Initialize all subsystems
        CommandBase.init();
    }

    public void autonomousInit() {
        myTest.autonomousInit();
        
        // schedule the autonomous command (example)
        // autonomousCommand.start();
        // autoCommand.start();
        //RobotMap.switch1.get();
        //Place below into switch statement
        /*
        int switch1Int = RobotMap.switch1.get() ? 1 : 0;//if switch1 == true, switch1Int = 1, else switch1Int = 0
        switch(switch1Int){
            case 0:
                if(RobotMap.switch2.get()){
                    script2.start();
                }//if
                else{
                    script1.start();
                }//else
                break;
                
            case 1:
                if(RobotMap.switch2.get()){
                    command3.start();
                }//if
                else{
                    command4.start();
                }//else
                break;
                
            default:
                break;
        }//switch
//        if (RobotMap.switch1.get() == false && RobotMap.switch2.get() == false){
//            script1.start();
//        }
//        if (RobotMap.switch1.get() == false && RobotMap.switch2.get() == true){
//            script2.start();
//        }
//        if (RobotMap.switch1.get() == true && RobotMap.switch2.get() == true){
//            command3.start();
//        }
//        if (RobotMap.switch1.get() == true && RobotMap.switch2.get() == false){
//            command4.start();
//        }
         * 
         */
    }
        
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        myTest.autonomousPeriodic();
        //Scheduler.getInstance().run();
        
        
        /*boolean switchVal = switch1.get();
        System.out.println(switchVal);
        
        //System.out.println(switch1.get());
        if(switchVal == true)
        {
            System.out.println("Motor 1 enabled");
            motor1.set(0.5);
        }
        else
        {
            System.out.println("The 2nd Motor is enabled");
            motor2.set(0.5);
        }
        
        if(switch1.get() == true)
        {
            motor1.set(.5);
            motor2.set(0);
        }
        if(switch1.get() == false)
        {
            motor1.set(0);
            motor2.set(.5);
        }*/
        
    }

    public void teleopInit() {
	// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        //autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        
        OI.fireShooter.whenPressed(new FireShooter());
        OI.retractShooter.whenPressed(new RetractShooter());

        Scheduler.getInstance().run();
    
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
