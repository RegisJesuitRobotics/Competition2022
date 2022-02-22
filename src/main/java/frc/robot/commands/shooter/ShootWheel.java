package frc.robot.commands.shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;
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
import frc.robot.subsystems.Shooter;

import static frc.robot.Constants.ShooterConstants.*;

public class ShootWheel extends CommandBase{
    
    public static final double shootWheelSpeed = 0;
    private PlaystationController driverController;
    private Shooter shooter; 
    // private final CANSparkMax shootingMotor = new CANSparkMax(SHOOTER_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
    // private final Trigger rightTrigger = new Trigger(this, Axis.kR2.value);
    
    
    public ShootWheel(Shooter shooter){
        this.shooter = shooter;
    } 

    
}
    
    
    

//method for starting motor
//method to start feeder motor
//