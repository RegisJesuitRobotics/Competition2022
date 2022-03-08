package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class SimpleAutoDriveCommand extends CommandBase {
    private final double speed;
    private final double angularSpeed;
    private final DriveTrain driveTrain;

    public SimpleAutoDriveCommand(double speed, double angularSpeed, DriveTrain driveTrain) {
        this.speed = speed;
        this.angularSpeed = angularSpeed;
        this.driveTrain = driveTrain;

        addRequirements(driveTrain);
    }

    @Override
    public void execute() {
        driveTrain.arcadeDrive(speed, angularSpeed);
    }

    @Override
    public void end(boolean interrupted) {
        driveTrain.arcadeDrive(0.0, 0.0);
    }
}
