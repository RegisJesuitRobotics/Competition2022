// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LengthClimber;

public class ClimberDownCommand extends CommandBase {
    private final double PERCENT = -0.2;
    private final LengthClimber climber;

    /** Creates a new ClimberDownCommand. */
    public ClimberDownCommand(LengthClimber climber) {
        this.climber = climber;
        addRequirements(climber);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        climber.setLengthPercent(PERCENT);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        climber.setLengthPercent(0.0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
