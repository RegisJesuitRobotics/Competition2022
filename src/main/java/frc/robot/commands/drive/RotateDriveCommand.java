package frc.robot.commands.drive;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.TrajectoryConstants;
import frc.robot.subsystems.DriveTrain;

public class RotateDriveCommand extends CommandBase {
    private final DriveTrain driveTrain;
    private final double relativeDegrees;
    private final PIDController pidController;

    public RotateDriveCommand(double relativeDegrees, DriveTrain driveTrain) {
        this.relativeDegrees = relativeDegrees;
        this.driveTrain = driveTrain;

        pidController = new PIDController(TrajectoryConstants.P_ANGLE_POSITION_DEGREES,
                TrajectoryConstants.I_ANGLE_POSITION_DEGREES, 0.0);
//        pidController = new PIDController(0.01, 0.0, 0.0);
        pidController.enableContinuousInput(-180, 180);
        pidController.setTolerance(1.5);

        addRequirements(driveTrain);
    }

    @Override
    public void initialize() {
        // Wrap to -180 - 180
        pidController.setSetpoint(Math.IEEEremainder(driveTrain.getHeading() + relativeDegrees, 360));
    }

    @Override
    public void execute() {
        double voltage = pidController.calculate(driveTrain.getHeading());
        voltage += Math.signum(voltage) * TrajectoryConstants.ARB_FF_ANGLE_POSITION_DEGREES;

        driveTrain.voltageDrive(-voltage, voltage);
    }

    @Override
    public void end(boolean interrupted) {
        driveTrain.voltageDrive(0.0, 0.0);
    }

    @Override
    public boolean isFinished() {
        return pidController.atSetpoint();
    }
}
