package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.Shooter;

public class WaitForShooterWarmupCommand extends WaitUntilCommand {
    public WaitForShooterWarmupCommand(double targetRPM, Shooter shooter) {
        super(() -> shooter.getShooterRPM() >= targetRPM);
    }
}
