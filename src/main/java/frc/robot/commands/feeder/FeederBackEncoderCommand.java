package frc.robot.commands.feeder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Feeder;

public class FeederBackEncoderCommand extends CommandBase {
    private final Feeder feeder;
    private final double relativeTarget;
    private double target;

    public FeederBackEncoderCommand(double relativeTarget, Feeder feeder) {
        this.feeder = feeder;
        this.relativeTarget = relativeTarget;
    }

    @Override
    public void initialize() {
        target = feeder.getEncoderRotations() + relativeTarget;
    }

    @Override
    public void execute() {
        feeder.setFeederPercent(Math.signum(relativeTarget) * 0.1);
    }

    @Override
    public void end(boolean interrupted) {
        feeder.setFeederPercent(0.0);
    }

    @Override
    public boolean isFinished() {
        if (relativeTarget > 0) {
            return feeder.getEncoderRotations() - target >= 0;
        }
        return feeder.getEncoderRotations() - target <= 0;
    }
}
