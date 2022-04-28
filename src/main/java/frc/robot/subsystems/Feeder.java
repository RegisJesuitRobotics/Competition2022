package frc.robot.subsystems;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMaxLowLevel.PeriodicFrame;
import com.revrobotics.ColorSensorV3.ProximitySensorMeasurementRate;
import com.revrobotics.ColorSensorV3.ProximitySensorResolution;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.FeederConstants.*;

public class Feeder extends SubsystemBase {
    public enum FeederDetectedColor {
        RED_BALL,
        BLUE_BALL,
    }

    private final CANSparkMax feederMotor = new CANSparkMax(FEEDER_PORT, MotorType.kBrushless);
    private final RelativeEncoder feederEncoder = feederMotor.getEncoder();
    private final ColorSensorV3 feederSensor = new ColorSensorV3(Port.kMXP);

    public Feeder() {
        feederMotor.restoreFactoryDefaults();
        feederMotor.setInverted(true);
        feederMotor.setIdleMode(IdleMode.kCoast);
        feederMotor.setPeriodicFramePeriod(PeriodicFrame.kStatus1, 255);
        feederMotor.setPeriodicFramePeriod(PeriodicFrame.kStatus3, 255);
        feederMotor.burnFlash();

        feederSensor.configureProximitySensor(ProximitySensorResolution.kProxRes11bit,
                ProximitySensorMeasurementRate.kProxRate6ms);
        Shuffleboard.getTab("ShooterRaw").addString("Detected", () -> getSensorColor().name());
        Shuffleboard.getTab("ShooterRaw").addBoolean("BallLoaded?", this::isBallLoaded);
        Shuffleboard.getTab("ShooterRaw").addNumber("Proximity", feederSensor::getProximity);
    }

    public void setFeederPercent(double percent) {
        feederMotor.set(percent);
    }

    public FeederDetectedColor getSensorColor() {
        if (feederSensor.getRed() > feederSensor.getBlue()) {
            return FeederDetectedColor.RED_BALL;
        }
        return FeederDetectedColor.BLUE_BALL;
    }

    public boolean isBallLoaded() {
        return feederSensor.getProximity() > FEEDER_SENSOR_PROXIMITY_LEVEL;
    }

    public double getEncoderRotations() {
        return feederEncoder.getPosition();
    }
}
