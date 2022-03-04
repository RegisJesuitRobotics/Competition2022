package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
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
    private final SlewRateLimiter rateLimiter = new SlewRateLimiter(800);

    private double shooterTargetRPS = 0.0;

    private final DoubleSolenoid shooterSolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, SHOOTER_AIM_OPEN_PORT,
            SHOOTER_AIM_CLOSE_PORT);

    private final ShuffleboardTab shooterTab = Shuffleboard.getTab("Shooter");

    public Shooter() {
        // We have to use rotations per second instead of the native unit of rotations
        // per second
        // because sys-id uses rotation/s and translating the gains seems overkill when
        // we can just do it this way
        shooterMotor.setInverted(true);
        shooterMotor.setIdleMode(IdleMode.kCoast);
        shooterEncoder.setVelocityConversionFactor(1 / (SHOOTER_GEARING * 60));
        shooterMotor.burnFlash();

        shooterTab.addNumber("Target Shooter RPM", () -> shooterTargetRPS * 60);
        shooterTab.addNumber("Actual Shooter RPM", this::getShooterRPM);
        shooterTab.addBoolean("Shooting Close", () -> shooterSolenoid.get() == Value.kReverse);
    }


    public void setShooterRPM(double rpm) {
        shooterTargetRPS = rpm / 60;
    }


    public double getShooterRPM() {
        return shooterEncoder.getVelocity() * 60;
    }

    public void setAimState(Value state) {
        shooterSolenoid.set(state);
    }

    public void toggleAimState() {
        Value value = shooterSolenoid.get();
        if (value == Value.kReverse) {
            setAimState(Value.kForward);
        } else {
            setAimState(Value.kReverse);
        }
    }

    @Override
    public void periodic() {
        if (shooterTargetRPS == 0) {
            shooterMotor.setVoltage(0.0);
            rateLimiter.reset(0.0);
            return;
        }
        double actualTarget = rateLimiter.calculate(shooterTargetRPS);
        double shooterFeedback = shooterPidController.calculate(shooterEncoder.getVelocity(), actualTarget);
        double shooterFeedforward = shooterFeedForward.calculate(actualTarget);
        shooterMotor.setVoltage(shooterFeedback + shooterFeedforward);
    }
}
