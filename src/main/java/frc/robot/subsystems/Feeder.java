package frc.robot.subsystems;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

    private double lastConfidence = 0.0;

    private final CANSparkMax feederMotor = new CANSparkMax(FEEDER_PORT, MotorType.kBrushless);
    private final RelativeEncoder feederEncoder = feederMotor.getEncoder();
    private final ColorSensorV3 feederSensor = new ColorSensorV3(Port.kOnboard);
    private final ColorMatch colorMatch = new ColorMatch();

    public Feeder() {
        feederMotor.restoreFactoryDefaults();
        feederMotor.setInverted(true);
        feederMotor.setIdleMode(IdleMode.kCoast);
        feederMotor.burnFlash();

        colorMatch.addColorMatch(FieldConstants.BLUE_BALL_COLOR);
        colorMatch.addColorMatch(FieldConstants.RED_BALL_COLOR);

        Shuffleboard.getTab("ColorSensor").addString("Detected", () -> getSensorStatus().name());
        Shuffleboard.getTab("ColorSensor").addNumber("Confidence", () -> lastConfidence);
    }

    public void setFeederPercent(double percent) {
        feederMotor.set(percent);
    }

    public FeederSensorStatus getSensorStatus() {
        ColorMatchResult matched = colorMatch.matchClosestColor(feederSensor.getColor());

        // If below minimum confidence level
        lastConfidence = matched.confidence;
        if (matched.confidence < FEEDER_SENSOR_CONFIDENCE_LEVEL) {
            return FeederSensorStatus.NOTHING;
        }
        if (matched.color.equals(FieldConstants.BLUE_BALL_COLOR)) {
            return FeederSensorStatus.BLUE_BALL;
        }
        return FeederSensorStatus.RED_BALL;
    }

    public double getEncoderRotations() {
        return feederEncoder.getPosition();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("R", feederSensor.getColor().red);
        SmartDashboard.putNumber("G", feederSensor.getColor().green);
        SmartDashboard.putNumber("B", feederSensor.getColor().blue);
    }
}
