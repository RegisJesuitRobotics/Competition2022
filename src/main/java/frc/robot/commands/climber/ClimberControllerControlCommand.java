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
        boolean bothUp = logitechController.buttonEight.get();
        boolean bothDown = logitechController.buttonNine.get();
        if (bothUp || bothDown) {
            if (bothUp && bothDown) {
                // Both are pressed
                lengthClimber.setBothPercent(0.0);
            } else if (bothUp) {
                // Up is pressed
                lengthClimber.setBothPercent(ClimberConstants.LENGTH_SPEED);
            } else {
                // Down is pressed
                lengthClimber.setBothPercent(-ClimberConstants.LENGTH_SPEED);
            }
            return;
        }

        // Either left-climbers buttons pressed
        boolean leftUp = logitechController.buttonSix.get();
        boolean leftDown = logitechController.buttonSeven.get();
        if (leftUp || leftDown) {
            if (leftUp && leftDown) {
                // Both are pressed
                lengthClimber.setLeftPercent(0.0);
            } else if (leftUp) {
                // Up is pressed
                lengthClimber.setLeftPercent(ClimberConstants.LENGTH_SPEED);
            } else {
                // Down is pressed
                lengthClimber.setLeftPercent(-ClimberConstants.LENGTH_SPEED);
            }
            // No early return because we need to check right still
        }

        // Either right-climbers buttons pressed
        boolean rightUp = logitechController.buttonTen.get();
        boolean rightDown = logitechController.buttonEleven.get();
        if (rightUp || rightDown) {
            if (rightUp && rightDown) {
                // Both are pressed
                lengthClimber.setRightPercent(0.0);
            } else if (rightUp) {
                // Up is pressed
                lengthClimber.setRightPercent(ClimberConstants.LENGTH_SPEED);
            } else {
                // Down is pressed
                lengthClimber.setRightPercent(-ClimberConstants.LENGTH_SPEED);
            }
        }

        boolean bothForward = logitechController.buttonFour.get();
        boolean bothBackward = logitechController.buttonFive.get();
        if (bothForward || bothBackward) {
            if (bothForward && bothBackward) {
                rotationClimber.setRotationPercent(0.0);
            } else if (bothForward) {
                rotationClimber.setRotationPercent(ClimberConstants.ROTATION_SPEED);
            } else {
                rotationClimber.setRotationPercent(-ClimberConstants.ROTATION_SPEED);
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
