package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.FeederConstants.*;

public class Feeder extends SubsystemBase {
    private final CANSparkMax feederMotor = new CANSparkMax(FEEDER_PORT, MotorType.kBrushless);
    private final ColorSensorV3 feederSensor = new ColorSensorV3(Port.kOnboard);

    public Feeder() {
        feederMotor.restoreFactoryDefaults();
        feederMotor.setInverted(true);
        feederMotor.setIdleMode(IdleMode.kCoast);
        feederMotor.burnFlash();
    }

    public void setFeederPercent(double percent) {
        feederMotor.set(percent);
    }

    public boolean isBallDetected() {
        return false;
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Prox", feederSensor.getProximity());
    }
}
