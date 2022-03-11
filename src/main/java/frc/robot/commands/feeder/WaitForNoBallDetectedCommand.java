package frc.robot.commands.feeder;

import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Feeder.FeederSensorStatus;

public class WaitForNoBallDetectedCommand extends WaitUntilCommand {
    public WaitForNoBallDetectedCommand(Feeder feeder) {
        super(() -> feeder.getSensorStatus() == FeederSensorStatus.NOTHING);
    }

}
