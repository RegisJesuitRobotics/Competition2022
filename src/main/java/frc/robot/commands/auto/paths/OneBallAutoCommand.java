package frc.robot.commands.auto.paths;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.auto.TrajectoryCommandGenerator;
import frc.robot.commands.intake.IntakeDeployCommand;
import frc.robot.commands.shooter.OneBallShootSequenceCommand;
import frc.robot.commands.shooter.ShooterAimStateCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shooter.AimState;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.intake.Spinners;

public class OneBallAutoCommand extends SequentialCommandGroup {
    public OneBallAutoCommand(DriveTrain driveTrain, Intake intake, Shooter shooter, Feeder feeder, Spinners spinners) {
        super(new ShooterAimStateCommand(AimState.CLOSE, shooter),
                new OneBallShootSequenceCommand(ShooterConstants.CLOSE_DISTANCE_RPM, feeder, shooter, spinners),
                TrajectoryCommandGenerator.getCommandFromFile("1MeterBack", driveTrain),
                new IntakeDeployCommand(intake));
    }
}
