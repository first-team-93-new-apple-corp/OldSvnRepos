/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.other;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.SpeedController;


public class CANVictorSPX implements SpeedController
{ 
    VictorSPX motor;
    boolean isInverted;

    public CANVictorSPX(int port)
    {
        motor = new VictorSPX(port);
        isInverted = false;
    }
    public void set(double value)
    {
        if(!isInverted)
        {
            motor.set(ControlMode.PercentOutput, value);
        }
        else
        {
            motor.set(ControlMode.PercentOutput, value * -1);
        }
    }

    public void pidWrite(double value)
    {
        set(value);
    }

    public void disable()
    {
        set(0);
    }

    public double get()
    {
        return motor.getMotorOutputPercent();
    }

    //setValue is not used
    public void setInverted(boolean setValue)
    {
        isInverted = !isInverted;
    }

    public boolean getInverted()
    {
        return false;
    }

    public void stopMotor()
    {
        set(0);
    }
}
