package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Shooter;

public class ShooterAimStateCommand extends InstantCommand {
    private final Shooter shooter;


    public ShooterAimStateCommand(Shooter shooter) {
        this.shooter = shooter;
    }

    @Override public void initialize() {
        shooter.setAimState();
    }
}
