package frc.robot.commands.shooter;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS4Controller.Axis;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.utils.MathUtils;
import frc.robot.joysticks.PlaystationController;
import frc.robot.joysticks.PlaystationController.Trigger;
import static frc.robot.Constants.ShooterConstants.*;

public class feedMotor {
    private final ShootWheel shooter;
   
    public feedMotor( ShootWheel shooter){
        this.shooter = shooter;
    } 



}
/*
right button get pressed
    shooter motor turn on
        shooter motor hits popor speed
            feeder motor runs
            


*/