// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.IntakeConstants;
import frc.robot.subsystems.intake.Intake;

public class IntakeRunCommand extends CommandBase {
    private final Intake intake;
    private final double multiplier;

    public IntakeRunCommand(Intake intake) {
        this(false, intake);
    }

    public IntakeRunCommand(boolean negative, Intake intake) {
        this.intake = intake;
        this.multiplier = negative ? -1 : 1;

        addRequirements(intake);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        intake.setPercentage(IntakeConstants.INTAKE_SPEED * multiplier);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        intake.setPercentage(0.0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
