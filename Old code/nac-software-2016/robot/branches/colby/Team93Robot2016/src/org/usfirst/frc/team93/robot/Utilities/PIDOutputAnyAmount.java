package org.usfirst.frc.team93.robot.Utilities;

import edu.wpi.first.wpilibj.PIDOutput;

import java.util.ArrayList;

/**
 * This writes to any amount of pidControllers
 */
public class PIDOutputAnyAmount implements PIDOutput 
{

    ArrayList<PairPIDOutputGain> m_pidOutputList = new ArrayList<PairPIDOutputGain>();

    private PIDOutput m_pidController;
    private double m_gain;

    class PairPIDOutputGain 
    {
        public PIDOutput m_pidOutput;
        public double m_gain;

        /**
         * 
         * @param pidOutput
         *            This is the type of pidOutput you want
         * @param gain
         *            This is the value that you want the speed to be multiplied
         *            by in order for the pidWrite
         */
        public PairPIDOutputGain(PIDOutput pidOutput, double gain) 
        {
            // stuff here too
            m_pidOutput = pidOutput;
            m_gain = gain;
        }

        /**
         * This is sets the pidOutput
         * 
         * @param pidOutput
         *            This is they type of the PIDOutput we wish to use
         */
        public void setPID(PIDOutput pidOutput) 
        {
            m_pidOutput = pidOutput;
        }

        /**
         * 
         * @param gain
         *            his is the value that you want the speed to be multiplied
         *            by in order for the pidWrite
         */
        public void setGain(double gain) 
        {
            m_gain = gain;
        }

        /**
         * Sets how fast you want the PID to go.
         * 
         * @param speed
         *            How fast do you want to go
         */
        public void set(double speed) 
        {
            m_pidOutput.pidWrite(speed * m_gain);
        }

        /**
         * 
         * @param output
         *            what do you want to set they pids to
         */
        public void pidWrite(double output) 
        {
            set(output);
        }

        /**
         * Disables the PIDs
         */
        public void disable() 
        {
            set(0.0);
        }

        /**
         * 
         * @return the gain for the pids
         */
        public double getGainValue() 
        {
            return m_gain;
        }

        /**
         * 
         * @return returns they pidOutput type
         */
        public PIDOutput getOutputType() 
        {
            return m_pidOutput;
        }
    }

    // Initialize your subsystem here
    /**
     * 
     * @param pidController
     *            this is the pidController you want to use
     * @param gain
     *            this is the gain you want to use
     */
    public PIDOutputAnyAmount(PIDOutput pidController, double gain) 
    {
        m_pidController = pidController;
        m_gain = gain;
    }

    /**
     * 
     * @see edu.wpi.first.wpilibj.PIDOutput#pidWrite(double)
     */
    @Override
    public void pidWrite(double output) 
    {
        // TODO Auto-generated method stub
    }

    /**
     * This adds a pidList to the pid arrayList
     * 
     * @param outputList
     *            they arrayList you want to write to
     */
    public void addPIDOutputToList(PairPIDOutputGain outputList) 
    {
        m_pidOutputList.add(outputList);
    }

    // Will need to access, but not here
    /**
     * This access the data in the array list
     */
    public void getPIDOutput() 
    {
        for (int i = 0; i > m_pidOutputList.size(); i++) 
        {
            m_pidOutputList.get(i).getGainValue();
            m_pidOutputList.get(i).getOutputType();
        }
    }
}
