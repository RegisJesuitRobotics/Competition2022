package frc.robot.commands.drive;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.joysticks.PlaystationController;
import frc.robot.subsystems.DriveTrain;

public class TankishDriveCommand extends CommandBase {
    private final DriveTrain driveTrain;
    private final PlaystationController driverController;

    private final SlewRateLimiter rightRateLimiter = new SlewRateLimiter(DriveConstants.DRIVER_RATE_LIMITER_PER_SECOND);
    private final SlewRateLimiter leftRateLimiter = new SlewRateLimiter(DriveConstants.DRIVER_RATE_LIMITER_PER_SECOND);

    public TankishDriveCommand(DriveTrain driveTrain, PlaystationController driverController) {
        this.driveTrain = driveTrain;
        this.driverController = driverController;

        addRequirements(driveTrain);
    }

    @Override
    public void initialize() {
        driveTrain.setBrakeMode(true);
        rightRateLimiter.reset(0.0);
        leftRateLimiter.reset(0.0);
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
        rightSpeed = rightRateLimiter.calculate(rightSpeed);
        leftSpeed = leftRateLimiter.calculate(leftSpeed);
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
