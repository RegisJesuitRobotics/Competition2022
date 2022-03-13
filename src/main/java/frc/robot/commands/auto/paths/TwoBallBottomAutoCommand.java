package frc.robot.commands.auto.paths;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.auto.TrajectoryCommandGenerator;
import frc.robot.commands.drive.RotateDriveCommand;
import frc.robot.commands.feeder.FeedOneBallToShooterCommand;
import frc.robot.commands.intake.IntakeDeployCommand;
import frc.robot.commands.intake.IntakeRunCommand;
import frc.robot.commands.intake.IntakeUnDeployCommand;
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

// edge of bot (without bumpers) 1.25m away from edge of ball
public class TwoBallBottomAutoCommand extends SequentialCommandGroup {
    public TwoBallBottomAutoCommand(DriveTrain driveTrain, Intake intake, Shooter shooter, Feeder feeder,
            Spinners spinners, LimeLight limeLight) {
        super(new IntakeDeployCommand(intake),
                deadline(TrajectoryCommandGenerator.getCommandFromFile("2BallBottomA", driveTrain),
                        new FeedOneBallToShooterCommand(feeder), new IntakeRunCommand(intake)),
                new IntakeUnDeployCommand(intake), new ShooterAimStateCommand(AimState.FAR, shooter),
                new RotateDriveCommand(170.0, driveTrain), new LimeLightAllAlignCommand(-1, limeLight, driveTrain),
                new TwoBallShootSequenceCommand(ShooterConstants.FAR_DISTANCE_RPM, feeder, shooter, spinners),
                new IntakeDeployCommand(intake),
                deadline(TrajectoryCommandGenerator.getCommandFromFile("2BallBottomB", driveTrain),
                        new FeedOneBallToShooterCommand(feeder), new IntakeRunCommand(intake)),
                TrajectoryCommandGenerator.getCommandFromFile("2BallBottomC", driveTrain),
                new OneBallShootSequenceCommand(ShooterConstants.EXPEL_BALL_RPM, feeder, shooter, spinners));
    }
}
