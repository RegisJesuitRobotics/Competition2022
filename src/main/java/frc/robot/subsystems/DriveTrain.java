package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;
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
    private final WPI_TalonFX leftLeader = new WPI_TalonFX(DriveConstants.LEFT_LEADER_PORT);
    private final WPI_TalonFX leftFollower = new WPI_TalonFX(DriveConstants.LEFT_FOLLOWER_PORT);

    private final WPI_TalonFX rightLeader = new WPI_TalonFX(DriveConstants.RIGHT_LEADER_PORT);
    private final WPI_TalonFX rightFollower = new WPI_TalonFX(DriveConstants.RIGHT_FOLLOWER_PORT);

    private final AHRS gyro = new AHRS();

    private final DifferentialDrive differentialDrive = new DifferentialDrive(leftLeader, rightLeader);

    private final DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(gyro.getRotation2d());
    private final Field2d field2d = new Field2d();

    public DriveTrain() {
        leftFollower.follow(leftLeader);
        rightFollower.follow(rightLeader);

        rightLeader.setInverted(true);

        leftFollower.setInverted(InvertType.FollowMaster);
        rightFollower.setInverted(InvertType.FollowMaster);

        ShuffleboardTabs.getAutoTab().addNumber("Left Encoder", this::getLeftEncoderDistance);
        ShuffleboardTabs.getAutoTab().addNumber("Right Encoder", this::getRightEncoderDistance);
        ShuffleboardTabs.getAutoTab().add("Field", field2d);
        ShuffleboardTabs.getAutoTab().add("Gyro", gyro);
    }

    public void resetEncoders() {
        leftLeader.setSelectedSensorPosition(0);
        rightLeader.setSelectedSensorPosition(0);
    }

    @Override
    public void periodic() {
        odometry.update(gyro.getRotation2d(), getLeftEncoderDistance(), getRightEncoderDistance());
        field2d.setRobotPose(odometry.getPoseMeters());
    }

    public Pose2d getPosition() {
        return odometry.getPoseMeters();
    }

    public void resetOdometry(Pose2d pose) {
        resetEncoders();
        odometry.resetPosition(pose, gyro.getRotation2d());
    }

    public void arcadeDrive(double xSpeed, double zRotation) {
        differentialDrive.arcadeDrive(xSpeed, zRotation);
    }

    public void tankDrive(double leftSpeed, double rightSpeed) {
        differentialDrive.tankDrive(leftSpeed, rightSpeed);
    }

    public void voltageDrive(double leftVoltage, double rightVoltage) {
        leftLeader.setVoltage(leftVoltage);
        rightLeader.setVoltage(rightVoltage);

        differentialDrive.feed();
    }

    public double getLeftEncoderDistance() {
        return leftLeader.getSelectedSensorPosition() * DriveConstants.DISTANCE_PER_COUNT;
    }

    public double getRightEncoderDistance() {
        return rightLeader.getSelectedSensorPosition() * DriveConstants.DISTANCE_PER_COUNT;
    }

    public double getAverageEncodersDistance() {
        return (getLeftEncoderDistance() + getRightEncoderDistance()) / 2;
    }

    /**
     * @return the rate of change of the left encoder (meters/second)
     */
    public double getLeftEncoderRate() {
        return leftLeader.getSelectedSensorVelocity() * DriveConstants.DISTANCE_PER_COUNT * 10;
    }

    /**
     * @return the rate of change of the right encoder (meters/second)
     */
    public double getRightEncoderRate() {
        return rightLeader.getSelectedSensorVelocity() * DriveConstants.DISTANCE_PER_COUNT * 10;
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
            leftLeader.setNeutralMode(NeutralMode.Brake);
            leftFollower.setNeutralMode(NeutralMode.Brake);
            rightLeader.setNeutralMode(NeutralMode.Brake);
            rightFollower.setNeutralMode(NeutralMode.Brake);
        } else {
            leftLeader.setNeutralMode(NeutralMode.Coast);
            leftFollower.setNeutralMode(NeutralMode.Coast);
            rightLeader.setNeutralMode(NeutralMode.Coast);
            rightFollower.setNeutralMode(NeutralMode.Coast);
        }
    }

    public void setField2dTrajectory(Trajectory trajectory) {
        field2d.getObject("traj").setTrajectory(trajectory);
    }
}
