package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.feeder.LoadBallToWaitingZoneAndCheckColorIfShouldCommand;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.intake.Spinners;

import java.util.function.BooleanSupplier;

public class IntakeRunAndLoadBallCommand extends ParallelCommandGroup {
    public IntakeRunAndLoadBallCommand(BooleanSupplier shouldEjectBalls, Feeder feeder, Intake intake, Shooter shooter,
            Spinners spinners) {
        super(new IntakeRunCommand(intake),
                new LoadBallToWaitingZoneAndCheckColorIfShouldCommand(shouldEjectBalls, feeder, shooter, spinners));
    }
}
