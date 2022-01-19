package frc.robot.commands.auto;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;

import java.util.List;

import static frc.robot.Constants.TrajectoryConstants.*;

public class TrajectoryCommandGenerator {
    public static Command getCommand(DriveTrain driveTrain) {
        DifferentialDriveVoltageConstraint voltageConstraint = new DifferentialDriveVoltageConstraint(
                new SimpleMotorFeedforward(sVolts, vVoltSecondsPerMeter, aVoltSecondsSquaredPerMeter),
                Constants.DriveConstants.kinematics, 10);

        TrajectoryConfig config = new TrajectoryConfig(maxSpeedMetersPerSecond, maxAccelerationMetersPerSecondSquared)
                .setKinematics(Constants.DriveConstants.kinematics).addConstraint(voltageConstraint);

        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(new Pose2d(0, 0, new Rotation2d(0)),
                List.of(new Translation2d(1, 1), new Translation2d(2, -1)), new Pose2d(3, 0, new Rotation2d(0)),
                config);

        RamseteCommand ramseteCommand = new RamseteCommand(trajectory, driveTrain::getPosition,
                new RamseteController(ramseteB, ramseteZeta),
                new SimpleMotorFeedforward(sVolts, vVoltSecondsPerMeter, aVoltSecondsSquaredPerMeter),
                Constants.DriveConstants.kinematics, driveTrain::getWheelSpeeds, new PIDController(pDriveVel, 0, 0),
                new PIDController(pDriveVel, 0, 0), driveTrain::voltageDrive, driveTrain);

        InstantCommand resetOdometryCommand = new InstantCommand(
                () -> driveTrain.resetOdometry(trajectory.getInitialPose()));

        InstantCommand stopCommand = new InstantCommand(() -> driveTrain.voltageDrive(0, 0));

        return resetOdometryCommand.andThen(ramseteCommand).andThen(stopCommand);
    }
}
