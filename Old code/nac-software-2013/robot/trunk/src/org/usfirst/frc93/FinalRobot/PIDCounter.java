/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc93.FinalRobot;

/**
 *
 * @author NAC Controls
 */
public class PIDCounter {
    int counter = 0;
    int lot = 15;
    public PIDCounter(){
        
    }
    public PIDCounter(int lot){
        this.lot = lot;
    }
    public boolean countOnTarget(){
        counter ++;
        return counter>lot;
    }
    public void reset(){
        counter = 0;
    }
}
