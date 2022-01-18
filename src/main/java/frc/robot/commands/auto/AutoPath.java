package frc.robot.commands.auto;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drive.DriveDistancePID;
import frc.robot.subsystems.DriveTrain;

public class AutoPath extends SequentialCommandGroup {
    public AutoPath(DriveTrain driveTrain) {
        super(new DriveDistancePID(2, driveTrain));
    }
}
