// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import static frc.robot.Constants.IntakeConstants;

public class Spinners extends SubsystemBase {
  private final WPI_VictorSPX spinner = new WPI_VictorSPX(IntakeConstants.SPINNER);
  
  /** Creates a new Spinners. */
  public Spinners() {
    spinner.setInverted(false);
  }
  
  public void setSpinners(double speed){
    spinner.set(speed);
  }
}
