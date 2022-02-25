package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.FeederConstants.*;

public class Feeder extends SubsystemBase {
    private final CANSparkMax feederMotor = new CANSparkMax(FEEDER_PORT, MotorType.kBrushless);
    private final RelativeEncoder feederEncoder = feederMotor.getEncoder();
    private final SimpleMotorFeedforward feederFeedForward = new SimpleMotorFeedforward(FEEDER_VELOCITY_S_VOLTS,
            FEEDER_VELOCITY_V_VOLTS);
    private final PIDController feederPidController = new PIDController(FEEDER_VELOCITY_P, 0.0, 0.0);
    private double feederTargetRPS = 0.0;

    private final ShuffleboardTab shooterTab = Shuffleboard.getTab("Shooter");

    public Feeder() {
        feederMotor.setInverted(true);
        feederMotor.setIdleMode(IdleMode.kCoast);
        feederEncoder.setVelocityConversionFactor(1 / (FEEDER_GEARING * 60));
        feederMotor.burnFlash();

        shooterTab.addNumber("Target Feeder RPM", () -> feederTargetRPS * 60);
        shooterTab.addNumber("Actual Feeder RPM", () -> feederEncoder.getVelocity() * 60);
    }

    public void setFeederRPM(double rpm) {
        feederTargetRPS = rpm / 60;
    }

    @Override
    public void periodic() {
        if (feederTargetRPS == 0) {
            feederMotor.setVoltage(0.0);
        } else {
            double feederFeedback = feederPidController.calculate(feederEncoder.getVelocity(), feederTargetRPS);
            double feederFeedforward = feederFeedForward.calculate(feederTargetRPS);
            feederMotor.setVoltage(feederFeedback + feederFeedforward);
        }
    }
}
