package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class ZeroDriveCommand extends CommandBase {
    private final DriveTrain driveTrain;

    public ZeroDriveCommand(DriveTrain driveTrain) {
        this.driveTrain = driveTrain;
    }

    @Override
    public void execute() {
        driveTrain.tankDrive(0.0, 0.0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
