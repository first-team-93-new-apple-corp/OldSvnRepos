package frc.robot.other;

import edu.wpi.first.wpilibj.PIDOutput;
import frc.robot.OI;
import frc.robot.subsystems.Endgame;
import frc.robot.subsystems.Manipulator;

public class EndgamePIDOutput implements PIDOutput
{

    @Override
    public void pidWrite(double output) {
        if(output < 0 && Manipulator.habLiftEncoder.get() >= 5)
        {
            Endgame.habLift.set(0);
            
        }
        else{
        Endgame.habLift.set(output - (OI.getOperatorStick() * 1.25));
    }
    }

}