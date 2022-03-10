// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.Constants.FeederConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.DoNothingCommand;
import frc.robot.commands.climber.ClimberControllerControlCommand;
import frc.robot.commands.drive.ArcadeDriveCommand;
import frc.robot.commands.drive.SimpleAutoDriveCommand;
import frc.robot.commands.drive.TankishDriveCommand;
import frc.robot.commands.feeder.FeederRunCommand;
import frc.robot.commands.intake.*;
import frc.robot.commands.limelight.LimeLightAllAlignCommand;
import frc.robot.commands.shooter.ShooterAndFeederRunCommand;
import frc.robot.commands.shooter.ToggleAimCommand;
import frc.robot.joysticks.Logitech3DProController;
import frc.robot.joysticks.PseudoXboxController;
import frc.robot.subsystems.*;
import frc.robot.subsystems.climber.LengthClimber;
import frc.robot.subsystems.climber.RotationClimber;
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

    private final PseudoXboxController driverController = new PseudoXboxController(0);
    private final PseudoXboxController operatorController = new PseudoXboxController(1);
    private final Logitech3DProController operatorClimberController = new Logitech3DProController(2);

    private final SendableChooser<Command> teleopDriveStyle = new SendableChooser<>();
    private final ClimberControllerControlCommand climberControlCommand = new ClimberControllerControlCommand(
            operatorClimberController, lengthClimber, rotationClimber);

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
        // Driver
        driverController.dPad.right.whileHeld(new SimpleAutoDriveCommand(0.0, 0.3, driveTrain));
        driverController.dPad.left.whileHeld(new SimpleAutoDriveCommand(0.0, -0.3, driveTrain));

        driverController.rightButton.whileHeld(
                new ConditionalCommand(new IntakeRunCommand(intake), new DoNothingCommand(), intake::isDeployed));

        driverController.circle.whenPressed(new IntakeToggleCommand(intake));

        // Operator
        operatorController.share.whileHeld(new FeederRunCommand(FeederConstants.FEEDER_SPEED / 2, feeder));
        operatorController.options.whileHeld(new FeederRunCommand(-FeederConstants.FEEDER_SPEED / 2, feeder));
        operatorController.rightButton.whileHeld(new ConditionalCommand(
                new ShooterAndFeederRunCommand(ShooterConstants.CLOSE_DISTANCE_RPM, shooter, feeder, spinners),
                new ShooterAndFeederRunCommand(ShooterConstants.FAR_DISTANCE_RPM, shooter, feeder, spinners),
                shooter::isAimingClose));

        operatorController.dPad.left.whenPressed(new ToggleAimCommand(shooter));
        operatorController.leftButton.whenHeld(new LimeLightAllAlignCommand(-1, limeLight, driveTrain));

        lengthClimber.setDefaultCommand(climberControlCommand);
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
//        return new InstantCommand(() -> shooter.setAimState(Value.kReverse))
//                .andThen(new RunShooterAndFeederCommand(ShooterConstants.CLOSE_DISTANCE_RPM, shooter, feeder))
//                .andThen(new SimpleAutoDriveCommand(-0.5, 0.0, driveTrain).withTimeout(1));
        return null;
    }
}
