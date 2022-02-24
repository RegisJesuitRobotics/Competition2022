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
        public static final int LEFT_FRONT_PORT = 1;
        public static final int LEFT_BACK_PORT = 3;

        public static final int RIGHT_TOP_PORT = 8;
        public static final int RIGHT_FRONT_PORT = 9;
        public static final int RIGHT_BACK_PORT = 7;

        public static final double WHEEL_DIAMETER_METERS = Units.inchesToMeters(6);
        public static final double GEARING = 8.68;
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

        public static final double RAMSETE_B = 2;
        public static final double RAMSETE_ZETA = 0.7;
    }

    public static class IntakeConstants {
        public static final int INTAKE_PORT = 14;
        public static final int SOLENOID_RIGHT_OPEN_PORT = 1;
        public static final int SOLENOID_RIGHT_CLOSE_PORT = 0;
        public static final int SOLENOID_LEFT_OPEN_PORT = 8;
        public static final int SOLENOID_LEFT_CLOSE_PORT = 7;
        public static final int SPINNER_LEFT = -1;
        public static final int SPINNER_RIGHT = -1;
    }

    public static class ClimberConstants {
        public static final int LEFT_CLIMBER_LENGTH_PORT = 5;
        public static final int LEFT_CLIMBER_ROTATION_PORT = 4;
        public static final int RIGHT_CLIMBER_LENGTH_PORT = 11;
        public static final int RIGHT_CLIMBER_ROTATION_PORT = 10;

        public static final double LENGTH_SPEED = 0.3;
        public static final double ROTATION_SPEED = 0.3;
    }

    public static class ShooterConstants {
        public static final int SHOOTER_PORT = 13;

        public static final double VELOCITY_P = 0;
        public static final double VELOCITY_I = 0;
        public static final double VELOCITY_D = 0;
        public static final double VELOCITY_FF = 0;
    }

    public static class LimeLightConstants {
        public static final String NETWORK_TABLES_ID = "limelight";
        public static final String STREAM_URL = "http://10.37.29.11:5800";
    }
}
