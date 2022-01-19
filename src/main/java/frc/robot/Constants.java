// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
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

        // Distance between wheels of opposite sides
        public static final double trackWidthMeters = Units.inchesToMeters(26);
        public static final DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(trackWidthMeters);
    }

    public static class PurePursuitConstants {
        public static final double KV = 0.34;
        public static final double KA = 0.002;
        public static final double KP = 0.01;

        public static final double MAX_VELOCITY = 2;

        public static final double SMOOTHING_A = 0.3;
        public static final double SMOOTHING_B = 0.7;
        public static final double SMOOTHING_TOLERANCE = 0.001;

        // How fast to go around turns (1-5)
        public static final double VELOCITY_CONSTANT = 5;
    }

    public static class TrajectoryConstants {
        public static final double sVolts = 0;
        public static final double vVoltSecondsPerMeter = 0;
        public static final double aVoltSecondsSquaredPerMeter = 0;

        public static final double pDriveVel = 0;

        public static final double maxSpeedMetersPerSecond = 3;
        public static final double maxAccelerationMetersPerSecondSquared = 3;

        public static final double ramseteB = 2;
        public static final double ramseteZeta = 0.7;
    }

    public static class LimeLightConstants {
        public static final String networkTablesId = "limelight";
        public static final String streamURL = "http://10.37.29.11:5800";
    }
}
