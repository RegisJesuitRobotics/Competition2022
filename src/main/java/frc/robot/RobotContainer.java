// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.FeederConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.DoNothingCommand;
import frc.robot.commands.climber.ClimberControllerControlCommand;
import frc.robot.commands.auto.paths.*;
import frc.robot.commands.drive.TankishDriveCommand;
import frc.robot.commands.feeder.*;
import frc.robot.commands.intake.*;
import frc.robot.commands.shooter.OneBallShootSequenceCommand;
import frc.robot.commands.shooter.ShooterRunCommand;
import frc.robot.commands.shooter.ToggleAimCommand;
import frc.robot.joysticks.ThrustMaster;
import frc.robot.commands.shooter.TwoBallShootSequenceCommand;
import frc.robot.joysticks.PlaystationController;
import frc.robot.joysticks.PseudoXboxController;
import frc.robot.subsystems.*;
import frc.robot.subsystems.climber.LengthClimber;
import frc.robot.subsystems.climber.RotationClimber;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.intake.Spinners;

import java.util.Map;


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

    private final PlaystationController driverController = new PlaystationController(0);
    private final PseudoXboxController operatorController = new PseudoXboxController(1);
    private final ThrustMaster operatorClimberController = new ThrustMaster(2);

    private final SendableChooser<Command> autoRoutineChooser = new SendableChooser<>();
    private final ClimberControllerControlCommand climberControlCommand = new ClimberControllerControlCommand(
            operatorClimberController, lengthClimber, rotationClimber
    );
    private final NetworkTableEntry maxSpeedEntry = Shuffleboard.getTab("OutreachRaw").add("Max Speed", 0.8)
            .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1.0)).getEntry();
    private final TankishDriveCommand tankishDriveCommand = new TankishDriveCommand(
            () -> maxSpeedEntry.getDouble(0.8), driveTrain, driverController
    );

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        autoRoutineChooser.setDefaultOption(
                "One Ball No Tarmac",
                new OneBallShootSequenceCommand(ShooterConstants.CLOSE_DISTANCE_RPM, feeder, shooter, spinners)
        );
        autoRoutineChooser.addOption(
                "One Ball With Tarmac", new OneBallAutoCommand(driveTrain, intake, shooter, feeder, spinners)
        );
        autoRoutineChooser.addOption(
                "Two Ball Close Hanger", new TwoBallTopAutoCommand(driveTrain, intake, shooter, feeder, spinners)
        );
        autoRoutineChooser
                .addOption("Three Ball", new ThreeBallAutoCommand(driveTrain, intake, shooter, feeder, spinners));
        autoRoutineChooser.addOption(
                "Two Ball Far Hanger (No Steal)",
                new TwoBallBottomNoStealAutoCommand(driveTrain, intake, shooter, feeder, spinners)
        );
        autoRoutineChooser.addOption(
                "Two Ball Close Hanger (No Steal)",
                new TwoBallTopNoStealAutoCommand(driveTrain, intake, shooter, feeder, spinners)
        );
        autoRoutineChooser.addOption("Tarmac Only", new TarmacOnlyCommand(driveTrain));
        autoRoutineChooser.addOption("Do Nothing", new DoNothingCommand());

        Shuffleboard.getTab("DriveTrainRaw").add("Auto", autoRoutineChooser);
        Shuffleboard.getTab("UtilsRaw").addNumber("Match Time", () -> Math.ceil(DriverStation.getMatchTime()));
        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by instantiating a {@link GenericHID} or one of its subclasses
     * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
     * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        Trigger intakeDeployedTrigger = new Trigger(intake::isDeployed);

        // Driver
        driverController.rightButton.and(intakeDeployedTrigger).whileActiveOnce(
                new ParallelCommandGroup(
                        new IntakeRunCommand(intake),
                        new LoadBallToWaitingZoneAndCheckColorCommand(feeder, shooter, spinners)
                )
        );

        driverController.triangle.and(intakeDeployedTrigger).whileActiveContinuous(new IntakeRunCommand(intake));
        driverController.circle.whenPressed(new IntakeToggleCommand(intake));
        driverController.x.and(intakeDeployedTrigger).whileActiveContinuous(new IntakeRunCommand(true, intake));

        // Operator
        operatorController.rightButton.whenHeld(
                new ConditionalCommand(
                        new TwoBallShootSequenceCommand(ShooterConstants.CLOSE_DISTANCE_RPM, feeder, shooter, spinners),
                        new TwoBallShootSequenceCommand(ShooterConstants.EDGE_TARMAC_RPM, feeder, shooter, spinners),
                        shooter::isAimingClose
                )
        );

        operatorController.dPad.up.whenHeld(new LoadBallToWaitingZoneAndCheckColorCommand(feeder, shooter, spinners));
        operatorController.dPad.left.whileHeld(new FeederRunCommand(FeederConstants.FEEDER_SPEED, feeder));
        operatorController.dPad.right.whileHeld(new FeederRunCommand(FeederConstants.FEEDER_BACKWARD_SPEED, feeder));
        operatorController.dPad.down.whileHeld(new ShooterRunCommand(-1000, shooter));

        operatorController.square.whenPressed(new ToggleAimCommand(shooter));

        // When there are 55 seconds left remind drivers to climb
        Trigger climbReminder = new Trigger(
                () -> DriverStation.getMatchTime() < 55 && DriverStation.getMatchTime() > 53 && DriverStation.isTeleop()
        );
        climbReminder.whenActive(() -> setOperatorRumble(true));
        climbReminder.whenInactive(() -> setOperatorRumble(false));

        lengthClimber.setDefaultCommand(climberControlCommand);

        driveTrain.setDefaultCommand(tankishDriveCommand);
    }

    private void setOperatorRumble(boolean on) {
        int value = on ? 1 : 0;
        operatorController.setRumble(RumbleType.kRightRumble, value);
        operatorController.setRumble(RumbleType.kLeftRumble, value);
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return autoRoutineChooser.getSelected();
    }
}
