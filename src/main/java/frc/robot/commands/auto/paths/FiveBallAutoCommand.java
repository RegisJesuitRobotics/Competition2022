package frc.robot.commands.auto.paths;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.intake.IntakeDeployCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.intake.Spinners;

public class FiveBallAutoCommand extends SequentialCommandGroup {
    public FiveBallAutoCommand(DriveTrain driveTrain, Intake intake, Shooter shooter, Feeder feeder, Spinners spinners) {
        super(
                new IntakeDeployCommand(intake),

        )
    }
}
