package frc.robot.commands.feeder;

import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import frc.robot.Constants.FeederConstants;
import frc.robot.commands.FinishInstantlyCommand;
import frc.robot.commands.intake.SpinnersRunCommand;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Feeder.FeederSensorStatus;
import frc.robot.subsystems.intake.Spinners;

import static edu.wpi.first.wpilibj2.command.CommandGroupBase.deadline;
import static edu.wpi.first.wpilibj2.command.CommandGroupBase.sequence;

public class LoadBallToWaitingZoneCommand extends ConditionalCommand {

    public LoadBallToWaitingZoneCommand(Feeder feeder, Spinners spinners) {
        super(sequence(
                deadline(new WaitForBallDetectedCommand(feeder),
                        new FeederRunCommand(FeederConstants.FEEDER_SPEED, feeder), new SpinnersRunCommand(spinners)),
                new FeederBackEncoderCommand(-0.25, feeder)), new FinishInstantlyCommand(),
                () -> feeder.getSensorStatus() == FeederSensorStatus.NOTHING);
    }
}
