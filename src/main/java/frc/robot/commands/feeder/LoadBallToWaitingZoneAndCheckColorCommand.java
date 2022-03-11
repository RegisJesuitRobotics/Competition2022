package frc.robot.commands.feeder;

import edu.wpi.first.wpilibj2.command.ScheduleCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.FeederConstants;
import frc.robot.commands.intake.SpinnersRunCommand;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.intake.Spinners;

public class LoadBallToWaitingZoneAndCheckColorCommand extends SequentialCommandGroup {
    public LoadBallToWaitingZoneAndCheckColorCommand(Feeder feeder, Shooter shooter, Spinners spinners) {
        super(new WaitForBallDetectedCommand(feeder), new FeederRunCommand(FeederConstants.FEEDER_SPEED, feeder),
                new SpinnersRunCommand(spinners),
                new ScheduleCommand(new ExpelIfBallWrongColorCommand(feeder, shooter, spinners)));
    }
}
