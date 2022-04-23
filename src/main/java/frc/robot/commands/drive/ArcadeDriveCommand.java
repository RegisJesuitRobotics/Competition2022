package frc.robot.commands.drive;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.joysticks.PlaystationController;
import frc.robot.subsystems.DriveTrain;

import java.util.function.DoubleSupplier;

public class ArcadeDriveCommand extends CommandBase {
    private final DriveTrain driveTrain;
    private final PlaystationController driverController;
    private final DoubleSupplier maxSpeed;

    private final SlewRateLimiter rateLimiter = new SlewRateLimiter(3.0);
    private final SlewRateLimiter rateLimiter2 = new SlewRateLimiter(3.0);

    public ArcadeDriveCommand(DoubleSupplier maxSpeed, DriveTrain driveTrain, PlaystationController driverController) {
        this.maxSpeed = maxSpeed;
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
        driveTrain.arcadeDrive(
                rateLimiter.calculate(driverController.leftThumb.getYAxis()),
                rateLimiter2.calculate(driverController.rightThumb.getXAxis()), maxSpeed.getAsDouble()
        );
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
