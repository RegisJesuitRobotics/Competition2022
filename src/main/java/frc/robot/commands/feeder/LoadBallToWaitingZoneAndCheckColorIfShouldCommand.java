package frc.robot.commands.feeder;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.intake.Spinners;

import java.util.function.BooleanSupplier;

public class LoadBallToWaitingZoneAndCheckColorIfShouldCommand extends SequentialCommandGroup {
    public LoadBallToWaitingZoneAndCheckColorIfShouldCommand(BooleanSupplier shouldEjectBalls, Feeder feeder,
            Shooter shooter, Spinners spinners) {
        super(new LoadBallToWaitingZoneCommand(feeder, spinners).asProxy(),
                new ConditionalCommand(
                        sequence(new WaitCommand(0.08),
                                new ScheduleCommand(new ExpelIfBallWrongColorCommand(feeder, shooter, spinners))),
                        new InstantCommand(), shouldEjectBalls));
    }
}
