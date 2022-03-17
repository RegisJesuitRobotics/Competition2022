package frc.robot.commands.limelight;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.LimeLightConstants;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.LimeLight.LightMode;

public class LimeLightAlignCommand extends CommandBase {
    private final LimeLight limeLight;
    private final DriveTrain driveTrain;

    /**
     * @param limeLight  limelight subsystems
     * @param driveTrain driveTrain subsystem
     */
    public LimeLightAlignCommand(LimeLight limeLight, DriveTrain driveTrain) {
        this.driveTrain = driveTrain;
        this.limeLight = limeLight;

        addRequirements(driveTrain);
    }

    @Override
    public void initialize() {
        limeLight.setLightMode(LimeLight.LightMode.ON);
    }

    @Override
    public void execute() {
        double angleOutput = LimeLightConstants.P_LIMELIGHT_ANGLE * limeLight.getHorizontalOffset()
                + LimeLightConstants.ARB_FF_LIMELIGHT_ANGLE * Math.signum(limeLight.getHorizontalOffset());
        driveTrain.tankDrive(-angleOutput, angleOutput);
    }

    @Override
    public void end(boolean interrupted) {
        driveTrain.tankDrive(0.0, 0.0);
        limeLight.setLightMode(LightMode.OFF);
    }

    @Override
    public boolean isFinished() {
//        return limeLight.hasValidTarget()
//                && Math.abs(limeLight.getHorizontalOffset()) < LimeLightConstants.LIMELIGHT_ANGLE_ACCEPTABLE_ERROR_ANGLE
//                && Math.abs(distanceError) < LimeLightConstants.LIMELIGHT_DISTANCE_ACCEPTABLE_ERROR_METERS;
        return Math.abs(limeLight.getHorizontalOffset()) < LimeLightConstants.LIMELIGHT_ANGLE_ACCEPTABLE_ERROR_ANGLE;
    }
}
