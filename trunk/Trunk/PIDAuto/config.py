{
    # Class names of motor controllers used.
    # Options:
    # 'WPI_TalonSRX'
    # 'WPI_VictorSPX'
    # Note: The first motor should always be a TalonSRX, as the VictorSPX
    # does not support encoder connections.
    "controllerTypes": ["WPI_TalonSRX", "WPI_TalonSRX"],
    # Ports for the motors
    "motorPorts": [19,20],
    # Inversions for the motors
    "motorsInverted": [True, True],
    # Pulley diameter (in units of your choice - will dictate units of analysis)
    "pulleyDiameter": 0.031,
    # This value should be the edges per revolution *of the pulley*, and so
    # should take into account gearing between the encoder and the pulley
    "encoderEPR": 400,
    # Ports for the encoder
    "encoderPorts": [2, 3],
    # Whether the encoder is inverted
    "encoderInverted": False,
}
