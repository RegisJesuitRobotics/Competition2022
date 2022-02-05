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

import static frc.robot.Constants.ShooterConstants.*;

public class Shooter extends SubsystemBase {
    private final CANSparkMax shooterMotor = new CANSparkMax(SHOOTER_PORT, MotorType.kBrushless);
    private final RelativeEncoder shooterEncoder = shooterMotor.getEncoder();
    private final SimpleMotorFeedforward shooterFeedForward = new SimpleMotorFeedforward(SHOOTER_VELOCITY_S_VOLTS, SHOOTER_VELOCITY_V_VOLTS);
    private final PIDController shooterPidController = new PIDController(SHOOTER_VELOCITY_P, 0.0, 0.0);
    private double shooterTargetRPM = 0.0;

    private final CANSparkMax feederMotor = new CANSparkMax(FEEDER_PORT, MotorType.kBrushless);
    private final RelativeEncoder feederEncoder = feederMotor.getEncoder();
    private final SimpleMotorFeedforward feederFeedForward = new SimpleMotorFeedforward(FEEDER_VELOCITY_S_VOLTS, FEEDER_VELOCITY_V_VOLTS);
    private final PIDController feederPidController = new PIDController(FEEDER_VELOCITY_P, 0.0, 0.0);
    private double feederTargetRPM = 0.0;

    private final ShuffleboardTab shooterTab = Shuffleboard.getTab("Shooter");


    public Shooter() {
        shooterMotor.setInverted(true);
        shooterMotor.setIdleMode(IdleMode.kCoast);
        shooterEncoder.setVelocityConversionFactor(1 / SHOOTER_GEARING);
        shooterMotor.burnFlash();

        feederMotor.setInverted(true);
        feederMotor.setIdleMode(IdleMode.kCoast);
        feederEncoder.setVelocityConversionFactor(1 / FEEDER_GEARING); // Rotations per minute
        feederMotor.burnFlash();

        shooterTab.addNumber("Target Feeder RPM", () -> feederTargetRPM);
        shooterTab.addNumber("Actual Feeder RPM", feederEncoder::getVelocity);

        shooterTab.addNumber("Target Shooter RPM", () -> shooterTargetRPM);
        shooterTab.addNumber("Actual Shooter RPM", shooterEncoder::getVelocity);
    }


    public void setShooterRPM(double rpm) {
        shooterTargetRPM = rpm;
    }

    public void setFeederRPM(double rpm) {
        feederTargetRPM = rpm;
    }

    @Override
    public void periodic() {
        feederMotor.setVoltage(feederPidController.calculate(feederEncoder.getVelocity(), feederTargetRPM) + feederFeedForward.calculate(feederTargetRPM));
        shooterMotor.setVoltage(shooterPidController.calculate(shooterEncoder.getVelocity(), shooterTargetRPM) + shooterFeedForward.calculate(shooterTargetRPM));
    }
}
