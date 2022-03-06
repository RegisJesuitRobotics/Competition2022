package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.ClimberConstants.LEFT_CLIMBER_LENGTH_PORT;
import static frc.robot.Constants.ClimberConstants.RIGHT_CLIMBER_LENGTH_PORT;

public class LengthClimber extends SubsystemBase {
    private final CANSparkMax leftClimberLength = new CANSparkMax(LEFT_CLIMBER_LENGTH_PORT,
            CANSparkMaxLowLevel.MotorType.kBrushless);
    private final CANSparkMax rightClimberLength = new CANSparkMax(RIGHT_CLIMBER_LENGTH_PORT,
            CANSparkMaxLowLevel.MotorType.kBrushless);

    private final RelativeEncoder leftEncoder = leftClimberLength.getEncoder();
    private final RelativeEncoder rightEncoder = rightClimberLength.getEncoder();

    public LengthClimber() {
        rightClimberLength.restoreFactoryDefaults();
        leftClimberLength.restoreFactoryDefaults();

        leftClimberLength.setInverted(true);
        rightClimberLength.follow(leftClimberLength, true);

        leftEncoder.setPosition(0.0);
        rightEncoder.setPosition(0.0);
        leftEncoder.setPositionConversionFactor(1 / 75.0);
        rightEncoder.setPositionConversionFactor(1 / 75.0);

        Shuffleboard.getTab("Climber").addNumber("Left", leftEncoder::getPosition);
        Shuffleboard.getTab("Climber").addNumber("Right", rightEncoder::getPosition);

        rightClimberLength.burnFlash();
        leftClimberLength.burnFlash();
    }

    public void setLengthPercent(double percent) {
        leftClimberLength.set(percent);
    }
}
