package org.usfirst.frc93.Team93Robot2015;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonomousSelection extends CommandGroup {
    // final int ninety = 90;
    // final int one_eighty = 180;
    //
    // public AutonomousSelection() {
    // loadAutonomous();
    // }
    //
    // private void loadInitCommands() {
    // }
    //
    // //
    // ----DRIVE--COMMANDS-----------------------------------------------------------------------------
    // private void DriveForwards(int driveDistance) {
    // addSequential(new DriveForward(driveDistance)); // Need import for
    // }
    //
    // private void DriveReverse(int driveDistance) {
    // addSequential(new DriveBackward(driveDistance)); // Need import for
    // }
    //
    // private void TurnRight(int degrees) {
    // System.out.println("Turning 90degrees to the Right.");
    // addSequential(new TurnRight(degrees));
    // }
    //
    // private void TurnLeft(int degrees) {
    // System.out.println("Turning 90degrees to the Left.");
    // addSequential(new TurnLeft(degrees));
    // }
    //
    // //
    // ----CLAW--COMMANDS------------------------------------------------------------------------------
    // private void Claw(boolean state) {
    // addSequential(new CanGrabber(state));
    // }
    //
    // private void LiftClaw() {
    // // Lifts totes to a level where they can be set atop another tote.
    // System.out.println("Lifting Claw.");
    // // Need Commands/values.
    // }
    //
    // private void LowerClaw() {
    // // Lowers claw after dropping totes onto the stack.
    // System.out.println("Lowering Claw.");
    // // Need Commands/values.
    // }
    //
    // //
    // ----BIN--SNATCHER--COMMANDS---------------------------------------------------------------------
    // private void DeploySnatcher(boolean state) {
    // // addParallel(new DeploySnatcher);
    // addParallel(new BinSnatcherDeploy(state));
    // }
    //
    // private void SnatchBin()
    // {
    // appParallel(new SnatchBin);
    // }
    //
    // //
    // ----ACTUAL--AUTONOMOUS--MODELS------------------------------------------------------------------
    // private void StackThreeTotes()// VALUES WILL NEED TO BE ADJUSTED
    // {
    // DriveForwards();
    // Claw(true);
    // LiftClaw();
    // DriveForwards(693);
    // Claw(false);
    // LowerClaw();
    // Claw(true);
    // LiftClaw();
    // DriveForwards(693);
    // Claw(false);
    // LowerClaw();
    // Claw(true);
    // TurnRight(ninety);
    // DriveForwards();
    // Claw(false);
    // DriveReverse();
    // }
    //
    // private void SnatchBins() {
    // // DriveForwards();
    // DeploySnatcher(true);
    // // TurnRight();
    // SnatchBin();
    // DriveReverse();
    // SnatchBin();
    // DriveReverse();
    // SnatchBin();
    // DriveReverse();
    // SnatchBin();
    // }
    //
    // private void StackBinToteMove() {
    // int startSelect = AutonomousSelector.getStartSelect();
    // DriveForwards();
    // Claw(true);
    // LiftClaw();
    // DriveForwards();
    // Claw(false);
    // LowerClaw();
    // Claw(true);
    // TurnLeft(64);
    // if (startSelect == 1) {
    // LiftClaw();
    // }
    // DriveForward();
    // if (startSelect == 1) {
    // LowerClaw();
    // }
    // Claw(false);
    // DriveReverse();
    // }
    //
    // private void loadAutonomous() {
    // int planNumber = AutonomousSelector.getAutoSelect();
    // int startSelect = AutonomousSelector.getStartSelect();
    // loadInitCommands();
    // switch (planNumber) {
    // case (0):
    // default:
    // DriveForwards();
    // break;
    // case (1):
    // SnatchBins();
    // break;
    // case (2):
    // StackThreeTotes();
    // break;
    // case (3):
    // StackBinToteMove();
    // break;
    // }
    // }
}
