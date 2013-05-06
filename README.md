# PID using the Am-2067 Gyroscope

This sample uses a Drivetrain capable of rotation and a Gyro to explain how to use the PID classes of the WPILIB by rotating the Robot 90 degrees.

### Requirements

* Analog breakout
* Crio
* Digital Sidecar
* A Drivetrain cabable of rotation
* SmartDashboard
* A mounted Gyroscope hooked up to the analog breakout on pin 2(or change it to 1)

You will probably need to Change the Jaguar class to the motor controller of your choice and specify the correct PWM out ports.

### Tuning

You will probably need to tune the values of P, I, and D for your drive train for it to rotate in a more efficient manner. This can be done live by going into Test mode.

### Wiring

Solder or connect a PWM cable to the solder holes or pins next to the text 'Rate' on the gyro board. The solder hole or pin next closest to the 'Rate' text is signal, and then 5v power and Ground, respectively. Connect these to an analog breakout on the cRio(only channels 1 and 2).

![wiring image](http://azrathud.com/data/gyro-images/gyro.png)
