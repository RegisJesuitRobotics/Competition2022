package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Intake;

public class IntakeDeploy extends InstantCommand {
  public IntakeDeploy(Intake intake) {
    super(intake::setSolenoid, intake);
    // Use addRequirements() here to declare subsystem dependencies.
  } 
}