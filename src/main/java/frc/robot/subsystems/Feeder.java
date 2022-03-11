package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.FieldConstants;

import static frc.robot.Constants.FeederConstants.FEEDER_PORT;
import static frc.robot.Constants.FeederConstants.FEEDER_SENSOR_CONFIDENCE_LEVEL;

public class Feeder extends SubsystemBase {
    public enum FeederSensorStatus {
        RED_BALL,
        BLUE_BALL,
        NOTHING
    }

    private final CANSparkMax feederMotor = new CANSparkMax(FEEDER_PORT, MotorType.kBrushless);
    private final ColorSensorV3 feederSensor = new ColorSensorV3(Port.kOnboard);
    private final ColorMatch colorMatch = new ColorMatch();

    public Feeder() {
        feederMotor.restoreFactoryDefaults();
        feederMotor.setInverted(true);
        feederMotor.setIdleMode(IdleMode.kCoast);
        feederMotor.burnFlash();

        colorMatch.addColorMatch(FieldConstants.BLUE_BALL_COLOR);
        colorMatch.addColorMatch(FieldConstants.RED_BALL_COLOR);
        colorMatch.setConfidenceThreshold(FEEDER_SENSOR_CONFIDENCE_LEVEL);
    }

    public void setFeederPercent(double percent) {
        feederMotor.set(percent);
    }

    public FeederSensorStatus getSensorStatus() {
        ColorMatchResult matched = colorMatch.matchColor(feederSensor.getColor());

        // If below minimum confidence level
        if (matched == null) {
            return FeederSensorStatus.NOTHING;
        }
        if (matched.color.equals(FieldConstants.BLUE_BALL_COLOR)) {
            return FeederSensorStatus.BLUE_BALL;
        }
        return FeederSensorStatus.RED_BALL;
    }
}
