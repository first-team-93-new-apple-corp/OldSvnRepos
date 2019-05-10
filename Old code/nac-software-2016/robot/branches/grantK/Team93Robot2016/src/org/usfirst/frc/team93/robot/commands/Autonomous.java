package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Autonomous extends CommandGroup
{

	int autonomousSelection;
	int xShootingPosition = 1;
	int zShootingPosition = 1;
	int rpm = 4500;
	int shootingWheelRadius = 2;
	int rampAngle = 7;

	public Autonomous()
	{
		autonomousSelection = 0;
		for (int i = 0; i < RobotMap.AutoSwiches.length; i++)
		{
			if (RobotMap.AutoSwiches[i].get())
			{
				autonomousSelection += Math.pow(2, i);
			}
		}

		autonomousSelection++;

		// 65.49 drive encoder ticks per inch

		switch (autonomousSelection)
		{
		case 1:
			// 1 BALL LOW BAR AUTONOMOUS
			// Robot must start 13 inches away from the wall

			addSequential(new IntakeBall(0.5));
			// drive to other side of field (past low bar)
			// 8776 ticks = 134 inches
			addSequential(new DriveForward(8776, 87));
			// turns 90 degrees
			addSequential(new TurnRight(1447, 14));
			// drives to middle of field (147 inches)
			addSequential(new DriveForward(9627, 96));
			// turns left 90 degrees
			addSequential(new TurnLeft(1447, 14));
			// drives to the goal (200 inches)
			addSequential(new DriveForward(13108, 130));
			addSequential(new SetFiringAngle(-rampAngle
					+ getFiringAngle(xShootingPosition, zShootingPosition, 36, 90, shootingWheelRadius, rpm)));
			addSequential(new SetShootWheelsPID(1.0, .1));
			addSequential(new SleepCommand(1));
			addSequential(new ShootBoulder(1.0));
			addSequential(new SetFiringAngle(0));
			break;
		case 2:
			// 2 BALL LOW BAR AUTONOMOUS
			// Robot must start 13 inches away from the wall

			addSequential(new IntakeBall(0.5));
			// drive to other side of field (past low bar)
			// 8776 ticks = 134 inches
			addSequential(new DriveForward(8776, 87));
			// turns to face the goal (40.37 degrees)
			addSequential(new TurnRight(649, 10));
			addSequential(new SetFiringAngle(
					getFiringAngle(xShootingPosition, zShootingPosition, 214, 93, shootingWheelRadius, rpm)));
			addSequential(new SetShootWheelsPID(1.0, .1));
			addSequential(new SleepCommand(1));
			addSequential(new ShootBoulder(1.0));
			addSequential(new SetFiringAngle(0));
			addParallel(new TurnLeft(649, 10));
			addSequential(new DriveForward(-9562, 95));
			addSequential(new TurnLeft(1447, 14));
			addParallel(new IntakeBall(.5));
			addSequential(new DriveForward(-3029, 30));
			addSequential(new DriveForward(3029, 30));
			addSequential(new TurnRight(1447, 14));
			addSequential(new DriveForward(9562, 95));
			// turns to face the goal (40.37 degrees)
			addSequential(new TurnRight(649, 10));
			addSequential(new SetFiringAngle(
					getFiringAngle(xShootingPosition, zShootingPosition, 214, 93, shootingWheelRadius, rpm)));
			addSequential(new SetShootWheelsPID(1.0, .1));
			addSequential(new SleepCommand(1));
			addSequential(new ShootBoulder(1.0));
			addSequential(new SetFiringAngle(0));
			break;

		case 3:
			// 1 BALL MOAT AUTONOMOUS
			// Must start in line with left half of the obstacle

			addSequential(new IntakeBall(0.5));
			// drive to other side of field (past low bar)
			// 8776 ticks = 134 inches
			addSequential(new DriveForward(8776, 87));
			// turns 90 degrees
			addSequential(new TurnRight(1447, 14));
			// drives to middle of field (147 inches)
			addSequential(new DriveForward(DistanceToCenter(), DistanceToCenter() / 100));
			// turns left 90 degrees
			addSequential(new TurnLeft(1447, 14));
			// drives to the goal (200 inches)
			addSequential(new DriveForward(13108, 130));
			addSequential(new SetFiringAngle(-rampAngle
					+ getFiringAngle(xShootingPosition, zShootingPosition, 36, 90, shootingWheelRadius, rpm)));
			addSequential(new SetShootWheelsPID(1.0, .1));
			addSequential(new SleepCommand(1));
			addSequential(new ShootBoulder(1.0));
			addSequential(new SetFiringAngle(0));
			break;
		case 4:
			// 2 BALL MOAT AUTONOMOUS
			// Robot must start in left half of obstacle

			addSequential(new IntakeBall(0.5));
			// drive to other side of field (past low bar)
			// 8776 ticks = 134 inches
			addSequential(new DriveForward(8776, 87));
			// turns to face the goal (40.37 degrees)
			addSequential(new TurnRight(getAngleToFaceGoal(), getAngleToFaceGoal() / 10));
			addSequential(new SetFiringAngle(getFiringAngle(xShootingPosition, zShootingPosition, 214,
					DistanceToCenter() / 65.49, shootingWheelRadius, rpm)));
			addSequential(new SetShootWheelsPID(1.0, .1));
			addSequential(new SleepCommand(1));
			addSequential(new ShootBoulder(1.0));
			addSequential(new SetFiringAngle(0));
			addParallel(new TurnLeft(getAngleToFaceGoal(), getAngleToFaceGoal() / 10));
			addSequential(new DriveForward(-9562, 95));
			addSequential(new TurnLeft(1447, 14));
			addParallel(new IntakeBall(.5));
			addSequential(new DriveForward(-3029, 30));
			addSequential(new DriveForward(3029, 30));
			addSequential(new TurnRight(1447, 14));
			addSequential(new DriveForward(9562, 95));
			// turns to face the goal (40.37 degrees)
			addSequential(new TurnRight(getAngleToFaceGoal(), getAngleToFaceGoal() / 10));
			addSequential(new SetFiringAngle(getFiringAngle(xShootingPosition, zShootingPosition, 214,
					DistanceToCenter() / 65.49, shootingWheelRadius, rpm)));
			addSequential(new SetShootWheelsPID(1.0, .1));
			addSequential(new SleepCommand(1));
			addSequential(new ShootBoulder(1.0));
			addSequential(new SetFiringAngle(0));
			break;
		case 5:
			// 1 BALL RAMPARTS AUTONOMOUS
			// Must start in line with left half of the obstacle

			addSequential(new IntakeBall(0.5));
			// drive to other side of field (past low bar)
			// 8776 ticks = 134 inches
			addSequential(new DriveForward(8776, 87));
			// turns 90 degrees
			addSequential(new TurnRight(1447, 14));
			// drives to middle of field (147 inches)
			addSequential(new DriveForward(DistanceToCenter(), DistanceToCenter() / 100));
			// turns left 90 degrees
			addSequential(new TurnLeft(1447, 14));
			// drives to the goal (200 inches)
			addSequential(new DriveForward(13108, 130));
			addSequential(new SetFiringAngle(-rampAngle
					+ getFiringAngle(xShootingPosition, zShootingPosition, 36, 90, shootingWheelRadius, rpm)));
			addSequential(new SetShootWheelsPID(1.0, .1));
			addSequential(new SleepCommand(1));
			addSequential(new ShootBoulder(1.0));
			addSequential(new SetFiringAngle(0));
			break;
		case 6:
			// 2 BALL RAMPARTS AUTONOMOUS
			// Must start in line with left half of the obstacle

			addSequential(new IntakeBall(0.5));
			// drive to other side of field (past low bar)
			// 8776 ticks = 134 inches
			addSequential(new DriveForward(8776, 87));
			// turns to face the goal
			addSequential(new TurnRight(getAngleToFaceGoal(), getAngleToFaceGoal() / 10));
			addSequential(new SetFiringAngle(getFiringAngle(xShootingPosition, zShootingPosition, 214,
					DistanceToCenter() / 65.49, shootingWheelRadius, rpm)));
			addSequential(new SetShootWheelsPID(1.0, .1));
			addSequential(new SleepCommand(1));
			addSequential(new ShootBoulder(1.0));
			addSequential(new SetFiringAngle(0));
			addParallel(new TurnLeft(getAngleToFaceGoal(), getAngleToFaceGoal() / 10));
			addSequential(new DriveForward(-9562, 95));
			addSequential(new TurnLeft(1447, 14));
			addParallel(new IntakeBall(.5));
			addSequential(new DriveForward(-3029, 30));
			addSequential(new DriveForward(3029, 30));
			addSequential(new TurnRight(1447, 14));
			addSequential(new DriveForward(9562, 95));
			// turns to face the goal
			addSequential(new TurnRight(getAngleToFaceGoal(), getAngleToFaceGoal() / 10));
			addSequential(new SetFiringAngle(getFiringAngle(xShootingPosition, zShootingPosition, 214,
					DistanceToCenter() / 65.49, shootingWheelRadius, rpm)));
			addSequential(new SetShootWheelsPID(1.0, .1));
			addSequential(new SleepCommand(1));
			addSequential(new ShootBoulder(1.0));
			addSequential(new SetFiringAngle(0));
			break;
		case 7:
			// 1 BALL ROCK WALL AUTONOMOUS
			addSequential(new IntakeBall(0.5));
			// drive to other side of field (past low bar)
			// 8776 ticks = 134 inches
			addSequential(new DriveForward(8776, 87));
			// turns 90 degrees
			addSequential(new TurnRight(1447, 14));
			// drives to middle of field (147 inches)
			addSequential(new DriveForward(DistanceToCenter(), DistanceToCenter() / 100));
			// turns left 90 degrees
			addSequential(new TurnLeft(1447, 14));
			// drives to the goal (200 inches)
			addSequential(new DriveForward(13108, 130));
			addSequential(new SetFiringAngle(-rampAngle
					+ getFiringAngle(xShootingPosition, zShootingPosition, 36, 90, shootingWheelRadius, rpm)));
			addSequential(new SetShootWheelsPID(1.0, .1));
			addSequential(new SleepCommand(1));
			addSequential(new ShootBoulder(1.0));
			addSequential(new SetFiringAngle(0));
			break;
		case 8:
			// 2 BALL ROCK WALL AUTONOMOUS

			addSequential(new IntakeBall(0.5));
			// drive to other side of field (past low bar)
			// 8776 ticks = 134 inches
			addSequential(new DriveForward(8776, 87));
			// turns to face the goal
			addSequential(new TurnRight(getAngleToFaceGoal(), getAngleToFaceGoal() / 10));
			addSequential(new SetFiringAngle(getFiringAngle(xShootingPosition, zShootingPosition, 214,
					DistanceToCenter() / 65.49, shootingWheelRadius, rpm)));
			addSequential(new SetShootWheelsPID(1.0, .1));
			addSequential(new SleepCommand(1));
			addSequential(new ShootBoulder(1.0));
			addSequential(new SetFiringAngle(0));
			addParallel(new TurnLeft(getAngleToFaceGoal(), getAngleToFaceGoal() / 10));
			addSequential(new DriveForward(-9562, 95));
			addSequential(new TurnLeft(1447, 14));
			addParallel(new IntakeBall(.5));
			addSequential(new DriveForward(-3029, 30));
			addSequential(new DriveForward(3029, 30));
			addSequential(new TurnRight(1447, 14));
			addSequential(new DriveForward(9562, 95));
			// turns to face the goal
			addSequential(new TurnRight(getAngleToFaceGoal(), getAngleToFaceGoal() / 10));
			addSequential(new SetFiringAngle(getFiringAngle(xShootingPosition, zShootingPosition, 214,
					DistanceToCenter() / 65.49, shootingWheelRadius, rpm)));
			addSequential(new SetShootWheelsPID(1.0, .1));
			addSequential(new SleepCommand(1));
			addSequential(new ShootBoulder(1.0));
			addSequential(new SetFiringAngle(0));
			break;
		case 9:
			// 1 BALL ROUGH TERRAIN AUTONOMOUS
			addSequential(new IntakeBall(0.5));
			// drive to other side of field (past low bar)
			// 8776 ticks = 134 inches
			addSequential(new DriveForward(8776, 87));
			// turns 90 degrees
			addSequential(new TurnRight(1447, 14));
			// drives to middle of field (147 inches)
			addSequential(new DriveForward(DistanceToCenter(), DistanceToCenter() / 100));
			// turns left 90 degrees
			addSequential(new TurnLeft(1447, 14));
			// drives to the goal (200 inches)
			addSequential(new DriveForward(13108, 130));
			addSequential(new SetFiringAngle(-rampAngle
					+ getFiringAngle(xShootingPosition, zShootingPosition, 36, 90, shootingWheelRadius, rpm)));
			addSequential(new SetShootWheelsPID(1.0, .1));
			addSequential(new SleepCommand(1));
			addSequential(new ShootBoulder(1.0));
			addSequential(new SetFiringAngle(0));
			break;
		case 10:
			// 2 BALL ROUGH TERRAIN AUTONOMOUS
			addSequential(new IntakeBall(0.5));
			// drive to other side of field (past low bar)
			// 8776 ticks = 134 inches
			addSequential(new DriveForward(8776, 87));
			// turns to face the goal
			addSequential(new TurnRight(getAngleToFaceGoal(), getAngleToFaceGoal() / 10));
			addSequential(new SetFiringAngle(getFiringAngle(xShootingPosition, zShootingPosition, 214,
					DistanceToCenter() / 65.49, shootingWheelRadius, rpm)));
			addSequential(new SetShootWheelsPID(1.0, .1));
			addSequential(new SleepCommand(1));
			addSequential(new ShootBoulder(1.0));
			addSequential(new SetFiringAngle(0));
			addParallel(new TurnLeft(getAngleToFaceGoal(), getAngleToFaceGoal() / 10));
			addSequential(new DriveForward(-9562, 95));
			addSequential(new TurnLeft(1447, 14));
			addParallel(new IntakeBall(.5));
			addSequential(new DriveForward(-3029, 30));
			addSequential(new DriveForward(3029, 30));
			addSequential(new TurnRight(1447, 14));
			addSequential(new DriveForward(9562, 95));
			// turns to face the goal
			addSequential(new TurnRight(getAngleToFaceGoal(), getAngleToFaceGoal() / 10));
			addSequential(new SetFiringAngle(getFiringAngle(xShootingPosition, zShootingPosition, 214,
					DistanceToCenter() / 65.49, shootingWheelRadius, rpm)));
			addSequential(new SetShootWheelsPID(1.0, .1));
			addSequential(new SleepCommand(1));
			addSequential(new ShootBoulder(1.0));
			addSequential(new SetFiringAngle(0));
			break;
		default:
			// Does Nothing
			break;
		}
	}

	public static int getPosition()
	{
		int defensePosition = 0;
		for (int i = 0; i < RobotMap.DefensePositionSwiches.length; i++)
		{
			if (RobotMap.DefensePositionSwiches[i].get())
			{
				defensePosition += Math.pow(2, i);
			}
		}
		defensePosition++;
		return defensePosition;
	}

	public static double DistanceToCenter()
	{
		int position = getPosition();

		return 65.49 * (147 - (50 * position));
	}

	public static double getAngleToFaceGoal()
	{
		double Distance = DistanceToCenter();
		double angle = Math.atan2(Distance, 8776);

		return (88.36 * 65.49 * (angle / 360));
	}

	public static double getFiringAngle(double xStart, double zStart, double xEnd, double zEnd,
			double shootingWheelRadius, double rpm)
	{
		double speed = 2 * Math.PI * shootingWheelRadius * (rpm / 60);

		return Math.atan((xEnd - xStart) + Math
				.sqrt(Math.pow((xEnd - xStart), 2)
						- (4 * .5 * 386.22 * ((-xStart + xEnd) / speed) * (-zStart - zEnd + ((xStart - xEnd) / speed))))
				/ (2 * (.5 * 386.22 * (-xStart + xEnd) / speed)));
	}
}
