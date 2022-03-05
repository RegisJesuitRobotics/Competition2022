package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import frc.robot.commands.feeder.RunFeederCommand;
import frc.robot.commands.intake.SpinnersRunCommand;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.intake.Spinners;

public class RunShooterAndFeederCommand extends ParallelRaceGroup {
    public RunShooterAndFeederCommand(double shooterRPM, Shooter shooter, Feeder feeder, Spinners spinners) {
        super(new RunShooterCommand(shooterRPM, shooter),
                sequence(new WaitForShooterWarmupCommand(shooterRPM - 50, shooter),
                        parallel(new RunFeederCommand(feeder), new SpinnersRunCommand(1.0, spinners))));
    }
}
