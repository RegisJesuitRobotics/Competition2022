package frc.robot.commands.auto.paths;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.auto.RunTrajectoryLoadingBallCommand;
import frc.robot.commands.drive.RotateDriveCommand;
import frc.robot.commands.intake.IntakeDeployCommand;
import frc.robot.commands.intake.IntakeUnDeployCommand;
import frc.robot.commands.limelight.LimeLightAlignCommand;
import frc.robot.commands.shooter.ShooterAimStateCommand;
import frc.robot.commands.shooter.TwoBallShootSequenceCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shooter.AimState;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.intake.Spinners;

public class TwoBallTopNoStealAutoCommand extends SequentialCommandGroup {
    public TwoBallTopNoStealAutoCommand(DriveTrain driveTrain, Intake intake, Shooter shooter, Feeder feeder,
            Spinners spinners, LimeLight limeLight) {
        super(new IntakeDeployCommand(intake),
                new RunTrajectoryLoadingBallCommand("2BallTopA", driveTrain, intake, spinners, feeder),
                new IntakeUnDeployCommand(intake), new ShooterAimStateCommand(AimState.FAR, shooter),
                new RotateDriveCommand(170, driveTrain), new LimeLightAlignCommand(limeLight, driveTrain),
                new TwoBallShootSequenceCommand(ShooterConstants.TWO_BALL_DISTANCE_RPM, feeder, shooter, spinners),
                new ShooterAimStateCommand(AimState.CLOSE, shooter));
    }
}
