// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final boolean autoSwitchShuffleboardTab = false;

    public static class DriveConstants {
        public static final int leftLeaderPort = 2;
        public static final int leftFollowerPort = 15;
        public static final int rightLeaderPort = 1;
        public static final int rightFollowerPort = 13;

        public static final double wheelDiameterMeters = Units.inchesToMeters(4);
        public static final double countPerRotation = 2048;
        public static final double gearing = 85.0 / 12;
        public static final double distancePerCount = (wheelDiameterMeters * Math.PI) / (countPerRotation * gearing);

        /*
         * Steps to tune PID
         * https://docs.wpilib.org/en/stable/docs/software/advanced-controls/
         * introduction/tuning-pid-controller.html
         */
        public static final double driveDistanceP = 4;
        public static final double driveDistanceI = 0;
        public static final double driveDistanceD = 0.329;

        public static final double driveRotateP = 0;
        public static final double driveRotateI = 0;
        public static final double driveRotateD = 0;
    }

    public static class PurePursuitConstants {
        public static final double KV = 0.34;
        public static final double KA = 0.002;
        public static final double KP = 0.01;

        public static final double MAX_VELOCITY = 2;
    }

    public static class LimeLightConstants {
        public static final String networkTablesId = "limelight";
        public static final String streamURL = "http://10.37.29.11:5800";
    }
}
