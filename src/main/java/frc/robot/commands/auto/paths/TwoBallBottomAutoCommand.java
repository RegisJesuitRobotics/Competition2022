package frc.robot.commands.auto.paths;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.auto.RunTrajectoryLoadingBallCommand;
import frc.robot.commands.auto.TrajectoryCommandGenerator;
import frc.robot.commands.drive.RotateDriveCommand;
import frc.robot.commands.intake.IntakeDeployCommand;
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

public class TwoBallBottomAutoCommand extends SequentialCommandGroup {
    public TwoBallBottomAutoCommand(DriveTrain driveTrain, Intake intake, Shooter shooter, Feeder feeder,
            Spinners spinners, LimeLight limeLight) {
        super(new IntakeDeployCommand(intake),
                new RunTrajectoryLoadingBallCommand("2BallBottomA", driveTrain, intake, spinners, feeder),
                new IntakeUnDeployCommand(intake), new ShooterAimStateCommand(AimState.FAR, shooter),
                new RotateDriveCommand(170.0, driveTrain), new LimeLightAllAlignCommand(-1, limeLight, driveTrain),
                new TwoBallShootSequenceCommand(ShooterConstants.FAR_DISTANCE_RPM, feeder, shooter, spinners),
                new IntakeDeployCommand(intake),
                new RunTrajectoryLoadingBallCommand("2BallBottomB", driveTrain, intake, spinners, feeder),
                new IntakeUnDeployCommand(intake),
                TrajectoryCommandGenerator.getCommandFromFile("2BallBottomC", driveTrain),
                new OneBallShootSequenceCommand(ShooterConstants.AUTO_EXPEL_RPM, feeder, shooter, spinners));
    }
}
