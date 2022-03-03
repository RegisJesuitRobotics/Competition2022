package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.intake.Intake;

public class IntakeUnDeployCommand extends InstantCommand {
    private final Intake intake;

    public IntakeUnDeployCommand(Intake intake) {
        this.intake = intake;

        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.setSolenoid(false);
    }
}
