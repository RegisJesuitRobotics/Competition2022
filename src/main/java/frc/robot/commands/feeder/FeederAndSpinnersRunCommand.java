package frc.robot.commands.feeder;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.intake.SpinnersRunCommand;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.intake.Spinners;

public class FeederAndSpinnersRunCommand extends ParallelCommandGroup {
    public FeederAndSpinnersRunCommand(double feederSpeed, Feeder feeder, Spinners spinners) {
        super(new FeederRunCommand(feederSpeed, feeder), new SpinnersRunCommand(spinners));
    }
}
