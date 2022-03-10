package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.feeder.FeedOneBallCommand;
import frc.robot.commands.feeder.LoadBallToWaitingZoneCommand;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.intake.Spinners;

public class TwoBallShootSequenceCommand extends ParallelRaceGroup {
    public TwoBallShootSequenceCommand(double shooterRPM, Feeder feeder, Shooter shooter, Spinners spinners) {
        super(new ShooterRunCommand(shooterRPM, shooter), sequence(
                // Wait for the shooter to warmup and while that is happening prepare the ball
                deadline(new WaitForShooterWarmupCommand(shooterRPM, shooter),
                        new LoadBallToWaitingZoneCommand(feeder)),
                // Feed the ball as it has warmed up
                new FeedOneBallCommand(feeder),
                // Get shooter back up to speed and prepare ball
                deadline(new WaitForShooterWarmupCommand(shooterRPM, shooter),
                        new LoadBallToWaitingZoneCommand(feeder)),
                // Feed the ball to shooter
                new FeedOneBallCommand(feeder),
                // Wait some time to make sure the ball is shot before stopping shooter
                new WaitCommand(0.3)));
    }
}
