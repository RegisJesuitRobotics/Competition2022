package frc.robot.commands.feeder;

import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants.FeederConstants;
import frc.robot.commands.intake.SpinnersRunCommand;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.intake.Spinners;

import static edu.wpi.first.wpilibj2.command.CommandGroupBase.deadline;

public class LoadBallToWaitingZoneCommand extends ConditionalCommand {

    public LoadBallToWaitingZoneCommand(Feeder feeder, Spinners spinners) {
        super(deadline(new WaitForBallDetectedCommand(feeder),
                new FeederRunCommand(FeederConstants.FEEDER_SPEED, feeder), new SpinnersRunCommand(spinners)),
                new InstantCommand(),
                // Run if there is not a ball loaded
                () -> !feeder.isBallLoaded());
    }
}
