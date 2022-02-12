package frc.robot.commands.limelight;

import frc.robot.Constants.LimeLightConstants;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.LimeLight;

public class LimeLightAllAlignCommand extends LimeLightCommand {
    private final double targetDistance;
    private final DriveTrain driveTrain;
    private double distanceError = 0.0;

    /**
     * @param targetDistanceFromTarget -1 for do not align distance (meters)
     * @param limeLight                limelight subsystems
     * @param driveTrain               driveTrain subsystem
     */
    public LimeLightAllAlignCommand(double targetDistanceFromTarget, LimeLight limeLight, DriveTrain driveTrain) {
        super(limeLight);
        this.driveTrain = driveTrain;
        this.targetDistance = targetDistanceFromTarget;

        addRequirements(driveTrain);
    }

    @Override
    public void execute() {
        if (targetDistance > 0) {
            distanceError = limeLight.getEstimatedDistance() - targetDistance;
        }
        driveTrain.arcadeDrive(LimeLightConstants.P_LIMELIGHT_DISTANCE * distanceError,
                LimeLightConstants.P_LIMELIGHT_ANGLE * limeLight.getHorizontalOffset());
    }

    @Override
    public void end(boolean interrupted) {
        driveTrain.arcadeDrive(0, 0);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(limeLight.getHorizontalOffset()) < LimeLightConstants.LIMELIGHT_ANGLE_ACCEPTABLE_ERROR_ANGLE
                && Math.abs(distanceError) < LimeLightConstants.LIMELIGHT_DISTANCE_ACCEPTABLE_ERROR_METERS;
    }
}
