package frc.robot.commands.shooter;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.limelight.LimeLightAllAlignCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.Shooter;

public class FarShootingLocationShootCommand extends SequentialCommandGroup {
    public FarShootingLocationShootCommand(Shooter shooter, DriveTrain driveTrain, LimeLight limeLight) {
        super(new LimeLightAllAlignCommand(Constants.ShooterConstants.FAR_SHOOTING_LOCATION_DISTANCE_METERS, limeLight,
                driveTrain));
    }
}
