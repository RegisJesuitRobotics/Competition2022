package frc.robot.commands.feeder;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import frc.robot.Constants.FeederConstants;
import frc.robot.subsystems.Feeder;

public class LoadBallToWaitingZoneCommand extends ParallelDeadlineGroup {

    public LoadBallToWaitingZoneCommand(Feeder feeder) {
        super(new WaitForBallDetectedCommand(feeder), new FeederRunCommand(FeederConstants.FEEDER_SPEED, feeder));
    }
}
