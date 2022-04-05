package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ShooterRunCommand extends CommandBase {
    private final Shooter shooter;
    private final double rpm;

    public ShooterRunCommand(double rpm, Shooter shooter) {
        this.rpm = rpm;
        this.shooter = shooter;

        addRequirements(shooter);
    }

    @Override
    public void execute() {
        shooter.setShooterRPM(rpm);
    }

    @Override
    public void end(boolean interrupted) {
        shooter.setShooterRPM(0.0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
