// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.intake.Spinners;

public class SpinnersRunCommand extends CommandBase {
    private final double speed;
    private final Spinners spinners;

    /** Creates a new IntakeSpinnersRun. */
    public SpinnersRunCommand(double speed, Spinners spinners) {
        // Use addRequirements() here to declare subsystem dependencies.
        this.speed = speed;
        this.spinners = spinners;

        addRequirements(spinners);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        spinners.setSpinners(speed);
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