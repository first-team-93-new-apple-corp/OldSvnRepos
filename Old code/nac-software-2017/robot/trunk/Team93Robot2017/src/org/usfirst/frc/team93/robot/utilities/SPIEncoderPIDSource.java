package org.usfirst.frc.team93.robot.utilities;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SPI;

/**
 * SPIEncoderPIDSource
 * 
 * @author Evans Chen Converts a SPI Encoder into a PIDSource.
 */

public class SPIEncoderPIDSource implements WheelAnglePIDSource
{
	
	// command bytes
	byte waitByte = (byte) 0xA5;
	byte readByte = (byte) 0x10;
	byte noopByte = (byte) 0x00;
	
	// sendable command byte arrays
	byte[] wait;
	byte[] read;
	byte[] noop;
	
	// the attached instance of the SPI.
	SPI m_spi;
	
	// offset
	int m_offset;
	// gain
	double m_gain;
	
	// last value
	Integer m_last;
	
	final int ticksPerRotation = 4096;
	
	// tracks number of rotations
	int m_rotations = 0;
	
	// compensation for SPI Encoder placement.
	double m_compensation = 0;
	
	// the chip select
	DigitalOutput m_chipSelect;
	
	// enable prints or not
	public boolean m_enablePrint = false;
	
	public final int maxTicksPerUpdate = 600;
	
	// stores the number of consecutive misreads.
	public int m_misreads = 0;
	
	public boolean m_initialized = false;
	public boolean m_reset = false;
	
	public boolean m_printErrors = true;
	
	public SPIEncoderPIDSource(SPI spi, DigitalOutput chipSelect)
	{
		m_spi = spi;
		m_chipSelect = chipSelect;
		// write high by default
		m_chipSelect.set(true);
		
		m_offset = 0;
		m_gain = 1.0;
		
		// configures SPI Encoder
		m_spi.setMSBFirst();
		m_spi.setClockActiveHigh();
		m_spi.setSampleDataOnRising();
		m_spi.setClockRate(20000);
		
		// initializes sendable byte arrays
		wait = new byte[] { waitByte };
		read = new byte[] { readByte };
		noop = new byte[] { noopByte };
		
		m_last = 0;
		m_misreads = 0;
		
		m_initialized = false;
		m_reset = false;
	}
	
	/**
	 * Returns the attached instance of the SPI.
	 * 
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
		// get value from encoder
		Integer readValue = readInt();
		// m_last can't be null, ever.
		if (readValue == null)
		{
			m_misreads += 1;
			readValue = m_last;
		}
		
		// 4095 is the highest value the encoder can give.
		// if the reading is above 4095, then the value is a misread.
		// use the last known value instead.
		if (readValue >= 4096)
		{
			printError("SPI int read greater than 4095: " + readValue);
			m_misreads += 1;
			readValue = m_last;
		}
		// if the encoder has started resetting, but hasn't finished,
		// then the only check needed to see if it's valid is if it's under
		// 4096.
		else if (!m_reset)
		{
			m_misreads = 0;
		}
		
		// evaluate validity of read using distance from previous read.
		int m_dist = (int) Math.abs(closestAngle(readValue, m_last, ticksPerRotation));
		// only check for misreads after one valid reading is received.
		if (m_initialized)
		{
			// checks for distance from last reading.
			// if too large, then discard.
			if ((m_dist > (m_misreads + 1) * maxTicksPerUpdate))
			{
				m_misreads += 1;
				printError("Discarded distance of " + m_dist + " current: " + readValue + " last: " + m_last + " misreads: " + m_misreads);
				readValue = m_last;
			}
			// otherwise, reading is valid. Clear counter of consecutive
			// misreads.
			else
			{
				m_misreads = 0;
			}
		}
		else
		{
			// if read value gives data other than default 0, AND the resetting
			// is finished, then the encoder is fully initialized.
			if ((readValue != 0) && (m_reset))
			{
				m_initialized = true;
				m_last = readValue;
			}
		}
		
		// calculate rotations
		// 2048 ticks = 180 degrees
		// 4096 ticks = 360 degrees
		
		// if the encoder jumps +180 degrees in one tick,
		// we know that it rotated around, so add one rotation
		
		// if the encoder jumps -180 degrees in one tick
		// we know that it rotated around, so subtract one rotation
		
		// make sure last is not null
		int delta = 0;
		if (m_last != null)
		{
			delta = readValue - m_last;
		}
		
		// if the delta is positive, we rotated in the negative direction
		if (delta > 2048)
		{
			m_rotations = m_rotations - 1;
		}
		// if the delta is negative, we rotated in the positive direction
		if (delta < -2048)
		{
			m_rotations = m_rotations + 1;
		}
		
		// return encoder value
		m_last = readValue;
		print("READ FROM ENCODER: " + readValue);
		
		// compare current and previous
		double current = ((readValue + m_offset + (m_rotations * ticksPerRotation)) * m_gain) - m_compensation;
		return current;
	}
	
	/**
	 * Gets the position of the SPI Encoder.
	 * 
	 * @return The SPI Encoder position.
	 */
	@Override
	public double get()
	{
		return pidGet();
	}
	
	/**
	 * Returns the multiplier to convert ticks to rotations.
	 * 
	 * @return The multiplier
	 */
	public double getGain()
	{
		return m_gain;
	}
	
	/**
	 * Sets the multiplier to convert ticks to rotations.
	 * 
	 * @param gain
	 */
	@Override
	public void setGain(double gain)
	{
		m_gain = gain;
	}
	
	/**
	 * Sets the SPI Encoder to a value, in degrees.
	 * 
	 * @param value Degrees to set the encoder to.
	 */
	@Override
	public void set(double value)
	{
		m_rotations = 0;
		if (m_gain != 0)
		{
			m_offset = -m_last + (int) Math.round(value / getGain());
		}
	}
	
	/**
	 * Resets the SPI Encoder to 0.0
	 */
	@Override
	public void reset()
	{
		set(0.0);
		m_rotations = 0;
		m_reset = false;
	}
	
	/**
	 * Sets the compensation of the SPI Encoder. Suppose the SPI Encoder is at
	 * 200 degrees at neutral position. Then, make compensation 200.
	 * 
	 * @param compensation
	 */
	public void setCompensation(double compensation)
	{
		m_compensation = compensation;
	}
	
	/**
	 * Returns the compensation value subtracted off of the SPI Encoder.
	 * 
	 * @return
	 */
	public double getCompensation()
	{
		return m_compensation;
	}
	
	/**
	 * Reads an integer from the SPI Encoder.
	 * 
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
	 * 
	 * @param byteList The List<Byte>.
	 * @return The byte[].
	 */
	private byte[] byteListToArray(List<Byte> byteList)
	{
		Byte[] bytes = new Byte[byteList.size()];
		byteList.toArray(bytes);
		byte[] byteArray = new byte[byteList.size()];
		int i = 0;
		for (Byte b : bytes)
		{
			byteArray[i++] = b.byteValue();
		}
		return byteArray;
	}
	
	/**
	 * Converts byte arrays to integers.
	 * 
	 * @param bytes The array to convert.
	 * @return The integer value.
	 */
	private int bytesToInt(byte[] bytes)
	{
		return ByteBuffer.wrap(bytes).getInt();
	}
	
	/**
	 * Reads from the SPI Encoder.
	 * 
	 * @return A list of the useful byte data received.
	 */
	public synchronized List<Byte> read()
	{
		/**
		 * Process: 1. Send 0x10 (read) 2. Send 0x00 (no operation) and receive
		 * 0xA5 (wait) until 0x10 (read) is received 3. Record data 4. Keep
		 * recording until 0xA5 (no operation) is received 5. Repeat the process
		 */
		
		// The array to store the data
		List<Byte> data = new ArrayList<Byte>();
		
		// send 0x10 (read)
		byte[] response;
		response = transaction(read);
		
		print("Started read");
		
		// wait until read is received
		while (!Arrays.equals(response, read))
		{
			// while the SPI is not ready,
			
			// send a request asking if the SPI is ready
			// SHOULD receive A5 from the SPI
			response = transaction(noop);
			print("Waiting for read: " + byteToHexString(response[0]));
			
			// if did not receive WAIT or READ...
			// exit, as something went wrong.
			if (!Arrays.equals(response, wait) && !Arrays.equals(response, read))
			{
				printError("Transaction contained invalid data while waiting for SPI to be ready: " + byteToHexString(response[0]));
				return null;
			}
		}
		
		// after read is received, collect data while there is data
		// if we receive wait, then stop collecting data.
		response = noop;
		while (!Arrays.equals(response, wait) && (data.size() < 3))
		{
			// while the SPI has more data to send,
			// save the current data
			data.add(response[0]);
			// send a request for more data from the SPI
			response = transaction(noop);
			print("Getting data: " + byteToHexString(response[0]));
		}
		
		// remove the "noop" bit received
		data.remove(0);
		
		// pad data to 4 bytes
		data.add(0, (byte) 0);
		data.add(0, (byte) 0);
		
		// throw out misreads
		if (data.size() != 4)
		{
			printError("Misread SPI data, invalid number of bytes received: " + byteListToString(data));
			return null;
		}
		
		print("Successfully finished read: " + byteListToString(data));
		return data;
	}
	
	/**
	 * Makes a transaction with the SPI encoder.
	 * 
	 * @param send The command to send.
	 * @return The response received.
	 */
	public byte[] transaction(byte[] send)
	{
		byte[] response = new byte[send.length];
		
		// write high to chip select line
		m_chipSelect.set(false);
		
		// delay to make sure it was received
		// delay(5);
		
		m_spi.transaction(send, response, send.length);
		
		// release chip select line
		m_chipSelect.set(true);
		// delay(2);
		
		return response;
	}
	
	/**
	 * Converts a byte to its bit representation
	 * 
	 * @param b The byte to convert
	 * @return The bit string.
	 */
	private String byteToBitString(byte b)
	{
		String bits = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
		return bits;
	}
	
	/**
	 * Converts a List of Bytes to its string representation. This will output
	 * something in the format "[10101011, 00010010, 01001001, ...]" The list
	 * {3, 6, 8} would output: "[00000011, 00000110, 00001000]"
	 * 
	 * @param bytes The List<Byte> byte list.
	 * @return The string representation.
	 */
	private String byteListToString(List<Byte> bytes)
	{
		// make sure list is not null
		if (bytes == null)
		{
			return "null";
		}
		
		// convert list to string
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
	
	/**
	 * Converts a byte into a string hexadecimal representation of the byte. For
	 * example, 16 would become "10", and 255 would become "FF".
	 * 
	 * @param b The byte
	 * @return The string representation.
	 */
	private String byteToHexString(byte b)
	{
		return String.format("%02X ", b);
	}
	
	/**
	 * prints.
	 */
	private void print(Object o)
	{
		if (m_enablePrint)
		{
			System.out.println("" + o);
		}
	}
	
	/**
	 * Prints errors and misreads.
	 */
	private void printError(Object o)
	{
		if (m_printErrors)
		{
			System.out.println("" + o);
		}
	}
	
	/**
	 * Get the closest angle between the given angles.
	 * 
	 * @param a
	 * @param b
	 * @param period The number of ticks in one rotation.
	 * @return
	 */
	private double closestAngle(double a, double b, double period)
	{
		// get direction
		period = Math.abs(period);
		double dir = modulo(b, period) - modulo(a, period);
		
		// convert from -360 to 360 to -180 to 180
		if (Math.abs(dir) > (period * 0.5))
		{
			dir = -(Math.signum(dir) * period) + dir;
		}
		return dir;
	}
	
	/**
	 * Modulo that works with negative numbers and always returns a positive
	 * number.
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	private double modulo(double a, double b)
	{
		return (a < 0 ? b + (a % b) : a % b);
	}
	
	public void disable()
	{
		m_initialized = false;
	}
	
	public void enable()
	{
		m_initialized = false;
	}
	
	@Override
	public void finishReset()
	{
		m_reset = true;
	}
	
	@Override
	public int getMisreads()
	{
		return m_misreads;
	}
}