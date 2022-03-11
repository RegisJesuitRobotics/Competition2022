// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.util.Color;

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
        public static final double TRACK_WIDTH_METERS = Units.inchesToMeters(27.5);
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
        public static final int INTAKE_PORT = 14;
        public static final int SPINNER_PORT = 6;

        public static final int SOLENOID_RIGHT_FORWARD_PORT = 0;
        public static final int SOLENOID_RIGHT_REVERSE_PORT = 1;
        public static final int SOLENOID_LEFT_FORWARD_PORT = 8;
        public static final int SOLENOID_LEFT_REVERSE_PORT = 7;
        public static final double SPINNER_SPEED = 1.0;
        public static final double INTAKE_SPEED = 0.7;
    }

    public static class ClimberConstants {
        public static final int LEFT_CLIMBER_LENGTH_PORT = 5;
        public static final int LEFT_CLIMBER_ROTATION_PORT = 4;
        public static final int RIGHT_CLIMBER_LENGTH_PORT = 11;
        public static final int RIGHT_CLIMBER_ROTATION_PORT = 10;

        public static final double LENGTH_SPEED = 0.7;
        public static final double ROTATION_SPEED = 0.2;
    }

    public static class ShooterConstants {
        public static final int SHOOTER_PORT = 13;

        public static final double SHOOTER_VELOCITY_P = 0.220;
        public static final double SHOOTER_VELOCITY_S_VOLTS = 0.17322;
        public static final double SHOOTER_VELOCITY_V_VOLTS = 0.12609;

        public static final int SHOOTER_AIM_OPEN_PORT = 15;
        public static final int SHOOTER_AIM_CLOSE_PORT = 14;

        public static final double SHOOTER_GEARING = 1.0;

        public static final double FAR_DISTANCE_RPM = 4400.0;
        public static final double CLOSE_DISTANCE_RPM = 3700.0;
        public static final double EXPEL_BALL_RPM = 1500.0;

        // TODO: get this value
        public static final double FAR_SHOOTING_LOCATION_DISTANCE_METERS = Units.inchesToMeters(100);
    }

    public static class FeederConstants {
        public static final int FEEDER_PORT = 12;

        public static final double FEEDER_SPEED = 0.4;

        public static final double FEEDER_SENSOR_CONFIDENCE_LEVEL = 0.8;
    }

    public static class LimeLightConstants {
        public static final String NETWORK_TABLES_ID = "limelight";
        public static final String STREAM_URL = "http://10.37.29.11:5800";

        public static final double MOUNT_HEIGHT_METERS = Units.inchesToMeters(19);
        public static final double MOUNT_ANGLE_DEGREES = 45.0;

        public static final double P_LIMELIGHT_DISTANCE = 0.4;
        public static final double LIMELIGHT_DISTANCE_ACCEPTABLE_ERROR_METERS = 0.2;

        public static final double P_LIMELIGHT_ANGLE = 0.03;
        public static final double ARB_FF_LIMELIGHT_ANGLE = 0.2;
        public static final double LIMELIGHT_ANGLE_ACCEPTABLE_ERROR_ANGLE = 0.5;
    }

    public static class FieldConstants {
        public static final double HUB_HEIGHT_METERS = Units.inchesToMeters((8 * 12) + 8); // 8ft 8

        public static final double VISION_TARGET_LENGTH_METERS = Units.inchesToMeters(5);
        public static final double VISION_TARGET_HEIGHT_METERS = Units.inchesToMeters(2);

        public static final double VISION_TARGET_DISTANCE_FROM_GROUND = HUB_HEIGHT_METERS
                - (VISION_TARGET_HEIGHT_METERS / 2);

        // TODO: calibrate
        public static final Color RED_BALL_COLOR = new Color(1.0, 0.0, 0.0);
        public static final Color BLUE_BALL_COLOR = new Color(0.0, 0.0, 1.0);
    }
}
