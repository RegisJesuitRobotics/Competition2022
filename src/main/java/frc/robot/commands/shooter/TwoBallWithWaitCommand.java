package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.commands.feeder.FeedOneBallToShooterCommand;
import frc.robot.commands.feeder.LoadBallToWaitingZoneCommand;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.intake.Spinners;

import java.util.function.BooleanSupplier;

public class TwoBallWithWaitCommand extends ParallelRaceGroup {
    public TwoBallWithWaitCommand(double shooterRPM, BooleanSupplier shouldShootSupplier, Feeder feeder,
            Shooter shooter, Spinners spinners) {
        super(new ShooterRunCommand(shooterRPM, shooter), sequence(
                // Wait for the shooter to warmup and while that is happening prepare the ball
                parallel(new WaitForShooterWarmupCommand(shooterRPM, shooter),
                        new LoadBallToWaitingZoneCommand(feeder, spinners)),
                sequence(new WaitCommand(0.2), new WaitUntilCommand(shouldShootSupplier)),
                // Feed the ball as it has warmed up
                new FeedOneBallToShooterCommand(feeder),
                // Get shooter back up to speed and prepare ball
                parallel(new WaitForShooterWarmupCommand(shooterRPM, shooter),
                        new LoadBallToWaitingZoneCommand(feeder, spinners)),
                new WaitCommand(0.5),
                // Feed the ball to shooter
                new FeedOneBallToShooterCommand(feeder),
                // Wait some time to make sure the ball is shot before stopping shooter
                new WaitCommand(0.2)));
    }
}
