package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shooter.AimState;

public class ShooterAimStateCommand extends InstantCommand {
    private final Shooter shooter;
    private final AimState aimState;


    public ShooterAimStateCommand(AimState aimState, Shooter shooter) {
        this.aimState = aimState;
        this.shooter = shooter;
    }

    @Override
    public void initialize() {
        shooter.setAimState(aimState);
    }
}
