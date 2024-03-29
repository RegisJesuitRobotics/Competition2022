package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.PeriodicFrame;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.ShooterConstants.*;

public class Shooter extends SubsystemBase {
    public enum AimState {
        CLOSE(Value.kReverse),
        FAR(Value.kForward);

        public final Value value;

        AimState(Value value) {
            this.value = value;
        }
    }

    private final CANSparkMax shooterMotor = new CANSparkMax(SHOOTER_PORT, MotorType.kBrushless);
    private final RelativeEncoder shooterEncoder = shooterMotor.getEncoder();
    private final SimpleMotorFeedforward shooterFeedForward = new SimpleMotorFeedforward(SHOOTER_VELOCITY_S_VOLTS,
            SHOOTER_VELOCITY_V_VOLTS);
    private final PIDController shooterPidController = new PIDController(SHOOTER_VELOCITY_P, 0.0, 0.0);
    private final SlewRateLimiter rateLimiter = new SlewRateLimiter(2000);
    private AimState previousState = AimState.CLOSE;

    private double shooterTargetRPS = 0.0;

    private boolean shootingClose = true;

    private final DoubleSolenoid shooterSolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, SHOOTER_AIM_OPEN_PORT,
            SHOOTER_AIM_CLOSE_PORT);

    public Shooter() {
        // We have to use rotations per second instead of the native unit of rotations
        // per second
        // because sys-id uses rotation/s and translating the gains seems overkill when
        // we can just do it this way
        shooterMotor.restoreFactoryDefaults();
        shooterMotor.setInverted(true);
        shooterMotor.setIdleMode(IdleMode.kCoast);
        shooterEncoder.setVelocityConversionFactor(1 / (SHOOTER_GEARING * 60));

        // Increase velocity measurement frame
        shooterMotor.setPeriodicFramePeriod(PeriodicFrame.kStatus1, 10);
        shooterMotor.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 255);
        shooterMotor.setPeriodicFramePeriod(PeriodicFrame.kStatus3, 255);

        shooterMotor.burnFlash();

        ShuffleboardTab shooterTab = Shuffleboard.getTab("ShooterRaw");
        ShuffleboardLayout rpmList = shooterTab.getLayout("RPM", BuiltInLayouts.kList);
        rpmList.addNumber("Target Shooter RPM", () -> shooterTargetRPS * 60);
        rpmList.addNumber("Actual Shooter RPM", this::getShooterRPM);
        shooterTab.addBoolean("Shooting Close", this::isAimingClose);

        setAimState(AimState.CLOSE);
    }


    public void setShooterRPM(double rpm) {
        shooterTargetRPS = rpm / 60;
    }

    public double getShooterRPM() {
        return shooterEncoder.getVelocity() * 60;
    }

    public void setAimState(AimState aimState) {
        setAimState(aimState.value);
        shootingClose = aimState == AimState.CLOSE;
    }

    private void setAimState(Value value) {
        shooterSolenoid.set(value);
    }

    public AimState getAimState() {
        return shooterSolenoid.get() == Value.kForward ? AimState.FAR : AimState.CLOSE;
    }

    public void toggleAimState() {
        if (shootingClose) {
            setAimState(AimState.FAR);
        } else {
            setAimState(AimState.CLOSE);
        }
    }

    public void setTemporaryState(AimState temporaryState) {
        previousState = getAimState();
        setAimState(temporaryState);
    }

    public void restorePreviousState() {
        setAimState(previousState);
    }

    public boolean isAimingClose() {
        return shootingClose;
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
