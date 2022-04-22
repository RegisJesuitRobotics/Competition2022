package frc.robot.joysticks;

import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class PseudoXboxController extends RaiderJoystick {
    public final JoystickButton square = new JoystickButton(this, Button.kX.value);
    public final JoystickButton x = new JoystickButton(this, Button.kA.value);
    public final JoystickButton circle = new JoystickButton(this, Button.kB.value);
    public final JoystickButton triangle = new JoystickButton(this, Button.kY.value);

    public final JoystickButton leftButton = new JoystickButton(this, Button.kLeftBumper.value);
    public final JoystickButton rightButton = new JoystickButton(this, Button.kRightBumper.value);

    public final Trigger leftTrigger = new Trigger(this, Axis.kLeftTrigger.value);
    public final Trigger rightTrigger = new Trigger(this, Axis.kRightTrigger.value);

    public final JoystickButton share = new JoystickButton(this, Button.kBack.value);
    public final JoystickButton options = new JoystickButton(this, Button.kStart.value);

    public final ThumbStick leftThumb = new ThumbStick(
            this, Button.kLeftStick.value, Axis.kLeftX.value, Axis.kLeftY.value
    );
    public final ThumbStick rightThumb = new ThumbStick(
            this, Button.kRightStick.value, Axis.kRightX.value, Axis.kRightY.value
    );

    public final DPad dPad = new DPad(this);


    public PseudoXboxController(int port) {
        super(port);
    }
}
