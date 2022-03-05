// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drive;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.DriveTrain;
import static frc.robot.Constants.TrajectoryConstants.*;

public class RampUpAutoDriveCommand extends CommandBase {
  private final double speed;
  private final double angularSpeed;
  private final DriveTrain driveTrain;

  private final SlewRateLimiter limiter;
  private final PIDController pid = new PIDController(P_DRIVE_VEL, 0.0, 0.0);

  /** Creates a new RampUpAutoDriveCommand. */
  public RampUpAutoDriveCommand(double speed, double angularSpeed, double limiter, DriveTrain driveTrain) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.speed = speed;
    this.angularSpeed = angularSpeed;
    this.limiter = new SlewRateLimiter(limiter);
    this.driveTrain = driveTrain;

    addRequirements(driveTrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // error = driveTrain.getLeftEncoderDistance() - driveTrain.getRightEncoderDistance();
    // driveTrain.arcadeDrive(limiter.calculate(speed), angularSpeed);
    new PIDCommand(pid, () -> driveTrain.getHeading(), angularSpeed, output -> driveTrain.arcadeDrive(limiter.calculate(speed), output), driveTrain);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveTrain.arcadeDrive(0.0, 0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
