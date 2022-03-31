// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.intake;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.IntakeConstants;

public class Intake extends SubsystemBase {
    private final CANSparkMax intakeMotor = new CANSparkMax(IntakeConstants.INTAKE_PORT,
            CANSparkMaxLowLevel.MotorType.kBrushless);
    private final DoubleSolenoid intakeSolenoidRight = new DoubleSolenoid(PneumaticsModuleType.REVPH,
            IntakeConstants.SOLENOID_RIGHT_FORWARD_PORT, IntakeConstants.SOLENOID_RIGHT_REVERSE_PORT);
    private final DoubleSolenoid intakeSolenoidLeft = new DoubleSolenoid(PneumaticsModuleType.REVPH,
            IntakeConstants.SOLENOID_LEFT_FORWARD_PORT, IntakeConstants.SOLENOID_LEFT_REVERSE_PORT);

    public Intake() {
        intakeMotor.restoreFactoryDefaults();
        intakeMotor.setInverted(true);
        intakeMotor.setIdleMode(IdleMode.kCoast);
        intakeMotor.setSmartCurrentLimit(20);

        intakeMotor.burnFlash();
    }

    public void setPercentage(double percentage) {
        intakeMotor.set(percentage);
    }

    public void setSolenoid(boolean deployed) {
        if (deployed) {
            intakeSolenoidRight.set(Value.kForward);
            intakeSolenoidLeft.set(Value.kForward);
        } else {
            intakeSolenoidRight.set(Value.kReverse);
            intakeSolenoidLeft.set(Value.kReverse);
        }
    }

    public void toggleSolenoid() {
        if (intakeSolenoidRight.get() == Value.kOff || intakeSolenoidLeft.get() == Value.kOff) {
            intakeSolenoidRight.set(Value.kForward);
            intakeSolenoidLeft.set(Value.kForward);
        } else {
            intakeSolenoidRight.toggle();
            intakeSolenoidLeft.toggle();
        }
    }

    public boolean isDeployed() {
        return intakeSolenoidLeft.get() == Value.kForward;
    }
}
