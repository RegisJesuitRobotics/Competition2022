package frc.robot.commands.feeder;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import frc.robot.Constants.FeederConstants;
import frc.robot.subsystems.Feeder;

public class FeedOneBallCommand extends ParallelDeadlineGroup {

    public FeedOneBallCommand(Feeder feeder) {
        super(new WaitForNoBallDetectedCommand(feeder), new FeederRunCommand(FeederConstants.FEEDER_SPEED, feeder));
    }
}
