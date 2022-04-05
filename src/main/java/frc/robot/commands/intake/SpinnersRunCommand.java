// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.IntakeConstants;
import frc.robot.subsystems.intake.Spinners;

public class SpinnersRunCommand extends CommandBase {
    private final Spinners spinners;
    private final double multiplier;

    /** Creates a new IntakeSpinnersRun. */
    public SpinnersRunCommand(boolean negative, Spinners spinners) {
        this.spinners = spinners;
        this.multiplier = negative ? -1 : 1;

        addRequirements(spinners);
    }

    public SpinnersRunCommand(Spinners spinners) {
        this(false, spinners);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        spinners.setSpinners(IntakeConstants.SPINNER_SPEED * multiplier);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        spinners.setSpinners(0.0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
