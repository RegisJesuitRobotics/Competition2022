package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.feeder.RunFeederCommand;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Shooter;

public class RunShooterAndFeederCommand extends ParallelRaceGroup {
    public RunShooterAndFeederCommand(double shooterRPM, Shooter shooter, Feeder feeder) {
        super(new RunShooterCommand(shooterRPM, shooter), new SequentialCommandGroup(
                new WaitForShooterWarmupCommand(shooterRPM - 50, shooter), new RunFeederCommand(feeder)));
    }
}
