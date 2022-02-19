// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class ClimberForwardCommand extends CommandBase {
    /** Creates a new ClimberForwardCommand. */
    private final double PERCENT = 0.5;
    private Climber climber = new Climber();

    public ClimberForwardCommand(Climber climber) {
        // Use addRequirements() here to declare subsystem dependencies.
        this.climber = climber;
        // addRequirements(climber);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        climber.ClimberRotation(PERCENT);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {}

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        climber.ClimberLength(0.0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
