// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.Constants.FeederConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.DoNothingCommand;
import frc.robot.commands.drive.ArcadeDriveCommand;
import frc.robot.commands.drive.SimpleAutoDriveCommand;
import frc.robot.commands.drive.TankishDriveCommand;
import frc.robot.commands.feeder.FeedOneBallCommand;
import frc.robot.commands.feeder.FeederRunCommand;
import frc.robot.commands.feeder.LoadBallToWaitingZoneCommand;
import frc.robot.commands.intake.*;
import frc.robot.commands.limelight.LimeLightAllAlignCommand;
import frc.robot.commands.shooter.OneBallShootSequenceCommand;
import frc.robot.commands.shooter.ToggleAimCommand;
import frc.robot.commands.shooter.TwoBallShootSequenceCommand;
import frc.robot.joysticks.PlaystationController;
import frc.robot.subsystems.*;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.intake.Spinners;


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

        Shuffleboard.getTab("DriveTrainRaw").add("Drive Style", teleopDriveStyle);
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

        driverController.rightButton.whileHeld(new ConditionalCommand(
                new IntakeRunAndLoadBallToWaitCommand(feeder, intake), new DoNothingCommand(), intake::isDeployed));

        driverController.triangle.whileHeld(
                new ConditionalCommand(new IntakeRunCommand(intake), new DoNothingCommand(), intake::isDeployed));

        driverController.circle.whenPressed(new IntakeToggleCommand(intake));

        // Operator
        operatorController.rightButton.whenHeld(new ConditionalCommand(
                new TwoBallShootSequenceCommand(ShooterConstants.CLOSE_DISTANCE_RPM, feeder, shooter, spinners),
                new TwoBallShootSequenceCommand(ShooterConstants.FAR_DISTANCE_RPM, feeder, shooter, spinners),
                shooter::isAimingClose));
        operatorController.leftButton.whenHeld(new ConditionalCommand(
                new OneBallShootSequenceCommand(ShooterConstants.CLOSE_DISTANCE_RPM, feeder, shooter, spinners),
                new OneBallShootSequenceCommand(ShooterConstants.FAR_DISTANCE_RPM, feeder, shooter, spinners),
                shooter::isAimingClose));

        operatorController.dPad.up.whenHeld(new LoadBallToWaitingZoneCommand(feeder));
        operatorController.dPad.left.whileHeld(new FeederRunCommand(FeederConstants.FEEDER_SPEED, feeder));
        operatorController.dPad.right.whileHeld(new FeederRunCommand(-FeederConstants.FEEDER_SPEED, feeder));
        operatorController.dPad.down.whenHeld(new FeedOneBallCommand(feeder));

        operatorController.triangle.whenHeld(new LimeLightAllAlignCommand(
                ShooterConstants.FAR_SHOOTING_LOCATION_DISTANCE_METERS, limeLight, driveTrain));
        operatorController.circle.whenHeld(new LimeLightAllAlignCommand(-1, limeLight, driveTrain));
        operatorController.square.whenPressed(new ToggleAimCommand(shooter));

        operatorController.share.whileHeld(new SpinnersRunCommand(spinners));

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
