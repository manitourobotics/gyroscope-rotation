package com.github.manitourobotics.gyroscopetesting;


import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
 * Used to test a gyroscope and the Gyro WPILIB class with a working drive train
 * and a Gyroscope attached to analog breakout.
 * 
 * The gyroscope is used to determine an angle for the 'setpoint'
 * which will take the setpoint minus current angle and use that as an indication
 * of error which it attempts to correct using PID pidControl. 
 * 
 * The drivetrain's rotation speed/direction/time duration is then based on this degree of error
 */
public class GyroscopeTesting extends IterativeRobot {
    public void robotInit() {

        // The gyroscope outputs a the absolute angle from the 
        //      original point.
        // Negative -90 is literally the robot counterclockwise 90 degrees
        // putting -720 as the argument would make the robot turn 
        //      counterclockwise twice
        // Putting 720 as the argument would rotate the robot clockwise twice
        pidControl.setSetpoint(-90);

        // This is the point at which the error is small enough to be tolerated
        pidControl.setPercentTolerance(3);

        // The maximum value given to the motor controller
        //      maximum speed is way too fast, and causes the robot grind on the
        //      motors, so 3/4 speed is good enough for my robot
        pidControl.setOutputRange(-.75, .75);

        pidControl.enable();
    }

    // Gyroscopes only be used on channels 1 and 2 for the analog breakout
    // to quote the error, "Accumulators are only available on slot 1 on channels 1,2"
    final int ANALOG_GYRO_SIGNAL = 2;
    Gyro gyro = new Gyro(ANALOG_GYRO_SIGNAL);

    // Note that the drivetrain of my robot uses one motor controller per side. 
    // If you drive train is different, then this will need to be changed.
    Jaguar leftDrive = new Jaguar(1);
    Jaguar rightDrive = new Jaguar(2);
    RobotDrive drive = new RobotDrive(leftDrive, rightDrive);

    // see below
    PIDOutput outputDriveRotate = new OutputDriveRotate();

    // The arguments are P, I, D, an object of a class that implements 
    // PIDSource, and an object of a class that implements PIDOutput
    PIDController pidControl = new PIDController(.2, .001, .003, gyro, outputDriveRotate);

    /*
     * To 'feed' the input of the gyroscope into the output of the drivetrain's 
     * rotation speed, some 'glue' is needed since the RobotDrive class doesn't 
     * implement the PIDOput interface, which the PIDController needs to 
     * properly input values. 
     * 
     * implementing the pidWrite method is the extent of the PIDOutput interface.
     * 
     * The Gyro class already implements 
     * PIDSource(which returns the absolute angle of the gyroscope from its 
     *      original position)
     */
    class OutputDriveRotate implements PIDOutput {
        public void pidWrite(double d) {
        SmartDashboard.putNumber("Motors", d);
            drive.arcadeDrive(0, d * -1);
        }
    }

    /*
     * Everytime you hit 'enable' on teleop mode, the gyroscope will be reset
     * to zero
     */
    public void teleopInit() {
        gyro.reset();
    }

    /*
     * The Gyroscope angle will be output to the screen in teleop mode
     * Note that the PIDController is working behind the scenes; It creates
     * a separate thread and does its own thing.
     */
    public void teleopPeriodic() {
        SmartDashboard.putNumber("Gyro angle", gyro.getAngle());
    }

    /*
     * Everytime you hit 'enable' on Test mode, the gyroscope will be reset
     * to zero if the mdoe is already disabled
     */
    public void testInit() {
        gyro.reset();
    }

    /*
     *  The putting the PIDController to the smart dashboard allows it to be
     *  tuned by adjusting values
     *  see http://wpilib.screenstepslive.com/s/3120/m/7932/l/81113-pid-tuning-with-smartdashboard
     * 
     *  The PIDContoller object can be put to the screen and works because it 
     *  implements the Sendable interface of the WPILIB
     */
    public void testPeriodic() {
        SmartDashboard.putData("PID tune", pidControl);

    }
}
