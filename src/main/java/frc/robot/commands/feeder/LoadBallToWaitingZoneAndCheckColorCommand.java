package frc.robot.commands.feeder;

import edu.wpi.first.wpilibj2.command.ProxyScheduleCommand;
import edu.wpi.first.wpilibj2.command.ScheduleCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.intake.Spinners;

public class LoadBallToWaitingZoneAndCheckColorCommand extends SequentialCommandGroup {
    public LoadBallToWaitingZoneAndCheckColorCommand(Feeder feeder, Shooter shooter, Spinners spinners) {
        super(
                new ProxyScheduleCommand(new LoadBallToWaitingZoneCommand(feeder, spinners)), new WaitCommand(0.08),
                new ScheduleCommand(new ExpelIfBallWrongColorCommand(feeder, shooter, spinners))
        );
    }
}
