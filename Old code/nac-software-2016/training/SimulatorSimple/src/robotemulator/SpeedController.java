package robotemulator;


/**
 * A Victor speed controller emulation for FRC.
 * @author Nick DiRienzo, Patrick Jameson
 * @version 11.12.2010.3
 */
public interface SpeedController {

	/**
     * Sets the value of the Victor using a value between -1.0 and +1.0.
     * @param speed The speed value of the Victor between -1.0 and +1.0.
     */
    public void set(double speed);

    /**
     * Gets the most recent value of the Victor.
     * @return The most recent value of the Victor from -1.0 and +1.0.
     */
    public double get();
    
    //add pidWrite method?
    
}