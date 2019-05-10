
/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

// Modified for the VEX speed controller by Team 93;

//package Shooter;
package org.usfirst.frc93.FinalRobot;

import edu.wpi.first.wpilibj.SafePWM;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.parsing.IDeviceController;

/**
 * IFI Victor Speed Controller
 */
public class VEXSpeedController extends Victor implements SpeedController, IDeviceController {

    /**
     * Common initialization code called by all constructors.
     *
     * Note that the Victor uses the following bounds for PWM values.  These values were determined
     * empirically through experimentation during the 2008 beta testing of the new control system.
     * Testing during the beta period revealed a significant amount of variation between Victors.
     * The values below are chosen to ensure that teams using the default values should be able to
     * get "full power" with the maximum and minimum values.  For better performance, teams may wish
     * to measure these values on their own Victors and set the bounds to the particular values
     * measured for the actual Victors they were be using.
     *   - 210 = full "forward"
     *   - 138 = the "high end" of the dead band range
     *   - 132 = center of the dead band range (off)
     *   - 126 = the "low end" of the dead band range
     *   - 56 = full "reverse"
     */
    
    final static int CENTER_PWM = 128;  //128
    final static int LOW_PWM = 1;
    final static int HIGH_PWM = 252;  // avoid 255 //254
    
    private void initVictor() 
    {
        // public void setBounds(final int max, final int deadbandMax, final int center, final int deadbandMin, final int min) {
        setBounds(HIGH_PWM, CENTER_PWM+1,CENTER_PWM, CENTER_PWM-1, LOW_PWM);
        setPeriodMultiplier(PeriodMultiplier.k2X);
        setRaw(CENTER_PWM);
    }
    

    /**
     * Constructor that assumes the default digital module.
     *
     * @param channel The PWM channel on the digital module that the Victor is attached to.
     */
    public VEXSpeedController(final int channel) {
        super(channel);
        initVictor();
    }

    /**
     * Constructor that specifies the digital module.
     *
     * @param slot The slot in the chassis that the digital module is plugged into.
     * @param channel The PWM channel on the digital module that the Victor is attached to.
     */
    public VEXSpeedController(final int slot, final int channel) {
        super(slot, channel);
        initVictor();
    }

}

