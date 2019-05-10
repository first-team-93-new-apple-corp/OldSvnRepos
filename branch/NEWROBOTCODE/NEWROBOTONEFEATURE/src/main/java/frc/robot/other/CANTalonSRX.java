/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.other;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.SpeedController;


public class CANTalonSRX implements SpeedController
{ 
    TalonSRX motor;
    boolean isInverted;
/**
 * Basic wrapper for talons
 * @param port
 * the virtual can port that the motor is attached with(use phoenix tuner to find this)
 */
    public CANTalonSRX(int port)
    {
        motor = new TalonSRX(port);
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

    public void setInverted(boolean setValue)
    {
        isInverted = setValue;
    }

    public void setInverted()
    {
        isInverted = !isInverted;
    }

    public boolean getInverted()
    {
        return isInverted;
    }

    public void stopMotor()
    {
        set(0);
    }
}
