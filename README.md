# Testing the Am-2067 Gyroscope

### Wiring

Solder or connect a PWM cable to the solder holes or pins next to the text 'Rate' on the gyro board. The solder hole or pin next closest to the 'Rate' text is signal, and then 5v power and Ground, respectively. Connect these to an analog breakout on the cRio(only channels 1 and 2).

### About

Used to test a gyroscope and the Gyro WPILIB class.
the getAngle() method gets the angle of the current heading since teleopInit()
when the gyro.reset(); is called
