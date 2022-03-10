package frc.robot.commands.auto.paths;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.auto.TrajectoryCommandGenerator;
import frc.robot.commands.drive.RotateDriveCommand;
import frc.robot.commands.feeder.FeedOneBallCommand;
import frc.robot.commands.intake.IntakeDeployCommand;
import frc.robot.commands.intake.IntakeRunCommand;
import frc.robot.commands.limelight.LimeLightAllAlignCommand;
import frc.robot.commands.shooter.OneBallShootSequenceCommand;
import frc.robot.commands.shooter.ShooterAimStateCommand;
import frc.robot.commands.shooter.TwoBallShootSequenceCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shooter.AimState;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.intake.Spinners;

// Bot straight on ball with center of bot 2.145m away from edge of ball
public class TwoBallBottomAutoCommand extends SequentialCommandGroup {
    public TwoBallBottomAutoCommand(DriveTrain driveTrain, Intake intake, Shooter shooter, Feeder feeder,
            Spinners spinners, LimeLight limeLight) {
        super(new IntakeDeployCommand(intake),
                deadline(TrajectoryCommandGenerator.getCommandFromFile("2BallBottomA", driveTrain),
                        new FeedOneBallCommand(feeder), new IntakeRunCommand(intake)),
                new ShooterAimStateCommand(AimState.FAR, shooter), new RotateDriveCommand(171.0, driveTrain),
                new LimeLightAllAlignCommand(-1, limeLight, driveTrain),
                new TwoBallShootSequenceCommand(ShooterConstants.FAR_DISTANCE_RPM, feeder, shooter, spinners),
                deadline(TrajectoryCommandGenerator.getCommandFromFile("2BallBottomB", driveTrain),
                        new FeedOneBallCommand(feeder), new IntakeRunCommand(intake)),
                new OneBallShootSequenceCommand(ShooterConstants.EXPEL_BALL_RPM, feeder, shooter, spinners));
    }
}
