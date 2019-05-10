SPI
===

If you use these, I’m sorry. Please try not to use SPI for anything but the FRC Gyro.

You’ll just have to look at the documentation a lot, then figure out how someone else did it in another language to understand what’s going on, then try to shoehorn that into FIRST’s SPI implementation, somehow scrounge out the data you need, then debug it for several weeks.

Here’s an example:

`SPIEncoderPIDSource, used in 2017 <http://nacsvn.aasd.k12.wi.us/repos/nac-software-2017/robot/trunk/Team93Robot2017/src/org/usfirst/frc/team93/robot/utilities/SPIEncoderPIDSource.java>`_

Oh, and ChipSelect1-3 doesn’t work as of 2017.
Use DigitalOutputs instead if you have to. Good luck.

.. toctree::
	:glob:
	:maxdepth: 10
	:caption: Contents:
	
	