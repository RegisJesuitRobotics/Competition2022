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
        public static final int LEFT_TOP_PORT = 2;
        public static final int LEFT_FRONT_PORT = 3;
        public static final int LEFT_BACK_PORT = 4;

        public static final int RIGHT_TOP_PORT = 5;
        public static final int RIGHT_FRONT_PORT = 6;
        public static final int RIGHT_BACK_PORT = 7;

        public static final double WHEEL_DIAMETER_METERS = Units.inchesToMeters(6);
        public static final double GEARING = 85.0 / 12;
        public static final double DISTANCE_PER_ROTATION = (WHEEL_DIAMETER_METERS * Math.PI) / (GEARING);

        // Distance between wheels of opposite sides
        public static final double TRACK_WIDTH_METERS = Units.inchesToMeters(26);
        public static final DifferentialDriveKinematics DRIVE_KINEMATICS = new DifferentialDriveKinematics(
                TRACK_WIDTH_METERS);
    }

    public static class TrajectoryConstants {
        public static final double S_VOLTS = 0.55345;
        public static final double V_VOLT_SECONDS_PER_METER = 2.384;
        public static final double A_VOLT_SECONDS_SQUARED_PER_METER = 0.15702;

        public static final double P_DRIVE_VEL = 2.5832;

        public static final double RAMSETE_B = 2.0;
        public static final double RAMSETE_ZETA = 0.7;
    }

    public static class IntakeConstants {
        public static final int INTAKE_PORT = -1;
        public static final int SOLENOID_PORT = -1;
    }


    public static class ShooterConstants {
        public static final int SHOOTER_PORT = 2;

        public static final double SHOOTER_VELOCITY_P = 0;
        public static final double SHOOTER_VELOCITY_S_VOLTS = 0;
        public static final double SHOOTER_VELOCITY_V_VOLTS = 0;

        public static final double SHOOTER_GEARING = 3.0;

        public static final double LONG_DISTANCE_RPM = 2000.0;
    }

    public static class FeederConstants {
        public static final int FEEDER_PORT = 1;

        public static final double FEEDER_VELOCITY_P = 0.13832;
        public static final double FEEDER_VELOCITY_S_VOLTS = 0.28575;
        public static final double FEEDER_VELOCITY_V_VOLTS = 0.2089;

        public static final double FEEDER_GEARING = 3.0;

        public static final double FEEDER_RPM = 200.0;
    }

    public static class LimeLightConstants {
        public static final String NETWORK_TABLES_ID = "limelight";
        public static final String STREAM_URL = "http://10.37.29.11:5800";
    }
}
