package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.feeder.LoadBallToWaitingZoneCommand;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.intake.Intake;

public class IntakeRunAndLoadBallToWaitCommand extends ParallelCommandGroup {
    public IntakeRunAndLoadBallToWaitCommand(Feeder feeder, Intake intake) {
        super(new LoadBallToWaitingZoneCommand(feeder), new IntakeRunCommand(intake));
    }
}
