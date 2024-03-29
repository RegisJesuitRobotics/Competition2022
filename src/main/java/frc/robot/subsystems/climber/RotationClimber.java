package frc.robot.subsystems.climber;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMaxLowLevel.PeriodicFrame;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.ClimberConstants.*;

public class RotationClimber extends SubsystemBase {
    private final CANSparkMax leftClimberRotation = new CANSparkMax(LEFT_CLIMBER_ROTATION_PORT,
            CANSparkMaxLowLevel.MotorType.kBrushless);
    private final CANSparkMax rightClimberRotation = new CANSparkMax(RIGHT_CLIMBER_ROTATION_PORT,
            CANSparkMaxLowLevel.MotorType.kBrushless);

    public RotationClimber() {
        rightClimberRotation.restoreFactoryDefaults();
        leftClimberRotation.restoreFactoryDefaults();

        leftClimberRotation.setPeriodicFramePeriod(PeriodicFrame.kStatus1, 255);
        leftClimberRotation.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 255);
        leftClimberRotation.setPeriodicFramePeriod(PeriodicFrame.kStatus3, 255);
        // Follower motor and we don't care about anything
        rightClimberRotation.setPeriodicFramePeriod(PeriodicFrame.kStatus0, 255);
        rightClimberRotation.setPeriodicFramePeriod(PeriodicFrame.kStatus1, 255);
        rightClimberRotation.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 255);
        rightClimberRotation.setPeriodicFramePeriod(PeriodicFrame.kStatus3, 255);


        rightClimberRotation.follow(leftClimberRotation, true);

        rightClimberRotation.setSmartCurrentLimit(30);
        leftClimberRotation.setSmartCurrentLimit(30);

        leftClimberRotation.burnFlash();
        rightClimberRotation.burnFlash();
    }

    public void setRotationPercent(double percent) {
        leftClimberRotation.set(-percent);
    }
}
