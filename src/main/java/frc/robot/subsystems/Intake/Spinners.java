// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import static frc.robot.Constants.IntakeConstants;

public class Spinners extends SubsystemBase {
  private final WPI_TalonFX leftSpinner = new WPI_TalonFX(IntakeConstants.SPINNER_LEFT);
  private final WPI_TalonFX rightSpinner = new WPI_TalonFX(IntakeConstants.SPINNER_RIGHT);
  
  /** Creates a new Spinners. */
  public Spinners() {
    rightSpinner.follow(leftSpinner);

    leftSpinner.setInverted(false);

    rightSpinner.setInverted(InvertType.OpposeMaster);
  }
  
  public void setSpinners(double speed){
    leftSpinner.set(speed);
  }
}
