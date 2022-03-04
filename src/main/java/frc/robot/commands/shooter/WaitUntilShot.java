// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooter;


import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Shooter;
public class WaitUntilShot extends WaitUntilCommand{
  /** Creates a new WaitUntilShot. */

  private final static int SHOOTER_SHOT_THRESHOLD = Constants.ShooterConstants.SHOOTER_SHOT_THRESHOLD;
  
  public WaitUntilShot(Shooter shooter) {
    
    super(() -> shooter.getShooterRPM() < SHOOTER_SHOT_THRESHOLD);
  }
}

 
