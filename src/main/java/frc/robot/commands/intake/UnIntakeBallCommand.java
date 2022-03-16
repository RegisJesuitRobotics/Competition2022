package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.feeder.FeederRunCommand;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.intake.Spinners;

public class UnIntakeBallCommand extends ParallelCommandGroup {
    public UnIntakeBallCommand(Intake intake, Spinners spinners, Feeder feeder) {
        super(new IntakeRunCommand(true, intake), new SpinnersRunCommand(true, spinners),
                new FeederRunCommand(-0.7, feeder));
    }
}
