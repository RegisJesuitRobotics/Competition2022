package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.utils.ShuffleboardTabs;

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

    private final DifferentialDrive differentialDrive = new DifferentialDrive(leftTop, rightTop);

    private final DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(getRotation2d());
    private final Field2d field2d = new Field2d();

    public DriveTrain() {
        leftBack.follow(leftTop);
        leftFront.follow(leftTop);

        rightBack.follow(rightTop);
        rightFront.follow(rightTop);

        leftEncoder.setPositionConversionFactor(DriveConstants.DISTANCE_PER_ROTATION);
        leftEncoder.setVelocityConversionFactor(DriveConstants.DISTANCE_PER_ROTATION / 60); // Change from rpm to
                                                                                            // meters/second

        rightEncoder.setPositionConversionFactor(DriveConstants.DISTANCE_PER_ROTATION);
        rightEncoder.setPositionConversionFactor(DriveConstants.DISTANCE_PER_ROTATION / 60);

        ShuffleboardTabs.getAutoTab().addNumber("Left Encoder", this::getLeftEncoderDistance);
        ShuffleboardTabs.getAutoTab().addNumber("Right Encoder", this::getRightEncoderDistance);
        ShuffleboardTabs.getAutoTab().add("Field", field2d);
        ShuffleboardTabs.getAutoTab().add("Gyro", gyro);
    }

    public void resetEncoders() {
        leftEncoder.setPosition(0);
        rightEncoder.setPosition(0);
    }

    @Override
    public void periodic() {
        odometry.update(getRotation2d(), getLeftEncoderDistance(), getRightEncoderDistance());
        field2d.setRobotPose(odometry.getPoseMeters());
    }

    public Pose2d getPosition() {
        return odometry.getPoseMeters();
    }

    public void resetOdometry(Pose2d pose) {
        resetEncoders();
        odometry.resetPosition(pose, getRotation2d());
    }

    public void arcadeDrive(double xSpeed, double zRotation) {
        differentialDrive.arcadeDrive(xSpeed, zRotation);
    }

    public void tankDrive(double leftSpeed, double rightSpeed) {
        differentialDrive.tankDrive(leftSpeed, rightSpeed);
    }

    public void voltageDrive(double leftVoltage, double rightVoltage) {
        leftTop.setVoltage(leftVoltage);
        rightTop.setVoltage(rightVoltage);

        differentialDrive.feed();
    }

    public double getLeftEncoderDistance() {
        return leftEncoder.getPosition();
    }

    public double getRightEncoderDistance() {
        return rightEncoder.getPosition();
    }

    public double getAverageEncodersDistance() {
        return (getLeftEncoderDistance() + getRightEncoderDistance()) / 2;
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

    public double getHeading() {
        return Math.IEEEremainder(gyro.getAngle(), 360);
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

    public void setField2dTrajectory(Trajectory trajectory) {
        field2d.getObject("traj").setTrajectory(trajectory);
    }
}
