package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.Shooter;

public class WaitForShooterWarmup extends WaitUntilCommand {
    public WaitForShooterWarmup(double targetRPM, Shooter shooter) {
        super(() -> shooter.getShooterRPM() >= targetRPM);
    }
}
