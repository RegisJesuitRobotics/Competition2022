package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ClimberConstants;
import frc.robot.joysticks.Logitech3DProController;
import frc.robot.subsystems.climber.LengthClimber;
import frc.robot.subsystems.climber.RotationClimber;

public class ClimberControllerControlCommand extends CommandBase {
    private final Logitech3DProController logitechController;
    private final LengthClimber lengthClimber;
    private final RotationClimber rotationClimber;

    public ClimberControllerControlCommand(Logitech3DProController logitechController, LengthClimber lengthClimber,
            RotationClimber rotationClimber) {
        this.logitechController = logitechController;
        this.lengthClimber = lengthClimber;
        this.rotationClimber = rotationClimber;

        addRequirements(lengthClimber, rotationClimber);
    }

    @Override
    public void execute() {
        // Either both-climbers buttons pressed
        if (logitechController.buttonEight.get() || logitechController.buttonNine.get()) {
            if (logitechController.buttonEight.get() && logitechController.buttonNine.get()) {
                // Both are pressed
                lengthClimber.setBothPercent(0.0);
            } else if (logitechController.buttonEight.get()) {
                // Up is pressed
                lengthClimber.setBothPercent(ClimberConstants.LENGTH_SPEED);
            } else {
                // Down is pressed
                lengthClimber.setBothPercent(-ClimberConstants.LENGTH_SPEED);
            }
            return;
        }

        // Either left-climbers buttons pressed
        if (logitechController.buttonSix.get() || logitechController.buttonSeven.get()) {
            if (logitechController.buttonSix.get() && logitechController.buttonSeven.get()) {
                // Both are pressed
                lengthClimber.setLeftPercent(0.0);
            } else if (logitechController.buttonSix.get()) {
                // Up is pressed
                lengthClimber.setLeftPercent(ClimberConstants.LENGTH_SPEED);
            } else {
                // Down is pressed
                lengthClimber.setLeftPercent(-ClimberConstants.LENGTH_SPEED);
            }
            // No early return because we need to check right still
        }

        // Either right-climbers buttons pressed
        if (logitechController.buttonTen.get() || logitechController.buttonEleven.get()) {
            if (logitechController.buttonTen.get() && logitechController.buttonEleven.get()) {
                // Both are pressed
                lengthClimber.setRightPercent(0.0);
            } else if (logitechController.buttonTen.get()) {
                // Up is pressed
                lengthClimber.setRightPercent(ClimberConstants.LENGTH_SPEED);
            } else {
                // Down is pressed
                lengthClimber.setRightPercent(-ClimberConstants.LENGTH_SPEED);
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
        lengthClimber.setBothPercent(0.0);
        rotationClimber.setRotationPercent(0.0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
