// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.auto.TrajectoryCommandGenerator;
import frc.robot.commands.climber.ClimberBackwardCommand;
import frc.robot.commands.climber.ClimberDownCommand;
import frc.robot.commands.climber.ClimberForwardCommand;
import frc.robot.commands.climber.ClimberUpCommand;
import frc.robot.commands.drive.ArcadeDriveCommand;
import frc.robot.commands.drive.TankishDriveCommand;
import frc.robot.commands.shooter.ShootWheel;
import frc.robot.joysticks.PlaystationController;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Shooter;
import frc.robot.utils.ShuffleboardTabs;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    private final DriveTrain driveTrain = new DriveTrain();
    private final Climber climber = new Climber();
//    private final LimeLight limeLight = new LimeLight();
   private final Shooter shooter = new Shooter();

    private final PlaystationController driverController = new PlaystationController(0);
    private final PlaystationController operatorController = new PlaystationController(1);

    private final SendableChooser<Command> autoCommandChooser = new SendableChooser<>();

    private final SendableChooser<Command> teleopDriveStyle = new SendableChooser<>();

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        teleopDriveStyle.addOption("Tankish Drive (Aidan)", new TankishDriveCommand(driveTrain, driverController));
        teleopDriveStyle.setDefaultOption("Arcade Drive (Everyone else)",
                new ArcadeDriveCommand(driveTrain, driverController));

        // TODO: add auto commands to chooser
        ShuffleboardTabs.getAutoTab().add("Chooser", autoCommandChooser);
        ShuffleboardTabs.getTeleopTab().add("Drive Style", teleopDriveStyle);
        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by instantiating a {@link GenericHID} or one of its subclasses
     * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
     * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        evaluateDriveStyle();
        driverController.climberUpButton.whenHeld(new ClimberUpCommand(climber));
        driverController.climberDownButton.whenHeld(new ClimberDownCommand(climber));
        driverController.climberForwardRotationButton.whenHeld(new ClimberForwardCommand(climber));
        driverController.climberBackwardRotationButton.whenHeld(new ClimberBackwardCommand(climber));

        driverController.rightButton.whenHeld(new ShootWheel(shooter));
       // driverController.rightButton.whenActive(new
    }


    public void evaluateDriveStyle() {
        driveTrain.setDefaultCommand(teleopDriveStyle.getSelected());
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return TrajectoryCommandGenerator.getCommandFromFile("2BallLeft", driveTrain);
    }
}
