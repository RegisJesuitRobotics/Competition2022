package frc.robot.commands.limelight;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.LimeLightConstants;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.LimeLight.LightMode;

public class LimeLightAllAlignCommand extends CommandBase {
    private final LimeLight limeLight;
    private final DriveTrain driveTrain;
    private final double targetDistance;
    private double distanceError = 0.0;

    /**
     * @param targetDistanceFromTarget -1 for do not align distance (meters)
     * @param limeLight                limelight subsystems
     * @param driveTrain               driveTrain subsystem
     */
    public LimeLightAllAlignCommand(double targetDistanceFromTarget, LimeLight limeLight, DriveTrain driveTrain) {
        this.driveTrain = driveTrain;
        this.limeLight = limeLight;
        this.targetDistance = targetDistanceFromTarget;

        addRequirements(driveTrain);
    }

    @Override
    public void initialize() {
        limeLight.setLightMode(LimeLight.LightMode.ON);
    }

    @Override
    public void execute() {
        if (targetDistance > 0) {
            distanceError = limeLight.getEstimatedDistance() - targetDistance;
        }
        driveTrain.arcadeDrive(LimeLightConstants.P_LIMELIGHT_DISTANCE * distanceError,
                LimeLightConstants.P_LIMELIGHT_ANGLE * limeLight.getHorizontalOffset()
                        + LimeLightConstants.ARB_FF_LIMELIGHT_ANGLE * Math.signum(limeLight.getHorizontalOffset()));
    }

    @Override
    public void end(boolean interrupted) {
        driveTrain.arcadeDrive(0, 0);
        limeLight.setLightMode(LightMode.OFF);
    }

    @Override
    public boolean isFinished() {
        return limeLight.hasValidTarget()
                && Math.abs(limeLight.getHorizontalOffset()) < LimeLightConstants.LIMELIGHT_ANGLE_ACCEPTABLE_ERROR_ANGLE
                && Math.abs(distanceError) < LimeLightConstants.LIMELIGHT_DISTANCE_ACCEPTABLE_ERROR_METERS;
    }
}
