package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.joysticks.PlaystationController;
import frc.robot.subsystems.DriveTrain;

public class ArcadeDriveCommand extends CommandBase {
    private final DriveTrain driveTrain;
    private final PlaystationController driverController;

    public ArcadeDriveCommand(DriveTrain driveTrain, PlaystationController driverController) {
        this.driveTrain = driveTrain;
        this.driverController = driverController;

        addRequirements(driveTrain);
    }

    @Override
    public void initialize() {
        driveTrain.setBrakeMode(true);
    }

    @Override
    public void execute() {
        driveTrain.arcadeDrive(driverController.leftThumb.getYAxis(), driverController.leftThumb.getXAxis());
    }

    @Override
    public void end(boolean interrupted) {
        driveTrain.arcadeDrive(0, 0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
