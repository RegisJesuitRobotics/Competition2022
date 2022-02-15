package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.ClimberConstants.*;

public class Climber extends SubsystemBase {

    private final CANSparkMax leftClimberLength = new CANSparkMax(LEFT_CLIMBER_LENGTH_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final CANSparkMax leftClimberRotation = new CANSparkMax(LEFT_CLIMBER_ROTATION_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final CANSparkMax rightClimberLength = new CANSparkMax(RIGHT_CLIMBER_LENGTH_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final CANSparkMax rightClimberRotation = new CANSparkMax(RIGHT_CLIMBER_LENGTH_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);

    public Climber() {
        rightClimberLength.follow(leftClimberLength);
        rightClimberRotation.follow(leftClimberRotation);
    }

    public void ClimberLength(double percent) {
        leftClimberLength.set(percent);
    }

    public void ClimberRotation(double percent) {
        leftClimberRotation.set(percent);
    }
}
