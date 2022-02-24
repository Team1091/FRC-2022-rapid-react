// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class DriveTrain {
        public final static int backLeftMotorChannel = 1;
        public final static int frontLeftMotorChannel = 2;
        public final static int frontRightMotorChannel = 3;
        public final static int backRightMotorChannel = 4;
    }


    public static final class Escalator {
        public final static int motorChannel = 5;
    }

    public static final class BallPickup {
        public final static int grabberIn = 2;
        public final static int grabberOut = 3;
        public final static int inputMotorChannel = 6; //is a motor not a solenoid
        public final static double inputMotorSpeed = 0.8; //make negative to reverse rotor
    }

    public static final class XboxController {
        public final static int driverPort = 0;
        public final static int payloadSpecallistPort = 1;
    }

    public static final class Vision {
        public final static int frontCameraPort = 0;
        public final static int resizeImageWidth = 160;
        public final static int resizeImageHeight = 120;
        public final static int backCameraPort = 1;

    }

    public static final class Climber {
        public final static int climberIn = 1;
        public final static int climberOut = 0;
    }
}
