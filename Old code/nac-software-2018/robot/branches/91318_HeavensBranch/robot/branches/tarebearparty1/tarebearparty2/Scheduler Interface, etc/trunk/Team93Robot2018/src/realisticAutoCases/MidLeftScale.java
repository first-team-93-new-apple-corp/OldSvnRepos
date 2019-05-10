package realisticAutoCases;

import org.usfirst.frc.team93.robot.commands.DriveEncoderTicks;
import org.usfirst.frc.team93.robot.commands.RollerOuttakeController;
import org.usfirst.frc.team93.robot.commands.ScalerMove;
import org.usfirst.frc.team93.robot.commands.SleepCommand;
import org.usfirst.frc.team93.robot.commands.TilterDown;
import org.usfirst.frc.team93.robot.commands.TurnDeg;
import org.usfirst.frc.team93.robot.subsystems.Drive;
import org.usfirst.frc.team93.robot.subsystems.Scaler;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class MidLeftScale extends CommandGroup
{
	
	public MidLeftScale()
	{
		addSequential(new SleepCommand(SmartDashboard.getNumber("TimeBeforeAuto", 0)));
		addSequential(new TilterDown());
		addSequential(new DriveEncoderTicks(Drive.LENGTHTOSWITCHINFEET / 2 * Drive.TICKSPERFOOT));
		addSequential(new TurnDeg(-45, 3));
		addSequential(new DriveEncoderTicks(Drive.LENGTHTOSCALEINFEET / 1.5 * Drive.TICKSPERFOOT));
		addSequential(new TurnDeg(45, 3));
		addSequential(new DriveEncoderTicks(Drive.LENGTHTOSWITCHINFEET));
		addSequential(new TurnDeg(45, 3));
		addSequential(new ScalerMove(Scaler.ScalerLocation.TOP));
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
