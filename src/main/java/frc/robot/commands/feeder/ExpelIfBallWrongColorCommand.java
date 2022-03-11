package frc.robot.commands.feeder;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.FinishInstantlyCommand;
import frc.robot.commands.shooter.OneBallShootSequenceCommand;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Feeder.FeederSensorStatus;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.intake.Spinners;

public class ExpelIfBallWrongColorCommand extends ConditionalCommand {
    public ExpelIfBallWrongColorCommand(Feeder feeder, Shooter shooter, Spinners spinners) {
        super(new OneBallShootSequenceCommand(ShooterConstants.EXPEL_BALL_RPM, feeder, shooter, spinners),
                new FinishInstantlyCommand(),
                // If we are red alliance then run this if ball is blue
                () -> feeder.getSensorStatus() == ((DriverStation.getAlliance() == Alliance.Red)
                        ? FeederSensorStatus.BLUE_BALL
                        : FeederSensorStatus.RED_BALL));
    }
}
