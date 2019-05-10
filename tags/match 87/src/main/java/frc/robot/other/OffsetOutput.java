package frc.robot.other;

import edu.wpi.first.wpilibj.PIDOutput;

public class OffsetOutput implements PIDOutput
{
    double m_offset;
    PIDOutput m_output;
    /**
     * A wrapper for PIDOutputs that allows for them to be offset by a constant
     * @param output
     * The PIDOutput that is being written to
     * @param offset
     * The offset that is being added to the output
     */
    public OffsetOutput(PIDOutput output, double offset)
    {
        m_offset = offset;
        m_output = output;
    }

    @Override
    public void pidWrite(double output) {
        m_output.pidWrite(output + m_offset);
    }

}