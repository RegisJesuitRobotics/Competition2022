// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.RotationClimber;

public class ClimberForwardCommand extends CommandBase {
    /** Creates a new ClimberForwardCommand. */
    private final double PERCENT = 0.2;
    private final RotationClimber climber;

    public ClimberForwardCommand(RotationClimber climber) {
        this.climber = climber;
        addRequirements(climber);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        climber.setRotationPercent(PERCENT);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        climber.setRotationPercent(0.0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
