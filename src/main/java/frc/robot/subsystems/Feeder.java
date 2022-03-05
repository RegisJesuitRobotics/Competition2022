package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.FeederConstants.*;

public class Feeder extends SubsystemBase {
    private final CANSparkMax feederMotor = new CANSparkMax(FEEDER_PORT, MotorType.kBrushless);

    public Feeder() {
        feederMotor.restoreFactoryDefaults();
        feederMotor.setInverted(true);
        feederMotor.setIdleMode(IdleMode.kCoast);
        feederMotor.burnFlash();
    }

    public void setFeederPercent(double percent) {
        feederMotor.set(percent);
    }

}
