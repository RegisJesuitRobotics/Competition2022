// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.intake.*;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class IntakeSpinnersRunCommand extends ParallelCommandGroup {
    private final double INTAKE_SPEED = 0.7;

    /** Creates a new IntakeSpinnersRun. */
    public IntakeSpinnersRunCommand(Intake intake, Spinners spinners) {
        // Add your commands in the addCommands() call, e.g.
        // addCommands(new FooCommand(), new BarCommand());
        addCommands(new IntakeRunCommand(intake), new SpinnersRunCommand(spinners));
    }
}
