package frc.robot.commands.feeder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.FeederConstants;
import frc.robot.subsystems.Feeder;

public class RunFeederCommand extends CommandBase {
    private final Feeder feeder;

    public RunFeederCommand(Feeder feeder) {
        this.feeder = feeder;

        addRequirements(feeder);
    }

    @Override
    public void execute() {
        feeder.setFeederPercent(FeederConstants.FEEDER_PERCENT);
    }

    @Override
    public void end(boolean interrupted) {
        feeder.setFeederPercent(0.0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
