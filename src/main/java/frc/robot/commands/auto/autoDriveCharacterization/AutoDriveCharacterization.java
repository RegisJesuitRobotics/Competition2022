// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.autoDriveCharacterization;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drive.RampUpAutoDriveCommand;
import frc.robot.commands.drive.SimpleAutoDriveCommand;
import frc.robot.subsystems.DriveTrain;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoDriveCharacterization extends SequentialCommandGroup {
  /** Creates a new AutoDriveCharacterization. */
  public AutoDriveCharacterization(DriveTrain driveTrain) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new RampUpAutoDriveCommand(0.4, 0.0, 0.2, driveTrain).withTimeout(2.5));
  }
}
