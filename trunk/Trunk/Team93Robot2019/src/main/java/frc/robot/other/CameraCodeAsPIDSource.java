package frc.robot.other;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import frc.robot.subsystems.Limelight;

public class CameraCodeAsPIDSource
{
    public static CameraXSource cameraXSource;
    public static CameraYSource cameraYSource;
    /**
     * Allows use of distance from camera for pid sources
     */
    public CameraCodeAsPIDSource()
    {
        cameraXSource = new CameraXSource();
        cameraYSource = new CameraYSource();
    }
    public static class CameraXSource implements PIDSource {

        @Override
        public void setPIDSourceType(PIDSourceType pidSource) {
        }

        @Override
        public PIDSourceType getPIDSourceType() {
            return PIDSourceType.kDisplacement;
        }

        @Override
        public double pidGet() {
        NetworkTableEntry ta = Limelight.table.getEntry("ta");
		double a = ta.getDouble(0.0);
		NetworkTableEntry tx = Limelight.table.getEntry("tx");
        double x = tx.getDouble(0.0);
        double sendableValue;
        if(a == 0)
        {
            sendableValue =  0;
        }
        else
        {
            sendableValue = (87.084*Math.pow(a, -.573) * Math.tan(Math.toRadians(x)));
        }

        if(sendableValue == 0)
        {
            return 0;
        }
        else
        {
            return sendableValue + 3.26;
        }
        
		}

    }
    public static class CameraYSource implements PIDSource
    {

        @Override
        public void setPIDSourceType(PIDSourceType pidSource) {

        }

        @Override
        public PIDSourceType getPIDSourceType() {
            return PIDSourceType.kDisplacement;
        }

        @Override
        public double pidGet() {
            NetworkTableEntry ta = Limelight.table.getEntry("ta");
            double a = ta.getDouble(0.0);
            if(a == 0)
            {
                return 0;
            }
            else
            {
                return(87.084*Math.pow(a, -.573));
            }
        }
    }
}