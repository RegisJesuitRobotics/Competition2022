package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.ShooterConstants.*;

public class Shooter extends SubsystemBase {
    private final CANSparkMax shooterMotor = new CANSparkMax(SHOOTER_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final SparkMaxPIDController pidController = shooterMotor.getPIDController();

    public Shooter() {
        pidController.setP(VELOCITY_P);
        pidController.setI(VELOCITY_I);
        pidController.setD(VELOCITY_D);
        pidController.setFF(VELOCITY_FF);
        pidController.setOutputRange(-1, 1);
    }


    public void setRPM(double rpm) {
        pidController.setReference(rpm, CANSparkMax.ControlType.kVelocity);
    }
}
