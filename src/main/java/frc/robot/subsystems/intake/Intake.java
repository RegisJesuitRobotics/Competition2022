// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.intake;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.IntakeConstants;

public class Intake extends SubsystemBase {

    private final CANSparkMax intakeMotor = new CANSparkMax(IntakeConstants.INTAKE_PORT,
            CANSparkMaxLowLevel.MotorType.kBrushless);
    private final DoubleSolenoid intakeSolenoidRightOpen = new DoubleSolenoid(PneumaticsModuleType.REVPH, IntakeConstants.SOLENOID_RIGHT_OPEN_PORT, IntakeConstants.SOLENOID_RIGHT_CLOSE_PORT);
    private final DoubleSolenoid intakeSolenoidLeftOpen = new DoubleSolenoid(PneumaticsModuleType.REVPH, IntakeConstants.SOLENOID_LEFT_OPEN_PORT, IntakeConstants.SOLENOID_LEFT_CLOSE_PORT);

    public void setPercentage(double percentage) {
        intakeMotor.set(percentage);
    }

    public void setSolenoid(boolean deployed) {
        if (deployed) {
            intakeSolenoidRightOpen.set(Value.kForward);
            intakeSolenoidLeftOpen.set(Value.kForward);
        } else {
            intakeSolenoidRightOpen.set(Value.kReverse);
            intakeSolenoidLeftOpen.set(Value.kReverse);
        }
    }
}
