package frc.robot.commands.auto;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;

import java.io.IOException;
import java.nio.file.Path;

import static frc.robot.Constants.TrajectoryConstants.*;

public class TrajectoryCommandGenerator {
    public static Command getCommand(DriveTrain driveTrain) {
        DifferentialDriveVoltageConstraint voltageConstraint = new DifferentialDriveVoltageConstraint(
                new SimpleMotorFeedforward(sVolts, vVoltSecondsPerMeter, aVoltSecondsSquaredPerMeter),
                Constants.DriveConstants.kinematics, 4);

        TrajectoryConfig config = new TrajectoryConfig(maxSpeedMetersPerSecond, maxAccelerationMetersPerSecondSquared)
                .setKinematics(Constants.DriveConstants.kinematics).addConstraint(voltageConstraint);

//        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(new Pose2d(0, 0, new Rotation2d(0)),
//                List.of(new Translation2d(1, 1), new Translation2d(2, -1)), new Pose2d(3, 0, new Rotation2d(0)),
//                config);

        Trajectory trajectory;
        try {
            Path twoBall = Filesystem.getDeployDirectory().toPath().resolve("output/SimpleCurve.wpilib.json");
            trajectory = TrajectoryUtil.fromPathweaverJson(twoBall);
        } catch (IOException ex) {
            DriverStation.reportError("IT no work", ex.getStackTrace());
            return null;
        }


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
