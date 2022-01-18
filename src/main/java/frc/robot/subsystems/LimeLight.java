package frc.robot.subsystems;


import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LimeLightConstants;

public class LimeLight extends SubsystemBase {
    public enum LightMode {
        DEFAULT(0),
        OFF(1),
        BLINK(2),
        ON(3);

        final int value;

        LightMode(int value) {
            this.value = value;
        }
    }

    private final NetworkTable limelightNetworkTable = NetworkTableInstance.getDefault()
            .getTable(LimeLightConstants.networkTablesId);
    private final NetworkTableEntry validTarget = limelightNetworkTable.getEntry("tv");
    private final NetworkTableEntry horizontalOffset = limelightNetworkTable.getEntry("tx");
    private final NetworkTableEntry verticalOffset = limelightNetworkTable.getEntry("ty");
    private final NetworkTableEntry targetArea = limelightNetworkTable.getEntry("ta");
    private final NetworkTableEntry ledMode = limelightNetworkTable.getEntry("ledMode");
    private final NetworkTableEntry camMode = limelightNetworkTable.getEntry("camMode");
    private final NetworkTableEntry pipeline = limelightNetworkTable.getEntry("pipeline");

    public LimeLight() {
        ShuffleboardTab limelightTab = Shuffleboard.getTab("limelight");

        limelightTab.addBoolean("Valid Target?", () -> validTarget.getBoolean(false));
        limelightTab.addNumber("Horizontal Offset", () -> horizontalOffset.getDouble(0));
        limelightTab.addNumber("Vertical Offset", () -> verticalOffset.getDouble(0));

        limelightTab.addCamera("Limelight", "Limelight", LimeLightConstants.streamURL);
    }

    public boolean hasValidTarget() {
        return validTarget.getBoolean(false);
    }

    public double getHorizontalOffset() {
        return horizontalOffset.getDouble(0);
    }

    public double getVerticalOffset() {
        return verticalOffset.getDouble(0);
    }

    public double getTargetArea() {
        return targetArea.getDouble(0);
    }

    public void setLightMode(LightMode mode) {
        ledMode.setNumber(mode.value);
    }

    public void setDriverCameraMode(boolean driverCameraMode) {
        if (driverCameraMode) {
            camMode.setNumber(1);
        } else {
            camMode.setNumber(0);
        }
    }

    public void setPipeline(int pipelineId) {
        if (pipelineId < 0 || pipelineId > 9) {
            throw new IllegalArgumentException("Pipeline id must be between 0..9 (inclusive)");
        }
        pipeline.setNumber(pipelineId);
    }
}
