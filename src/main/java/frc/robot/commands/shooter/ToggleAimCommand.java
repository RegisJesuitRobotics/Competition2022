package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Shooter;

public class ToggleAimCommand extends InstantCommand {
    private final Shooter shooter;

    public ToggleAimCommand(Shooter shooter) {
        this.shooter = shooter;
    }

    @Override
    public void initialize() {
        shooter.toggleAimState();
    }
}
