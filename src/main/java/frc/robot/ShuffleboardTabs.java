package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class ShuffleboardTabs {
    private static ShuffleboardTab autoTab;
    private static ShuffleboardTab teleopTab;

    public static ShuffleboardTab getAutoTab() {
        if (autoTab == null) {
            autoTab = Shuffleboard.getTab("Auto");
        }
        return autoTab;
    }

    public static ShuffleboardTab getTeleopTab() {
        if (teleopTab == null) {
            teleopTab = Shuffleboard.getTab("Teleop");
        }
        return teleopTab;
    }

    public static void selectAutoTab() {
        Shuffleboard.selectTab(autoTab.getTitle());
    }

    public static void selectTeleopTab() {
        Shuffleboard.selectTab(teleopTab.getTitle());
    }
}
