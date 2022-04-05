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
        public static final double TRACK_WIDTH_METERS = 0.7483;
        public static final DifferentialDriveKinematics DRIVE_KINEMATICS = new DifferentialDriveKinematics(
                TRACK_WIDTH_METERS);
    }

    public static class TrajectoryConstants {
        public static final double S_VOLTS = 0.14741;
        public static final double V_VOLT_SECONDS_PER_METER = 2.2627;
        public static final double A_VOLT_SECONDS_SQUARED_PER_METER = 0.46053;

        public static final double P_DRIVE_VEL = 3.1136;

        public static final double RAMSETE_B = 2.0;
        public static final double RAMSETE_ZETA = 0.7;

        public static final double P_ANGLE_POSITION_DEGREES = 0.03;
        public static final double ARB_FF_ANGLE_POSITION_DEGREES = 0.75;
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

        public static final double ROTATION_SPEED = 0.2;
    }

    public static class ShooterConstants {
        public static final int SHOOTER_PORT = 13;

        public static final double SHOOTER_VELOCITY_P = 0.200;
        public static final double SHOOTER_VELOCITY_S_VOLTS = 0.17322;
        public static final double SHOOTER_VELOCITY_V_VOLTS = 0.12609;

        public static final int SHOOTER_AIM_OPEN_PORT = 15;
        public static final int SHOOTER_AIM_CLOSE_PORT = 14;

        public static final double SHOOTER_GEARING = 1.0;

        public static final double FAR_DISTANCE_RPM = 4500.0;
        public static final double TWO_BALL_DISTANCE_RPM = 4500.0;
        public static final double THREE_BALL_RPM = 4600.0;
        public static final double THREE_BALL_CLOSE = 3900.0;
        public static final double CLOSE_DISTANCE_RPM = 3800.0;
        public static final double EDGE_TARMAC_RPM = 4150.0;
        public static final double EXPEL_BALL_RPM = 2000.0;
    }

    public static class FeederConstants {
        public static final int FEEDER_PORT = 12;

        public static final double FEEDER_SPEED = 0.2;
        public static final double FEEDER_BACKWARD_SPEED = -0.4;

        public static final double FEEDER_SENSOR_PROXIMITY_LEVEL = 450;
    }
}
