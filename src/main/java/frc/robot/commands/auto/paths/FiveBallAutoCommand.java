package frc.robot.commands.auto.paths;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.auto.TrajectoryCommandGenerator;
import frc.robot.commands.drive.RotateDriveCommand;
import frc.robot.commands.feeder.LoadBallToWaitingZoneCommand;
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

// Right up to hub facing toward it. Directly towards vertex in tarmac
public class FiveBallAutoCommand extends SequentialCommandGroup {
    public FiveBallAutoCommand(DriveTrain driveTrain, Intake intake, Shooter shooter, Feeder feeder, Spinners spinners,
            LimeLight limeLight) {
        super(new ShooterAimStateCommand(AimState.CLOSE, shooter),
                new OneBallShootSequenceCommand(ShooterConstants.CLOSE_DISTANCE_RPM, feeder, shooter, spinners),
                new RotateDriveCommand(104, driveTrain), new IntakeDeployCommand(intake),
                deadline(TrajectoryCommandGenerator.getCommandFromFile("5BallA", driveTrain),
                        new LoadBallToWaitingZoneCommand(feeder, spinners), new IntakeRunCommand(intake)),
                new ShooterAimStateCommand(AimState.FAR, shooter),
                new LimeLightAllAlignCommand(3.436, limeLight, driveTrain),
                new TwoBallShootSequenceCommand(ShooterConstants.FAR_DISTANCE_RPM, feeder, shooter, spinners),
                new RotateDriveCommand(180.0, driveTrain),
                deadline(
                        sequence(TrajectoryCommandGenerator.getCommandFromFile("5BallB", driveTrain),
                                new WaitCommand(0.5)),
                        new LoadBallToWaitingZoneCommand(feeder, spinners), new IntakeRunCommand(intake)),
                TrajectoryCommandGenerator.getCommandFromFile("5BallC", driveTrain),
                new LimeLightAllAlignCommand(3.436, limeLight, driveTrain),
                new TwoBallShootSequenceCommand(ShooterConstants.FAR_DISTANCE_RPM, feeder, shooter, spinners));
    }
}
