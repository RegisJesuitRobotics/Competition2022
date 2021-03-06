package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.joysticks.PlaystationController;
import frc.robot.subsystems.DriveTrain;

public class TankishDriveCommand extends CommandBase {
    private final DriveTrain driveTrain;
    private final PlaystationController driverController;

    public TankishDriveCommand(DriveTrain driveTrain, PlaystationController driverController) {
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
        double leftTrigger = driverController.leftTrigger.getAxis();
        double rightTrigger = driverController.rightTrigger.getAxis();
        double leftStickX = driverController.leftThumb.getXAxis();

        double speed = rightTrigger - leftTrigger;
        double turn = 2 * leftStickX;

        double leftSpeed = speed;
        double rightSpeed = speed;
        if (turn > 0) {
            rightSpeed -= turn * speed;
        } else if (turn < 0) {
            leftSpeed += turn * speed;
        }
        driveTrain.tankDrive(leftSpeed * 0.8, rightSpeed * 0.8);
    }

    @Override
    public void end(boolean interrupted) {
        driveTrain.tankDrive(0, 0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
