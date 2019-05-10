package org.usfirst.frc.team93.robot.commands;

//import org.usfirst.frc.team93.robot.util.HSVValueFinder;
//import org.usfirst.frc.team93.robot.util.Imshow;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class HSVTester extends InstantCommand {

	//HSVValueFinder myTester = new HSVValueFinder();
    public HSVTester() {
        super();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called once when the command executes
    protected void initialize() {
//    	new Thread(() -> {
//         
//          myTester.init();
//          while(!Thread.interrupted()) {
//             myTester.process();
//          }
//    	}).start();
    }

}
