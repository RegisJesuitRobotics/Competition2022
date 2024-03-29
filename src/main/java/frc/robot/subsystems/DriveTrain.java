package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMaxLowLevel.PeriodicFrame;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.commands.drive.ToggleBrakeModeCommand;

public class DriveTrain extends SubsystemBase {
    private final CANSparkMax leftTop = new CANSparkMax(DriveConstants.LEFT_TOP_PORT, MotorType.kBrushless);
    private final CANSparkMax leftBack = new CANSparkMax(DriveConstants.LEFT_BACK_PORT, MotorType.kBrushless);
    private final CANSparkMax leftFront = new CANSparkMax(DriveConstants.LEFT_FRONT_PORT, MotorType.kBrushless);

    private final CANSparkMax rightTop = new CANSparkMax(DriveConstants.RIGHT_TOP_PORT, MotorType.kBrushless);
    private final CANSparkMax rightBack = new CANSparkMax(DriveConstants.RIGHT_BACK_PORT, MotorType.kBrushless);
    private final CANSparkMax rightFront = new CANSparkMax(DriveConstants.RIGHT_FRONT_PORT, MotorType.kBrushless);

    private final RelativeEncoder leftEncoder = leftTop.getEncoder();
    private final RelativeEncoder rightEncoder = rightTop.getEncoder();

    private final AHRS gyro = new AHRS();

    private final DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(getRotation2d());

    public DriveTrain() {
        leftTop.restoreFactoryDefaults();
        leftBack.restoreFactoryDefaults();
        leftFront.restoreFactoryDefaults();
        rightTop.restoreFactoryDefaults();
        rightBack.restoreFactoryDefaults();
        rightFront.restoreFactoryDefaults();

        leftBack.follow(leftTop);
        leftFront.follow(leftTop);

        rightBack.follow(rightTop);
        rightFront.follow(rightTop);

        leftTop.setInverted(true);
        rightTop.setInverted(false);

        leftEncoder.setPositionConversionFactor(DriveConstants.DISTANCE_PER_ROTATION);
        leftEncoder.setVelocityConversionFactor(DriveConstants.DISTANCE_PER_ROTATION / 60); // Change from rpm to

        rightEncoder.setPositionConversionFactor(DriveConstants.DISTANCE_PER_ROTATION);
        rightEncoder.setVelocityConversionFactor(DriveConstants.DISTANCE_PER_ROTATION / 60);

        resetEncoders();

        applyLeaderStatusFrames(leftTop);
        applyFollowerStatusFrames(leftFront);
        applyFollowerStatusFrames(leftBack);

        applyLeaderStatusFrames(rightTop);
        applyFollowerStatusFrames(rightFront);
        applyFollowerStatusFrames(rightBack);

        leftTop.burnFlash();
        leftBack.burnFlash();
        leftFront.burnFlash();
        rightTop.burnFlash();
        rightBack.burnFlash();
        rightFront.burnFlash();

        Shuffleboard.getTab("DriveTrainRaw").addBoolean("Brake On?", this::isBrakeOn);
        Shuffleboard.getTab("DriveTrainRaw").add("Toggle Brake", new ToggleBrakeModeCommand(this));
        Shuffleboard.getTab("DriveTrainRaw").addNumber("Gyro", this::getHeading);
    }

    private static void applyFollowerStatusFrames(CANSparkMax follower) {
        follower.setPeriodicFramePeriod(PeriodicFrame.kStatus0, 255);
        follower.setPeriodicFramePeriod(PeriodicFrame.kStatus1, 255);
        follower.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 255);
        follower.setPeriodicFramePeriod(PeriodicFrame.kStatus3, 255);
    }

    private static void applyLeaderStatusFrames(CANSparkMax leader) {
        leader.setPeriodicFramePeriod(PeriodicFrame.kStatus3, 255);
    }

    public void resetEncoders() {
        leftEncoder.setPosition(0);
        rightEncoder.setPosition(0);
    }

    @Override
    public void periodic() {
        odometry.update(getRotation2d(), getLeftEncoderDistance(), getRightEncoderDistance());
    }

    public Pose2d getPosition() {
        return odometry.getPoseMeters();
    }

    public void resetOdometry(Pose2d pose) {
        resetEncoders();
        odometry.resetPosition(pose, getRotation2d());
    }

    public void tankDrive(double leftSpeed, double rightSpeed) {
        leftTop.set(leftSpeed);
        rightTop.set(rightSpeed);
    }

    public void voltageDrive(double leftVoltage, double rightVoltage) {
        leftTop.setVoltage(leftVoltage);
        rightTop.setVoltage(rightVoltage);

    }

    public double getLeftEncoderDistance() {
        return leftEncoder.getPosition();
    }

    public double getRightEncoderDistance() {
        return rightEncoder.getPosition();
    }

    /**
     * @return the rate of change of the left encoder (meters/second)
     */
    public double getLeftEncoderRate() {
        return leftEncoder.getVelocity();
    }

    /**
     * @return the rate of change of the right encoder (meters/second)
     */
    public double getRightEncoderRate() {
        return rightEncoder.getVelocity();
    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(getLeftEncoderRate(), getRightEncoderRate());
    }

    /**
     * @return -180 - 180. Unit circle (counter-clockwise positive)
     */
    public double getHeading() {
        return -Math.IEEEremainder(gyro.getAngle(), 360);
    }

    public Rotation2d getRotation2d() {
        return gyro.getRotation2d();
    }

    public void setBrakeMode(boolean brakeOn) {
        if (brakeOn) {
            leftTop.setIdleMode(IdleMode.kBrake);
            leftBack.setIdleMode(IdleMode.kBrake);
            leftFront.setIdleMode(IdleMode.kBrake);
            rightTop.setIdleMode(IdleMode.kBrake);
            rightBack.setIdleMode(IdleMode.kBrake);
            rightFront.setIdleMode(IdleMode.kBrake);
        } else {
            leftTop.setIdleMode(IdleMode.kCoast);
            leftBack.setIdleMode(IdleMode.kCoast);
            leftFront.setIdleMode(IdleMode.kCoast);
            rightTop.setIdleMode(IdleMode.kCoast);
            rightBack.setIdleMode(IdleMode.kCoast);
            rightFront.setIdleMode(IdleMode.kCoast);
        }
    }

    public void toggleBrakeMode() {
        setBrakeMode(!isBrakeOn());
    }

    public boolean isBrakeOn() {
        return leftTop.getIdleMode() == IdleMode.kBrake;
    }
}
