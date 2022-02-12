package frc.robot.commands.limelight;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LimeLight;

public abstract class LimeLightCommand extends CommandBase {
    protected final LimeLight limeLight;

    protected LimeLightCommand(LimeLight limeLight) {
        this.limeLight = limeLight;
    }

    @Override
    public void initialize() {
        limeLight.setLightMode(LimeLight.LightMode.ON);
    }
}
