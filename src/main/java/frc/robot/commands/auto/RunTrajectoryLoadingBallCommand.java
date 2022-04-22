package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import frc.robot.commands.feeder.LoadBallToWaitingZoneCommand;
import frc.robot.commands.intake.IntakeRunCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.intake.Spinners;

public class RunTrajectoryLoadingBallCommand extends ParallelDeadlineGroup {
    public RunTrajectoryLoadingBallCommand(
            String trajectoryName, DriveTrain driveTrain, Intake intake, Spinners spinners, Feeder feeder
    ) {
        super(
                TrajectoryCommandGenerator.getCommandFromFile(trajectoryName, driveTrain),
                new LoadBallToWaitingZoneCommand(feeder, spinners), new IntakeRunCommand(intake)
        );
    }
}
