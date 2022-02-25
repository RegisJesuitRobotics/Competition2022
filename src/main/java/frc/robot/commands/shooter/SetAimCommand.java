package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Shooter;

public class SetAimCommand extends InstantCommand {
    private final Shooter shooter;
    private final boolean farShot;

    public SetAimCommand(boolean farShot, Shooter shooter) {
        this.farShot = farShot;
        this.shooter = shooter;
    }

    @Override
    public void initialize() {
        shooter.setAimState(farShot);
    }
}
