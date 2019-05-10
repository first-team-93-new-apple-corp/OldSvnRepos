package org.usfirst.frc.team93.robot.Auto;

import org.usfirst.frc.team93.robot.commands.DriveEncoderTicks;
import org.usfirst.frc.team93.robot.commands.DriveOneFoot;
import org.usfirst.frc.team93.robot.commands.RollerControllerDisable;
import org.usfirst.frc.team93.robot.commands.RollerIntakeControllerTimed;
import org.usfirst.frc.team93.robot.commands.RollerOuttakeControllerTimed;
import org.usfirst.frc.team93.robot.commands.ScalerMove;
import org.usfirst.frc.team93.robot.commands.SetMotorVoltage;
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
public class MiddleToRightSwitchx2 extends CommandGroup
{
	
	public MiddleToRightSwitchx2()
	{
		
		addSequential(new TilterUp());
		addSequential(new SleepCommand(SmartDashboard.getNumber("TimeBeforeAuto", 0)));
		addSequential(new DriveOneFoot(Drive.TICKSPERFOOT), 2);
		// addSequential(new DriveEncoderTicks(Drive.TICKSPERFOOT * 12.36));
		addSequential(new TurnEncoderDegrees(45, 5, 1));
		addSequential(new DriveEncoderTicks(Drive.TICKSPERFOOT * 7));
		addSequential(new TurnEncoderDegrees(-45, 4, 1.5));
		addParallel(new ScalerMove(Scaler.ScalerLocation.MIDDLE));
		addSequential(new DriveOneFoot((int) (Drive.TICKSPERFOOT * 2.5), 1.75));
		addSequential(new SetMotorVoltage(-0.25));
		// addSequential(new TilterDown());
		// addSequential(new SleepCommand(0.6));
		addSequential(new SleepCommand(0.25));
		addSequential(new RollerOuttakeControllerTimed());
		addSequential(new SleepCommand(0.35));
		addSequential(new SetMotorVoltage(0));
		addSequential(new RollerControllerDisable()); // First Cube has been placed in switch
		
		addParallel(new ScalerMove(Scaler.ScalerLocation.BOTTOM));
		addSequential(new DriveEncoderTicks(Drive.TICKSPERFOOT * -2.5));
		addSequential(new TilterDown());
		addSequential(new TurnEncoderDegrees(-65, 5, 1.5));
		addParallel(new RollerIntakeControllerTimed());
		addParallel(new ScalerMove(Scaler.ScalerLocation.BOTTOM));
		addSequential(new DriveOneFoot(Drive.TICKSPERFOOT * 2, 0.5));
		addSequential(new DriveOneFoot(Drive.TICKSPERFOOT * 1.25, 1));
		addSequential(new SleepCommand(0.5));
		
		addSequential(new DriveEncoderTicks(Drive.TICKSPERFOOT * -3.25));
		addSequential(new RollerControllerDisable()); // Second Cube in intaked
		addSequential(new TurnEncoderDegrees(70, 5, 5));
		addParallel(new ScalerMove(Scaler.ScalerLocation.MIDDLE));
		addSequential(new DriveOneFoot((int) (Drive.TICKSPERFOOT * 1.5), 2));
		addSequential(new SetMotorVoltage(-.25));
		addSequential(new SleepCommand(0.5));
		addSequential(new RollerOuttakeControllerTimed());
		addSequential(new SleepCommand(0.5));
		addSequential(new RollerControllerDisable());
		
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
