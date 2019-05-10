/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc93.FinalRobot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * Should work for everything
 */
public class OffsetJoystick extends Joystick {
    //Assumes 4 joystick axes as the max
    double [] axisOffsets = new double [6]; //SUBTRACT these vals (DONT ADD THEM)
    public OffsetJoystick(int port){
        super(port);
        for(int i = 0; i< axisOffsets.length; i++){
            axisOffsets[i] = super.getRawAxis(i);
        }
        
    }
    private String name = "GIVE ME A NAME";
    public OffsetJoystick(int port, String name){
        this(port);
        this.name = name;
        SmartDashboard.putString("joystick"+port, name + " is on port # "+port);
    }
    public double getRawAxis(int axisNum){
        return super.getRawAxis(axisNum) - axisOffsets[axisNum];
    }
}
