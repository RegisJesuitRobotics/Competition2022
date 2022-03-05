// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.climber.ClimberBackwardCommand;
import frc.robot.commands.climber.ClimberDownCommand;
import frc.robot.commands.climber.ClimberForwardCommand;
import frc.robot.commands.climber.ClimberUpCommand;
import frc.robot.commands.drive.ArcadeDriveCommand;
import frc.robot.commands.drive.SimpleAutoDriveCommand;
import frc.robot.commands.drive.TankishDriveCommand;
import frc.robot.commands.feeder.RunFeederCommand;
import frc.robot.commands.intake.*;
import frc.robot.commands.limelight.LimeLightAllAlignCommand;
import frc.robot.commands.shooter.RunShooterAndFeederCommand;
import frc.robot.commands.shooter.RunShooterCommand;
import frc.robot.commands.shooter.ToggleAimCommand;
import frc.robot.joysticks.PlaystationController;
import frc.robot.subsystems.*;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.intake.Spinners;
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
    private final Feeder feeder = new Feeder();
    private final Shooter shooter = new Shooter();
    private final LengthClimber lengthClimber = new LengthClimber();
    private final RotationClimber rotationClimber = new RotationClimber();
    private final Intake intake = new Intake();
    private final Spinners spinners = new Spinners();
    private final LimeLight limeLight = new LimeLight();

    private final PlaystationController driverController = new PlaystationController(0);
    private final PlaystationController operatorController = new PlaystationController(1);

    private final SendableChooser<Command> teleopDriveStyle = new SendableChooser<>();

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        teleopDriveStyle.setDefaultOption("Tankish Drive (Aidan)",
                new TankishDriveCommand(driveTrain, driverController));
        teleopDriveStyle.addOption("Arcade Drive (Everyone else)",
                new ArcadeDriveCommand(driveTrain, driverController));

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
        driverController.dPad.right.whileHeld(new SimpleAutoDriveCommand(0.0, 0.3, driveTrain));
        driverController.dPad.left.whileHeld(new SimpleAutoDriveCommand(0.0, -0.3, driveTrain));

        driverController.rightButton.whenHeld(new IntakeSpinnersRunCommand(intake, spinners));

        driverController.circle.whenPressed(new IntakeToggleCommand(intake));

        operatorController.share.whenPressed(new ToggleAimCommand(shooter));
        operatorController.leftButton.whileHeld(new RunFeederCommand(feeder));
        operatorController.rightButton
                .whileHeld(new RunShooterAndFeederCommand(ShooterConstants.CLOSE_DISTANCE_RPM, shooter, feeder));
        operatorController.dPad.left.whileHeld(new RunShooterCommand(ShooterConstants.CLOSE_DISTANCE_RPM, shooter));
        operatorController.options.whenHeld(new LimeLightAllAlignCommand(-1, limeLight, driveTrain));

        operatorController.triangle.whileHeld(new ClimberUpCommand(lengthClimber));
        operatorController.x.whileHeld(new ClimberDownCommand(lengthClimber));
        operatorController.circle.whileHeld(new ClimberForwardCommand(rotationClimber));
        operatorController.square.whileHeld(new ClimberBackwardCommand(rotationClimber));

        evaluateDriveStyle();
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
        return new InstantCommand(() -> shooter.setAimState(Value.kReverse))
                .andThen(new RunShooterAndFeederCommand(ShooterConstants.CLOSE_DISTANCE_RPM, shooter, feeder))
                .andThen(new SimpleAutoDriveCommand(-0.5, 0.0, driveTrain).withTimeout(1));
    }
}
