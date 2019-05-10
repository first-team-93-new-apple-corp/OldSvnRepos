/*
 * LEL NO INAPPROPRIATE COMMENTS BECAUSE BLAH BLAH BLAH NO REASON LEL
 */
package org.usfirst.frc93.FinalRobot;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author NAC Controls
 */
public class CoordinatedPIDProfiler {
    /*
     * This class must be updated every time it is used in the execute loop because
     * it performs its own calculations externally of the two pid controllers
     * it is running
     */
    private int stepSize =200;
    private PIDController pid1 = null;
    private PIDController pid2 = null;
    private int setpoint = 0;
    private Encoder pid1Enc;
    private Encoder pid2Enc;
    public CoordinatedPIDProfiler(int stepSize, PIDController pid1, PIDController pid2, int setpoint,Encoder l1, Encoder l2){
        this.stepSize= stepSize;
        this.pid1 = pid1;
        this.pid2 = pid2;
        pid1.reset();
        pid2.reset();
        this.pid1Enc = l1;
        this.pid2Enc = l2;
        setSetpoint(setpoint);
    }
    public void setSetpoint(int setpoint){       
        this.setpoint  = setpoint;
        updateCurSetpoint();
    }
    public void updateCurSetpoint(){
        double sp1 = setpoint - pid1Enc.pidGet();  //delta dist 
        double sp2 = setpoint - pid2Enc.pidGet();
        double relStepSize1 = (Math.abs(sp1)>stepSize) ? stepSize : Math.abs(sp1);
        double relStepSize2 = (Math.abs(sp2)>stepSize) ? stepSize : Math.abs(sp2);
        pid1.setSetpoint(pid1Enc.pidGet() + ((sp1>0 ) ? + relStepSize1 : -relStepSize1));
        pid2.setSetpoint(pid2Enc.pidGet() + ((sp2>0 ) ? +relStepSize2 : -relStepSize2));
    }
    /*
     * Call this every 50 ms
     */
    private PIDCounter p1 = new PIDCounter();
    private PIDCounter p2 = new PIDCounter();
    public boolean calculate(){
        //SmartDashboard.putBoolean("Pid1 on target? ", pid1.onTarget());
        //SmartDashboard.putBoolean("Pid2 on target? ", pid2.onTarget());
        //SmartDashboard.putNumber("Setpoint ", setpoint);
        //SmartDashboard.putNumber("Encoder 1", pid1Enc.pidGet());
        //SmartDashboard.putNumber("Encoder 2", pid2Enc.pidGet());
        //SmartDashboard.putData("pid1", pid1);
        //SmartDashboard.putData("pid2", pid2);
        //SmartDashboard.putNumber("Victor1 ", pid1.get());
        //SmartDashboard.putNumber("Victor2 ", pid2.get());
        SmartDashboard.putString("Left Side","Encoder left reads "+RobotMap.climberLeftEnc.getDistance()+" Victor left reads "+RobotMap.climberLeftMotor.get());
        SmartDashboard.putString("Right Side","Encoder right reads "+RobotMap.climberRightEnc.getDistance()+" Victor left reads "+RobotMap.climberRightMotor.get());
        boolean pid1Done = false; /*pid1.onTarget()*/ 
        boolean pid2Done = false; /*pid2.onTarget()*/ 
        
        if(pid1.onTarget()){    //Change this back to simply gone past its setpoint
            pid1Done = true;
        }
        else{
            p1.reset();
        }
        
        if(pid2.onTarget()){    //Change this back to simply gone past its setpoint
            pid2Done = true;
        }
        else{
            p2.reset();
        }
        
        if(pid1Done){
            pid1.disable();
        }
        if(pid2Done){
            pid2.disable();
        }
        
        if(pid1Done && pid2Done){
            p1.reset();
            p2.reset();
            pid1.reset();
            pid2.reset();
            if(!(pid1.getSetpoint() == setpoint && pid2.getSetpoint() == setpoint)){    //Won't work with overshooting , but theres no way we're going to overshoot that hard
                updateCurSetpoint();
                pid1.enable();
                pid2.enable();
            }
            else{
                System.out.println("Reached our target");
                return true;
            }
        }
        return false;
    }
    public void reset(){
        setpoint = 0;
        pid1.reset();
        pid2.reset();
    }
    public void disable(){
        pid1.disable();
        pid2.disable();
    }
    public void enable(){
        pid1.enable();
        pid2.enable();
    }
}
