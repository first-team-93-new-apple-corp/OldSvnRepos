package org.usfirst.frc.team93.robot.utilities;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;

/**
 * SPIEncoderPIDSource
 * @author Evans Chen
 * Converts a SPI Encoder into a PIDSource.
 */

public class SPIEncoderPIDSource implements PIDSource
{
	
	//command bytes
	byte waitByte = (byte)0xA5;
	byte readByte = (byte)0x10;
	byte noopByte = (byte)0x00;
	
	//sendable command byte arrays
	byte[] wait;
	byte[] read;
	byte[] noop;
    
	//the attached instance of the SPI.
	SPI m_spi;
	
	//offset
	int m_offset;
	//gain
	double m_gain;
	
	//last value
	Integer m_last;
	
	final int ticksPerRotation = 4096;
	
	//tracks number of rotations
	int m_rotations = 0;
	
	//compensation for SPI Encoder placement.
	double m_compensation = 0;
	
	public SPIEncoderPIDSource(SPI spi)
	{
    	m_spi = spi;
    	m_offset = 0;
    	m_gain = 1.0;
    	
    	//configures SPI Encoder
    	m_spi.setMSBFirst();
    	m_spi.setClockActiveHigh();
    	m_spi.setSampleDataOnRising();
		m_spi.setClockRate(12000);
		
		//initializes sendable byte arrays
		wait = new byte[]{waitByte};
		read = new byte[]{readByte};
		noop = new byte[]{noopByte};
	}
	
	/**
	 * Returns the attached instance of the SPI.
	 * @return
	 */
	public SPI getSPI()
	{
		return m_spi;
	}

	/**
	 * SPI Encoder only gives displacement. Cannot set PIDSourceType.
	 */
	@Override
	public void setPIDSourceType(PIDSourceType pidSource)
	{
	}
	
	/**
	 * SPI Encoder only gives displacement.
	 */
	@Override
	public PIDSourceType getPIDSourceType()
	{
		return PIDSourceType.kDisplacement;
	}
	
	/**
	 * Gets the position of the SPI Encoder.
	 */
	@Override
	public double pidGet()
	{
		//get value from encoder
		Integer readValue = readInt();
		if ((readValue == null) && (m_last != null))
		{
			readValue = m_last;
		}
		
		//calculate rotations
		//2048 ticks = 180 degrees
		//4096 ticks = 360 degrees
		
		//if the encoder jumps +180 degrees in one tick,
		//we know that it rotated around, so add one rotation
		
		//if the encoder jumps -180 degrees in one tick
		//we know that it rotated around, so subtract one rotation
		
		//make sure last is not null
		int delta = 0;
		if (m_last != null)
		{
			delta = m_last - readValue;
		}
		
		
		if (delta > 2048)
		{
			m_rotations = m_rotations + 1;
		}
		if (delta < -2048)
		{
			m_rotations = m_rotations - 1;
		}
		
		//return encoder value
		m_last = readValue;
		return ((readValue + m_offset + (m_rotations * ticksPerRotation)) * m_gain) - m_compensation;
	}
	
	/**
	 * Gets the position of the SPI Encoder.
	 * @return The SPI Encoder position.
	 */
	public double get()
	{
		return pidGet();
	}
	
	/**
	 * Returns the multiplier to convert ticks to rotations.
	 * @return The multiplier
	 */
	public double getGain()
	{
		return m_gain;
	}
	
	/**
	 * Sets the multiplier to convert ticks to rotations.
	 * @param gain
	 */
	public void setGain(double gain)
	{
		m_gain = gain;
	}
	
	/**
	 * Sets the SPI Encoder to a value;
	 * @param value
	 */
	public void set(double value)
	{
		if (m_gain != 0)
		{
			m_offset = -readInt() + (int)Math.round(value / getGain());
		}
	}
	
	/**
	 * Resets the SPI Encoder to 0.0
	 */
	public void reset()
	{
		set(0.0);
	}
	
	/**
	 * Sets the compensation of the SPI Encoder.
	 * Suppose the SPI Encoder is at 200 degrees at neutral position.
	 * Then, make compensation 200.
	 * @param compensation
	 */
	public void setCompensation(double compensation)
	{
		m_compensation = compensation;
	}
	
	/**
	 * Returns the compensation value subtracted off of the SPI Encoder.
	 * @return
	 */
	public double getCompensation()
	{
		return m_compensation;
	}
	
    /**
     * Reads an integer from the SPI Encoder.
     * @return The SPI Encoder's current location
     */
    private Integer readInt()
    {
	    List<Byte> bytes = read();
    	if (bytes == null)
    	{
    		return null;
    	}
    	return bytesToInt(byteListToArray(bytes));
    }
    
    /**
     * Converts a Byte list to a byte array.
     * @param byteList The List<Byte>.
     * @return The byte[].
     */
    private byte[] byteListToArray(List<Byte> byteList)
    {
    	Byte[] bytes = new Byte[byteList.size()];
    	byteList.toArray(bytes);
    	byte[] byteArray = new byte[byteList.size()];
    	int i = 0;
    	for(Byte b: bytes)
    	{
    		byteArray[i++] = b.byteValue();
    	}
    	return byteArray;
    }
    
    /**
     * Converts byte arrays to integers.
     * @param bytes The array to convert.
     * @return The integer value.
     */
    private int bytesToInt(byte[] bytes)
    {
    	return ByteBuffer.wrap(bytes).getInt();
    }
    
    /**
     * Reads from the SPI Encoder.
     * @return A list of the useful byte data received.
     */
    public synchronized List<Byte> read()
    {
    	/**
    	 * Process:
		 * 1. Send 0x10 (read)
		 * 2. Send 0x00 (no operation) and receive 0xA5 (wait) until 0x10 (read) is received
		 * 3. Record data
		 * 4. Keep recording until 0xA5 (no operation) is received
		 * 5. Repeat the process
    	 */
    	
    	//The array to store the data
    	List<Byte> data = new ArrayList<Byte>();
    	
    	//send 0x10 (read)
    	byte[] response;
    	response = transaction(read);
    	//wait until read is received
    	Timer timer = new Timer();
    	timer.reset();
    	timer.start();
    	while (!Arrays.equals(response, read))
    	{
    		//while the SPI is not ready,
    		//System.out.println("Waiting for read");
    		//send a request asking if the SPI is ready
    		response = transaction(noop);
    	}
    	//after read is received, collect data while there is data
    	//if we receive wait, then stop collecting data.
    	response = noop;
    	while (!(Arrays.equals(response, wait) || (data.size() >= 3)))
    	{
    		//while the SPI has more data to send,
    		//System.out.println("Waiting for data");
    		//save the current data
    		data.add(response[0]);
    		//send a request for more data from the SPI
    		response = transaction(noop);
    	}
    	if (!Arrays.equals(response, wait))
    	{
    		//System.out.println("SPI Read failed: Response was " + byteToHexString(response[0]));
    	}
    	
    	//remove status bit
    	data.remove(0);
    	
    	//pad data
    	data.add(0, (byte)0);
    	data.add(0, (byte)0);
    	 
    	//throw out bad data
    	if (data.size() != 4)
    	{
    		return null;
    	}
    	return data;
    }
    
    /**
     * Makes a transaction with the SPI encoder.
     * @param send The command to send.
     * @return The response received.
     */
    public byte[] transaction(byte[] send)
    {
    	byte[] response = new byte[send.length];
    	m_spi.transaction(send, response, send.length);
    	return response;
    }
    
    /**
     * Converts a byte to its bit representation
     * @param b The byte to convert
     * @return The bit string.
     */
    private String byteToBitString(byte b)
    {
    	String bits = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
    	return bits;
    }
    
    /**
     * Converts a List of Bytes to a list of its string representation.
     * This will output something in the format "[10101011, 00010010, 01001001, ...]"
     * The list {3, 6, 8} would output:
     * "[00000011, 00000110, 00001000]"
     * @param bytes The List<Byte> byte list.
     * @return The string representation.
     */
    private String byteListToString(List<Byte> bytes)
    {
    	String s = "[";
    	for (int i = 0; i < bytes.size(); i++)
    	{
    		if (i != 0)
    		{
    			s = s + ", ";
    		}
    		s = s + byteToBitString(bytes.get(i));
    	}
    	s = s + "]";
    	return s;
    }
    
    private String byteToHexString(byte b)
    {
    	return String.format("%02X ", b);
    }
}