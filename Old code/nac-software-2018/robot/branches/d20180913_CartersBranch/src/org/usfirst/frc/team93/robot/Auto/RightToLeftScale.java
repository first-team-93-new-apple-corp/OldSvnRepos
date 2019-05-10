package org.usfirst.frc.team93.robot.Auto;

import org.usfirst.frc.team93.robot.commands.DriveEncoderTicks;
import org.usfirst.frc.team93.robot.commands.DriveOneFoot;
import org.usfirst.frc.team93.robot.commands.RollerControllerDisable;
import org.usfirst.frc.team93.robot.commands.RollerOuttakeController;
import org.usfirst.frc.team93.robot.commands.ScalerMove;
import org.usfirst.frc.team93.robot.commands.SleepCommand;
import org.usfirst.frc.team93.robot.commands.TilterDown;
import org.usfirst.frc.team93.robot.commands.TilterUp;
import org.usfirst.frc.team93.robot.commands.TurnEncoderDegrees;
import org.usfirst.frc.team93.robot.subsystems.Drive;
import org.usfirst.frc.team93.robot.subsystems.Scaler;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class RightToLeftScale extends CommandGroup
{
	
	public RightToLeftScale()
	{
		
		addSequential(new TilterUp());
		addSequential(new SleepCommand(SmartDashboard.getNumber("TimeBeforeAuto", 0)));
		
		addSequential(new DriveEncoderTicks(Drive.TICKSPERFOOT * 19));
		addSequential(new TurnEncoderDegrees(-76, 3, 1));
		addSequential(new DriveEncoderTicks(Drive.TICKSPERFOOT * (18)));
		// addSequential(new DriveEncoderTicks(Drive.TICKSPERFOOT * 9.7));
		// addSequential(new DriveEncoderTicks(Drive.TICKSPERFOOT * 8.859));
		addSequential(new TurnEncoderDegrees(100, 5, 1));
		
		addParallel(new DriveEncoderTicks(Drive.TICKSPERFOOT * 3));
		addSequential(new ScalerMove(Scaler.ScalerLocation.TOP));
		addParallel(new TilterDown());
		addSequential(new DriveOneFoot(1 * Drive.TICKSPERFOOT));
		addSequential(new RollerOuttakeController());
		addSequential(new SleepCommand(0.25));
		addSequential(new RollerControllerDisable());
		
		/*
		 * addParallel(new DriveOneFoot((int) (-0.75 * Drive.TICKSPERFOOT)));
		 * addSequential(new TilterUp());
		 * addSequential(new SleepCommand(0.6));
		 * addSequential(new ScalerMove(Scaler.ScalerLocation.BOTTOM));
		 * addSequential(new ScalerDisableAuto());
		 * 
		 * addParallel(new TilterDown());
		 * addSequential(new TurnDeg(108, 9));
		 * addParallel(new RollerIntakeController());
		 * addSequential(new SetMotorVoltage(-.55));
		 * addSequential(new SleepCommand(1.5));
		 * addSequential(new RollerControllerDisable());
		 */
		
		// Add Commands here:
		// e.g. addSequential(new Command1());
		// addSequential(new Command2());
		// these will run in order.
		
		// To run multiple commands at the same time,
		// use addParallel()
		// e.g. addParallel(new Command1());
		// addSequential(new Command2());
		// Command1 and Command2 will run in parallel.
		
		// A command group will require all of the subsystems that each member
		// would require.
		// e.g. if Command1 requires chassis, and Command2 requires arm,
		// a CommandGroup containing them would require both the chassis and the
		// arm.
	}
}
