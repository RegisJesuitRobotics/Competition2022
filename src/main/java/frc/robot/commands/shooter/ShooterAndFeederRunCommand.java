package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.FeederConstants;
import frc.robot.commands.feeder.FeederAndSpinnersRunCommand;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.intake.Spinners;

public class ShooterAndFeederRunCommand extends ParallelRaceGroup {
    public ShooterAndFeederRunCommand(double shooterRPM, Shooter shooter, Feeder feeder, Spinners spinners) {
        super(
                new ShooterRunCommand(shooterRPM, shooter),
                sequence(
                        new WaitForShooterWarmupCommand(shooterRPM, shooter), new WaitCommand(0.5),
                        sequence(
                                new FeederAndSpinnersRunCommand(FeederConstants.FEEDER_SPEED, feeder, spinners)
                                        .withTimeout(0.3),
                                new WaitCommand(0.5),
                                new FeederAndSpinnersRunCommand(FeederConstants.FEEDER_SPEED, feeder, spinners)
                        )
                )
        );
    }
}
