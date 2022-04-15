package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ClimberConstants;
import frc.robot.joysticks.ThrustMaster;
import frc.robot.subsystems.climber.LengthClimber;
import frc.robot.subsystems.climber.RotationClimber;

public class ClimberControllerControlCommand extends CommandBase {
    private final ThrustMaster logitechController;
    private final LengthClimber lengthClimber;
    private final RotationClimber rotationClimber;

    public ClimberControllerControlCommand(
            ThrustMaster logitechController, LengthClimber lengthClimber, RotationClimber rotationClimber
    ) {
        this.logitechController = logitechController;
        this.lengthClimber = lengthClimber;
        this.rotationClimber = rotationClimber;

        addRequirements(lengthClimber, rotationClimber);
    }

    @Override
    public void execute() {
        // Either both-climbers buttons pressed
        double speed = Math.abs(logitechController.throttle.getAxis() - 1);

        // Either left-climbers buttons pressed
        boolean leftRetract = logitechController.buttonEight.get();
        boolean leftExtend = logitechController.buttonSeven.get();
        if (leftExtend || leftRetract) {
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
        } else {
            lengthClimber.setLeftPercent(0.0);
        }

        // Either right-climbers buttons pressed
        boolean rightRetract = logitechController.buttonNine.get();
        boolean rightExtend = logitechController.buttonSix.get();
        if (rightExtend || rightRetract) {
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
        } else {
            lengthClimber.setRightPercent(0.0);
        }

        boolean bothForward = logitechController.buttonFourteen.get();
        boolean bothBackward = logitechController.buttonFifteen.get();
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
