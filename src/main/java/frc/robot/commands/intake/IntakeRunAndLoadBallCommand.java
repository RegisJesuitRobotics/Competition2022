package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.feeder.LoadBallToWaitingZoneAndCheckColorCommand;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.intake.Spinners;

public class IntakeRunAndLoadBallCommand extends ParallelCommandGroup {
    public IntakeRunAndLoadBallCommand(Feeder feeder, Intake intake, Shooter shooter, Spinners spinners) {
        super(new LoadBallToWaitingZoneAndCheckColorCommand(feeder, shooter, spinners), new IntakeRunCommand(intake));
    }
}
