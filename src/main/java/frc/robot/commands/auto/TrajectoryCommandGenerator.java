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
    private static final String DIRECTORY = "paths/output/";
//    private static final String DIRECTORY = "pathplanner/generatedJSON/";
    private static final String FILE_EXTENSION = ".wpilib.json";

    public static Command getCommandFromFile(String pathName, DriveTrain driveTrain) {
        String fullFileName = DIRECTORY + pathName + FILE_EXTENSION;
        Trajectory trajectory;
        try {
            Path filePath = Filesystem.getDeployDirectory().toPath().resolve(fullFileName);
            trajectory = TrajectoryUtil.fromPathweaverJson(filePath);
        } catch (IOException ex) {
            DriverStation.reportError("Trajectory file '" + fullFileName + "' cannot be found", ex.getStackTrace());
            return null;
        }

        RamseteCommand ramseteCommand = new RamseteCommand(trajectory, driveTrain::getPosition,
                new RamseteController(RAMSETE_B, RAMSETE_ZETA),
                new SimpleMotorFeedforward(S_VOLTS, V_VOLT_SECONDS_PER_METER, A_VOLT_SECONDS_SQUARED_PER_METER),
                Constants.DriveConstants.DRIVE_KINEMATICS, driveTrain::getWheelSpeeds,
                new PIDController(P_DRIVE_VEL, 0, 0), new PIDController(P_DRIVE_VEL, 0, 0), driveTrain::voltageDrive,
                driveTrain);

        InstantCommand resetOdometryCommand = new InstantCommand(
                () -> driveTrain.resetOdometry(trajectory.getInitialPose()));

        InstantCommand stopCommand = new InstantCommand(() -> driveTrain.voltageDrive(0, 0));

        return resetOdometryCommand.andThen(ramseteCommand).andThen(stopCommand);
    }
}
