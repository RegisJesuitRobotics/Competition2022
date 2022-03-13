package frc.robot.commands.auto.paths;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.auto.TrajectoryCommandGenerator;
import frc.robot.commands.drive.RotateDriveCommand;
import frc.robot.commands.feeder.LoadBallToWaitingZoneCommand;
import frc.robot.commands.intake.IntakeRunCommand;
import frc.robot.commands.limelight.LimeLightAllAlignCommand;
import frc.robot.commands.shooter.TwoBallShootSequenceCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.intake.Spinners;

public class FiveBallAutoCommand extends SequentialCommandGroup {
    public FiveBallAutoCommand(DriveTrain driveTrain, Intake intake, Shooter shooter, Feeder feeder, Spinners spinners,
            LimeLight limeLight) {
        super(new ThreeBallAutoCommand(driveTrain, intake, shooter, feeder, spinners, limeLight),
                new RotateDriveCommand(180.0, driveTrain), new WaitCommand(0.5),
                deadline(
                        sequence(TrajectoryCommandGenerator.getCommandFromFile("5BallB", driveTrain),
                                new WaitCommand(0.5)),
                        new LoadBallToWaitingZoneCommand(feeder, spinners), new IntakeRunCommand(intake)),
                TrajectoryCommandGenerator.getCommandFromFile("5BallC", driveTrain),
                new LimeLightAllAlignCommand(-1, limeLight, driveTrain),
                new TwoBallShootSequenceCommand(ShooterConstants.FAR_DISTANCE_RPM, feeder, shooter, spinners));
    }
}
