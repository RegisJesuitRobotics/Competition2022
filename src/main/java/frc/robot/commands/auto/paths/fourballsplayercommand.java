package frc.robot.commands.auto.paths;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.auto.TrajectoryCommandGenerator;
import frc.robot.commands.intake.IntakeDeployCommand;
import frc.robot.commands.intake.IntakeSpinnersRunCommand;
import frc.robot.commands.limelight.LimeLightAllAlignCommand;
import frc.robot.commands.shooter.RunShooterAndFeederCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.intake.Spinners;

public class fourballsplayercommand extends SequentialCommandGroup {
    public fourballsplayercommand(DriveTrain driveTrain, Intake intake, Spinners spinners, Shooter shooter,
             LimeLight limeLight, Feeder feeder) {
        super(new IntakeDeployCommand(intake), // hamburger
        race(new IntakeSpinnersRunCommand(intake, spinners),
                TrajectoryCommandGenerator.getCommandFromFile("fourballsfieldcommand", driveTrain)),
        new LimeLightAllAlignCommand(-1, limeLight, driveTrain),
        new RunShooterAndFeederCommand(ShooterConstants.FAR_DISTANCE_RPM, shooter, feeder));
}
}
