package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.feeder.RunFeederCommand;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Shooter;

public class RunShooterAndFeederCommand extends SequentialCommandGroup {
    public RunShooterAndFeederCommand(double shooterRPM, Shooter shooter, Feeder feeder) {
        super(new RunShooterCommand(shooterRPM, shooter), new WaitForShooterWarmup(shooterRPM, shooter),
                new RunFeederCommand(feeder));
    }
}
