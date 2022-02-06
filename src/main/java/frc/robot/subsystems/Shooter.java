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
    private final SimpleMotorFeedforward shooterFeedForward = new SimpleMotorFeedforward(SHOOTER_VELOCITY_S_VOLTS,
            SHOOTER_VELOCITY_V_VOLTS);
    private final PIDController shooterPidController = new PIDController(SHOOTER_VELOCITY_P, 0.0, 0.0);
    private double shooterTargetRPS = 0.0;

    private final ShuffleboardTab shooterTab = Shuffleboard.getTab("Shooter");

    public Shooter() {
        // We have to use rotations per second instead of the native unit of rotations
        // per second
        // because sys-id uses rotation/s and translating the gains seems overkill when
        // we can just do it this way
        shooterMotor.setInverted(true);
        shooterMotor.setIdleMode(IdleMode.kCoast);
        shooterEncoder.setVelocityConversionFactor(1 / (SHOOTER_GEARING * 60));
        // Not using burn flash yet because this code is ran on the test bench, and we
        // don't want to invert those
//        shooterMotor.burnFlash();

        shooterTab.addNumber("Target Shooter RPM", () -> shooterTargetRPS * 60);
        shooterTab.addNumber("Actual Shooter RPM", () -> shooterEncoder.getVelocity() * 60);
    }


    public void setShooterRPM(double rpm) {
        shooterTargetRPS = rpm / 60;
    }

    @Override
    public void periodic() {
        double shooterFeedback = shooterPidController.calculate(shooterEncoder.getVelocity(), shooterTargetRPS);
        double shooterFeedforward = shooterFeedForward.calculate(shooterTargetRPS);
        shooterMotor.setVoltage(shooterFeedback + shooterFeedforward);
    }
}
