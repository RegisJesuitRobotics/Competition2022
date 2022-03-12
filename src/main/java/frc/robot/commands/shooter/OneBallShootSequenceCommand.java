package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.feeder.FeedOneBallToShooterCommand;
import frc.robot.commands.feeder.LoadBallToWaitingZoneCommand;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.intake.Spinners;

public class OneBallShootSequenceCommand extends ParallelRaceGroup {
    public OneBallShootSequenceCommand(double shooterRPM, Feeder feeder, Shooter shooter, Spinners spinners) {
        super(new ShooterRunCommand(shooterRPM, shooter), sequence(
                // Wait for the shooter to warmup and while that is happening prepare the ball
                deadline(new WaitForShooterWarmupCommand(shooterRPM, shooter),
                        new LoadBallToWaitingZoneCommand(feeder, spinners)),
                // Feed the ball as it has warmed up
                new FeedOneBallToShooterCommand(feeder),
                // Make sure the ball is through before stopping the shooter
                new WaitCommand(0.3)));
    }
}
