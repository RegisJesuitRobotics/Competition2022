package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.intake.Intake;


public class IntakeToggleCommand extends InstantCommand {
    private final Intake intake;

    public IntakeToggleCommand(Intake intake) {
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.toggleSolenoid();
    }
}
