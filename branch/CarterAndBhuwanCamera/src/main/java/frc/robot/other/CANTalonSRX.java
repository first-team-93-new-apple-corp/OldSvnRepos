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
    double m_voltageLimiter;
    boolean isVoltageLimited = false;
    public TalonSRX motor;
    boolean isInverted;
<<<<<<< .mine
    double voltagePercentage;

||||||| .r122

=======
/**
 * Basic wrapper for talons
 * @param port
 * the virtual can port that the motor is attached with(use phoenix tuner to find this)
 */
>>>>>>> .r131
    public CANTalonSRX(int port, double voltageLimiter){
        motor = new TalonSRX(port);
        isInverted = false;
        m_voltageLimiter = voltageLimiter;
        voltagePercentage = voltageLimiter/12;
    }

    public CANTalonSRX(int port)
    {
        motor = new TalonSRX(port);
        isInverted = false;
    }

    public void setVoltageLimiter(double voltageLimiter)
    {
        m_voltageLimiter = voltageLimiter;
        voltagePercentage = voltageLimiter/12;
    }

    public void setIsVoltageLimited(boolean limitVoltage)
    {
        isVoltageLimited = limitVoltage;
    }

    public double getVoltageLimiter()
    {
        return m_voltageLimiter;
    }

    public boolean getIsVoltageLimited()
    {
        return isVoltageLimited;
    }

    public void set(double value)
    {
        if(getIsVoltageLimited())
        {
            value *= voltagePercentage;
        }
        if(!isInverted)
        {
                motor.set(ControlMode.PercentOutput, value);
        }
        else
        {
                motor.set(ControlMode.PercentOutput, -value);
            
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
