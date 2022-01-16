package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class DriveTrain extends SubsystemBase {
    private final WPI_TalonSRX leftLeader = new WPI_TalonSRX(DriveConstants.leftLeaderPort);
    private final WPI_TalonSRX leftFollower = new WPI_TalonSRX(DriveConstants.leftFollowerPort);

    private final WPI_TalonSRX rightLeader = new WPI_TalonSRX(DriveConstants.rightLeaderPort);
    private final WPI_TalonSRX rightFollower = new WPI_TalonSRX(DriveConstants.rightFollowerPort);

    private final AHRS gyro = new AHRS();

    private final DifferentialDrive differentialDrive = new DifferentialDrive(leftLeader, rightLeader);

    public DriveTrain() {
        leftFollower.follow(leftLeader);
        rightFollower.follow(rightLeader);

        rightLeader.setInverted(true);

        leftFollower.setInverted(InvertType.FollowMaster);
        rightFollower.setInverted(InvertType.FollowMaster);
    }

    public void resetSensors() {
        leftLeader.setSelectedSensorPosition(0);
        rightLeader.setSelectedSensorPosition(0);

        gyro.reset();
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
}
