package frc.robot.commands.auto;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
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
    public static Command getCommandFromFile(String pathName, DriveTrain driveTrain) {
        Trajectory trajectory;
        try {
            Path twoBall = Filesystem.getDeployDirectory().toPath().resolve("output/" + pathName + ".wpilib.json");
            trajectory = TrajectoryUtil.fromPathweaverJson(twoBall);
        } catch (IOException ex) {
            DriverStation.reportError("Trajectory file cannot be found", ex.getStackTrace());
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
