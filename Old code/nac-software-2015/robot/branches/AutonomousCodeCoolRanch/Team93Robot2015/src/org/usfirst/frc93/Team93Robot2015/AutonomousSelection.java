package org.usfirst.frc93.Team93Robot2015;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc93.Team93Robot2015.commands.CanGrabber;
import org.usfirst.frc93.Team93Robot2015.commands.DriveBackward;
import org.usfirst.frc93.Team93Robot2015.commands.DriveForward;
import org.usfirst.frc93.Team93Robot2015.commands.ExtendDrawbridge;
import org.usfirst.frc93.Team93Robot2015.commands.MoveElevator;
import org.usfirst.frc93.Team93Robot2015.commands.RetractDrawbridge;
import org.usfirst.frc93.Team93Robot2015.commands.TurnLeft;
import org.usfirst.frc93.Team93Robot2015.commands.TurnRight;
import org.usfirst.frc93.Team93Robot2015.subsystems.AutonomousSelector;
import org.usfirst.frc93.Team93Robot2015.subsystems.AutonomousSelector.EAutoSelect;

public class AutonomousSelection extends CommandGroup {
    final int NINETY = 262;
    final int ONE_EIGHTY = 525;
    final int MAX_ERROR_ONE_INCH = 13;
    final int LENGTH_OF_TOTE = 367;
    final int DISTANCE_FROM_TOTE_TO_TOTE = 1060;
    final int DISTANCE_FROM_TOTE_TO_AUTO = 1454;
    final int DISTANCE_BETWEEN_ADJACENT_BINS = 693;
    final int DISTANCE_BETWEEN_MIDDLE_BINS = 1182;
    final int DISTANCE_FROM_BEHIND_TOTE_TO_AUTO = 1684;
    final int LENGTH_OF_BIN = 245;
    final int HEIGHT_OF_SCORING_PLATFORM = 48;
    final int DISTANCE_FROM_LANDFILL_TO_AUTO = 1019;
    // Below numbers may need to be adjusted
    final int HEIGHT_OF_TOTE = 185;
    final int GROUND_LEVEL = 0;
    final int ONE_INCH_OF_ELEVATOR = 420;

    public AutonomousSelection() {
        loadAutonomous();
    }

    private void loadInitCommands() {
    }

    // ----DRIVE--COMMANDS-----------------------------------------------------------------------------
    private void DriveForwards(int driveDistance, double driveError) {
        addSequential(new DriveForward(driveDistance, driveError));
    }

    private void DriveReverse(int driveDistance, double driveError) {
        addSequential(new DriveBackward(driveDistance, driveError));
    }

    private void TurnRight(int turnDistance) {
        System.out.println("Turning 90degrees to the Right.");
        addSequential(new TurnRight(turnDistance, MAX_ERROR_ONE_INCH));
    }

    private void TurnLeft(int turnDistance) {
        System.out.println("Turning 90degrees to the Left.");
        addSequential(new TurnLeft(turnDistance, MAX_ERROR_ONE_INCH));
    }

    // ----CLAW--COMMANDS------------------------------------------------------------------------------
    private void Claw(boolean state) {
        // Need Commands/values.
        addSequential(new CanGrabber(state));
    }

    private void LiftClaw(int raiseDistance, double raiseError) {
        // Lifts totes to a level where they can be set atop another tote.
        System.out.println("Lifting Claw.");
        addSequential(new MoveElevator(raiseDistance, raiseError));
        // Need Commands/values.
    }

    private void LowerClaw(int lowerDistance, double lowerError) {
        // Lowers claw after dropping totes onto the stack.
        System.out.println("Lowering Claw.");
        addSequential(new MoveElevator(lowerDistance, lowerError));
        // Need Commands/values.
    }

    // ----BIN--SNATCHER--COMMANDS---------------------------------------------------------------------
    private void DeploySnatcher(boolean state) {
        if (state) {
            addSequential(new ExtendDrawbridge());
        }
        else {
            addSequential(new RetractDrawbridge());
        }
        // addSequential(new PneumaticDrawbridgeControl(state));
    }

    private void SnatchBin() {
        // addSequential(new BinSnatch());
    }

    // ----ACTUAL--AUTONOMOUS--MODELS------------------------------------------------------------------
    private void StackThreeTotes()// VALUES WILL NEED TO BE ADJUSTED
    {
        DriveForwards(LENGTH_OF_TOTE, MAX_ERROR_ONE_INCH);
        Claw(true);
        LiftClaw(HEIGHT_OF_TOTE, ONE_INCH_OF_ELEVATOR);
        DriveForwards(DISTANCE_FROM_TOTE_TO_TOTE, MAX_ERROR_ONE_INCH);
        Claw(false);
        LowerClaw(GROUND_LEVEL, ONE_INCH_OF_ELEVATOR);
        Claw(true);
        LiftClaw(HEIGHT_OF_TOTE, ONE_INCH_OF_ELEVATOR);
        DriveForwards(DISTANCE_FROM_TOTE_TO_TOTE, MAX_ERROR_ONE_INCH);
        Claw(false);
        LowerClaw(GROUND_LEVEL, ONE_INCH_OF_ELEVATOR);
        Claw(true);
        TurnRight(NINETY);
        DriveForwards(DISTANCE_FROM_TOTE_TO_AUTO, MAX_ERROR_ONE_INCH);
        Claw(false);
        DriveReverse(LENGTH_OF_TOTE, MAX_ERROR_ONE_INCH);
    }

    private void SnatchBins() {
        DeploySnatcher(true);
        SnatchBin();
        DriveForwards(DISTANCE_BETWEEN_ADJACENT_BINS, MAX_ERROR_ONE_INCH);
        SnatchBin();
        DriveForwards(DISTANCE_BETWEEN_MIDDLE_BINS, MAX_ERROR_ONE_INCH);
        SnatchBin();
        DriveForwards(DISTANCE_BETWEEN_ADJACENT_BINS, MAX_ERROR_ONE_INCH);
        SnatchBin();
        DeploySnatcher(false);
        TurnLeft(NINETY);
        DriveForwards(DISTANCE_FROM_LANDFILL_TO_AUTO, MAX_ERROR_ONE_INCH);
    }

    private void StackBinToteMove() {
        int startSelect = AutonomousSelector.getStartSelect();
        DriveForwards(LENGTH_OF_BIN, MAX_ERROR_ONE_INCH);
        Claw(true);
        LiftClaw(HEIGHT_OF_TOTE, ONE_INCH_OF_ELEVATOR);
        DriveForwards(LENGTH_OF_BIN, MAX_ERROR_ONE_INCH);
        Claw(false);
        LowerClaw(GROUND_LEVEL, ONE_INCH_OF_ELEVATOR);
        Claw(true);
        TurnLeft(NINETY);
        if (startSelect == 1) {
            LiftClaw(HEIGHT_OF_SCORING_PLATFORM, ONE_INCH_OF_ELEVATOR);
        }
        DriveForwards(DISTANCE_FROM_TOTE_TO_AUTO, MAX_ERROR_ONE_INCH);
        if (startSelect == 1) {
            LowerClaw(GROUND_LEVEL, ONE_INCH_OF_ELEVATOR);
        }
        Claw(false);
        DriveReverse(LENGTH_OF_TOTE, MAX_ERROR_ONE_INCH);
    }

    // ----SELCT--AUTONOMOUS--TO--RUN------------------------------------------------------------------
    private void loadAutonomous() {
        EAutoSelect autoSelect = AutonomousSelector.getAutoSelect();
        loadInitCommands();
        switch (autoSelect) {
        case ePushToteToAuto:
        default:
            DriveForwards(DISTANCE_FROM_BEHIND_TOTE_TO_AUTO, MAX_ERROR_ONE_INCH);
            break;
        case eStackThreeTotes:
            StackThreeTotes();
            break;
        case eBinSnatcher:
            SnatchBins();
            break;
        case eStackToteBinToAuto:
            StackBinToteMove();
            break;
        }
    }
}