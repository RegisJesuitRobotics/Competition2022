package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import static frc.robot.Constants.ClimberConstants.LEFT_CLIMBER_LENGTH_PORT;
import static frc.robot.Constants.ClimberConstants.RIGHT_CLIMBER_LENGTH_PORT;

public class LengthClimber extends SubsystemBase {
    private final CANSparkMax leftClimberLength = new CANSparkMax(LEFT_CLIMBER_LENGTH_PORT,
            CANSparkMaxLowLevel.MotorType.kBrushless);
    private final CANSparkMax rightClimberLength = new CANSparkMax(RIGHT_CLIMBER_LENGTH_PORT,
            CANSparkMaxLowLevel.MotorType.kBrushless);
    
    
    public RelativeEncoder leftEncoder = leftClimberLength.getEncoder();
    private final double MAX_CLIMBER_LENGTH_ENCODER_VALUE = Constants.ClimberConstants.MAX_CLIMBER_LENGTH_ENCODER_VALUE;
    private final double MIN_CLIMBER_LENGTH_ENCODER_VALUE = Constants.ClimberConstants.MIN_CLIMBER_LENGTH_ENCODER_VALUE;

    public LengthClimber() {
        rightClimberLength.restoreFactoryDefaults();
        leftClimberLength.restoreFactoryDefaults();

        leftClimberLength.setInverted(true);
        rightClimberLength.follow(leftClimberLength, true);

        rightClimberLength.burnFlash();
        leftClimberLength.burnFlash();
    }

    public void setLengthPercent(double percent) {
        if (leftEncoder.getPosition() > MAX_CLIMBER_LENGTH_ENCODER_VALUE && percent > 0){
            return;
        } else if (leftEncoder.getPosition() < MIN_CLIMBER_LENGTH_ENCODER_VALUE && percent < 0){
            return;
        }
        leftClimberLength.set(percent);
    }
}
