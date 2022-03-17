package frc.robot.commands.auto.paths;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.auto.TrajectoryCommandGenerator;
import frc.robot.subsystems.DriveTrain;

public class TarmacOnlyCommand extends SequentialCommandGroup {
    public TarmacOnlyCommand(DriveTrain driveTrain) {
        super(TrajectoryCommandGenerator.getCommandFromFile("2MeterBack", driveTrain));
    }
}
