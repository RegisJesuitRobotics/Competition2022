package frc.robot.subsystems.climber;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMaxLowLevel.PeriodicFrame;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.ClimberConstants.LEFT_CLIMBER_LENGTH_PORT;
import static frc.robot.Constants.ClimberConstants.RIGHT_CLIMBER_LENGTH_PORT;

public class LengthClimber extends SubsystemBase {
    private final CANSparkMax leftClimberLength = new CANSparkMax(LEFT_CLIMBER_LENGTH_PORT,
            CANSparkMaxLowLevel.MotorType.kBrushless);
    private final CANSparkMax rightClimberLength = new CANSparkMax(RIGHT_CLIMBER_LENGTH_PORT,
            CANSparkMaxLowLevel.MotorType.kBrushless);

    public LengthClimber() {
        rightClimberLength.restoreFactoryDefaults();
        leftClimberLength.restoreFactoryDefaults();

        leftClimberLength.setIdleMode(IdleMode.kBrake);
        rightClimberLength.setIdleMode(IdleMode.kBrake);

        leftClimberLength.setInverted(true);
        rightClimberLength.setInverted(false);

        leftClimberLength.setSmartCurrentLimit(40);
        rightClimberLength.setSmartCurrentLimit(40);

        leftClimberLength.setPeriodicFramePeriod(PeriodicFrame.kStatus1, 255);
        leftClimberLength.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 255);
        leftClimberLength.setPeriodicFramePeriod(PeriodicFrame.kStatus3, 255);

        rightClimberLength.setPeriodicFramePeriod(PeriodicFrame.kStatus1, 255);
        rightClimberLength.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 255);
        rightClimberLength.setPeriodicFramePeriod(PeriodicFrame.kStatus3, 255);

        rightClimberLength.burnFlash();
        leftClimberLength.burnFlash();
    }

    public void setBothPercent(double percent) {
        setLeftPercent(percent);
        setRightPercent(percent);
    }

    public void setLeftPercent(double percent) {
        leftClimberLength.set(percent);
    }

    public void setRightPercent(double percent) {
        rightClimberLength.set(percent);
    }
}
