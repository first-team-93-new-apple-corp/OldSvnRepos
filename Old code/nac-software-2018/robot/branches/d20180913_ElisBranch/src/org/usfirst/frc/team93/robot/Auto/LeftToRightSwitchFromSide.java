package org.usfirst.frc.team93.robot.Auto;

import org.usfirst.frc.team93.robot.commands.DriveEncoderTicks;
import org.usfirst.frc.team93.robot.commands.RollerOuttakeController;
import org.usfirst.frc.team93.robot.commands.ScalerMove;
import org.usfirst.frc.team93.robot.commands.SleepCommand;
import org.usfirst.frc.team93.robot.commands.TilterDown;
import org.usfirst.frc.team93.robot.commands.TilterUp;
import org.usfirst.frc.team93.robot.commands.TurnDeg;
import org.usfirst.frc.team93.robot.subsystems.Drive;
import org.usfirst.frc.team93.robot.subsystems.Scaler;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class LeftToRightSwitchFromSide extends CommandGroup
{
	
	public LeftToRightSwitchFromSide()
	{
		addSequential(new TilterUp());
		addSequential(new SleepCommand(SmartDashboard.getNumber("TimeBeforeAuto", 0)));
		
		addSequential(new DriveEncoderTicks(Drive.TICKSPERFOOT * 18.97));
		addSequential(new TurnDeg(-89, 4));
		addSequential(new DriveEncoderTicks(Drive.TICKSPERFOOT * 14.14));
		addSequential(new TurnDeg(-90, 4));
		addSequential(new ScalerMove(Scaler.ScalerLocation.MIDDLE));
		addSequential(new TilterDown());
		addSequential(new SleepCommand(0.6));
		// addSequential(new DriveOneFoot((int) (1 * Drive.TICKSPERFOOT)));
		addSequential(new RollerOuttakeController());
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
