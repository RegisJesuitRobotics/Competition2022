package frc.robot.commands.feeder;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import frc.robot.Constants.FeederConstants;
import frc.robot.commands.intake.SpinnersRunCommand;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.intake.Spinners;

public class LoadBallToWaitingZoneCommand extends ParallelDeadlineGroup {

    public LoadBallToWaitingZoneCommand(Feeder feeder, Spinners spinners) {
        super(new WaitForBallDetectedCommand(feeder), new FeederRunCommand(FeederConstants.FEEDER_SPEED, feeder),
                new SpinnersRunCommand(spinners));
    }
}
