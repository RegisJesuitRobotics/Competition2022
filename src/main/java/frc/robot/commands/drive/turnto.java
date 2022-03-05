// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drive;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants.TrajectoryConstants;
import frc.robot.subsystems.DriveTrain;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class turnto extends PIDCommand {
  /** Creates a new turnto. */
  public turnto(double speed, double angularSpeed, double limit, DriveTrain driveTrain) {
    super(
        // The controller that the command will use
        new PIDController(TrajectoryConstants.P_DRIVE_VEL, 0.0, 0.0),
        // This should return the measurement
        () -> driveTrain.getHeading(),
        // This should return the setpoint (can also be a constant)
        () -> angularSpeed,
        // This uses the output
        output -> {
          // Use the output here
          driveTrain.arcadeDrive(calculateLimiter(speed, limit), output);
        });
        addRequirements(driveTrain);
        getController().setTolerance(3);
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
  }
  private static double calculateLimiter(double speed, double limit) {
    final SlewRateLimiter limiter = new SlewRateLimiter(limit);
    return limiter.calculate(speed);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
