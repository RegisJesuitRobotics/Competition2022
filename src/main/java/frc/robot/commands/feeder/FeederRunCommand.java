package frc.robot.commands.feeder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Feeder;

public class FeederRunCommand extends CommandBase {
    private final Feeder feeder;
    private final double speed;

    public FeederRunCommand(double speed, Feeder feeder) {
        this.feeder = feeder;
        this.speed = speed;

        addRequirements(feeder);
    }

    @Override
    public void execute() {
        feeder.setFeederPercent(speed);
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
