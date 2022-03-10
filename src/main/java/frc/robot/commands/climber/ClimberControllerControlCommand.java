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
        double speed = logitechController.throttle.getAxis();
        boolean leftSet = false;
        boolean rightSet = false;

        boolean bothRetract = logitechController.buttonTen.get();
        boolean bothExtend = logitechController.buttonNine.get();
        if (bothExtend || bothRetract) {
            if (bothExtend && bothRetract) {
                // Both are pressed
                lengthClimber.setBothPercent(0.0);
            } else if (bothExtend) {
                // Up is pressed
                lengthClimber.setBothPercent(-speed);
            } else {
                // Down is pressed
                lengthClimber.setBothPercent(speed);
            }
            return;
        }

        // Either left-climbers buttons pressed
        boolean leftRetract = logitechController.buttonTwelve.get();
        boolean leftExtend = logitechController.buttonEleven.get();
        if (leftExtend || leftRetract) {
            leftSet = true;
            if (leftExtend && leftRetract) {
                // Both are pressed
                lengthClimber.setLeftPercent(0.0);
            } else if (leftExtend) {
                // Up is pressed
                lengthClimber.setLeftPercent(-speed);
            } else {
                // Down is pressed
                lengthClimber.setLeftPercent(speed);
            }
            // No early return because we need to check right still
        }

        // Either right-climbers buttons pressed
        boolean rightRetract = logitechController.buttonEight.get();
        boolean rightExtend = logitechController.buttonSeven.get();
        if (rightExtend || rightRetract) {
            rightSet = true;
            if (rightExtend && rightRetract) {
                // Both are pressed
                lengthClimber.setRightPercent(0.0);
            } else if (rightExtend) {
                // Up is pressed
                lengthClimber.setRightPercent(-speed);
            } else {
                // Down is pressed
                lengthClimber.setRightPercent(speed);
            }
        }

        if (!rightSet) {
            lengthClimber.setRightPercent(0.0);
        }
        if (!leftSet) {
            lengthClimber.setLeftPercent(0.0);
        }


        boolean bothForward = logitechController.buttonThree.get();
        boolean bothBackward = logitechController.buttonFour.get();
        if (bothForward || bothBackward) {
            if (bothForward && bothBackward) {
                rotationClimber.setRotationPercent(0.0);
            } else if (bothForward) {
                rotationClimber.setRotationPercent(ClimberConstants.ROTATION_SPEED);
            } else {
                rotationClimber.setRotationPercent(-ClimberConstants.ROTATION_SPEED);
            }
        } else {
            rotationClimber.setRotationPercent(0.0);
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
