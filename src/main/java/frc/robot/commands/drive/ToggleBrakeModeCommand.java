package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.DriveTrain;

public class ToggleBrakeModeCommand extends InstantCommand {
    private final DriveTrain driveTrain;

    public ToggleBrakeModeCommand(DriveTrain driveTrain) {
        this.driveTrain = driveTrain;
    }

    @Override
    public void initialize() {
        driveTrain.toggleBrakeMode();
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}
