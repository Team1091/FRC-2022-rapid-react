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
    public final class DriveTrain {
        public final static int frontLeftMotorChannel = 1;
        public final static int backLeftMotorChannel = 1;
        public final static int frontRightMotorChannel = 1;
        public final static int backRightMotorChannel = 1;
    }


    public final class ConveyerSubs {
        public final static int conveyerMotorChannel = 1;
    }

    public final class Pnemautics {
        public final static int pneumaticIn = 1;
        public final static int pneumaticOut = 1;
    }

    public final class XboxController {
        public final static int port = 1;
    }

    public final class Vision {
        public final static int cameraPort = 1;
        public final static int resizeImageWidth = 160;
        public final static int resizeImageHeight = 120;
    }
}
