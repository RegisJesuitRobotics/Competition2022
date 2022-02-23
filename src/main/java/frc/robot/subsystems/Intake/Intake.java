// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Intake;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.IntakeConstants;

public class Intake extends SubsystemBase {

    private final CANSparkMax intakeMotor = new CANSparkMax(IntakeConstants.INTAKE_PORT,
            CANSparkMaxLowLevel.MotorType.kBrushless);
    private final Solenoid intakeSolenoid = new Solenoid(PneumaticsModuleType.REVPH, IntakeConstants.SOLENOID_PORT);

    public void setPercentage(double percentage) {
        intakeMotor.set(percentage);
    }

    public void setSolenoid(boolean deployed) {
        intakeSolenoid.set(deployed);
    }
}
