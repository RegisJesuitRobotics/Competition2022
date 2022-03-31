package frc.robot.joysticks;

import edu.wpi.first.wpilibj.PS4Controller.*;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

@SuppressWarnings("unused")
public class PlaystationController extends RaiderJoystick {
    public final JoystickButton square = new JoystickButton(this, Button.kSquare.value);
    public final JoystickButton x = new JoystickButton(this, Button.kCross.value);
    public final JoystickButton circle = new JoystickButton(this, Button.kCircle.value);
    public final JoystickButton triangle = new JoystickButton(this, Button.kTriangle.value);

    public final JoystickButton leftButton = new JoystickButton(this, Button.kL1.value);
    public final JoystickButton rightButton = new JoystickButton(this, Button.kR1.value);

    public final Trigger leftTrigger = new Trigger(this, Axis.kL2.value);
    public final Trigger rightTrigger = new Trigger(this, Axis.kR2.value);

    public final JoystickButton share = new JoystickButton(this, Button.kShare.value);
    public final JoystickButton options = new JoystickButton(this, Button.kOptions.value);

    public final ThumbStick leftThumb = new ThumbStick(this, Button.kL3.value, Axis.kLeftX.value, Axis.kLeftY.value);
    public final ThumbStick rightThumb = new ThumbStick(this, Button.kR3.value, Axis.kRightX.value, Axis.kRightY.value);

    public final JoystickButton playstationButton = new JoystickButton(this, Button.kPS.value);
    public final JoystickButton touchpad = new JoystickButton(this, Button.kTouchpad.value);

    public final DPad dPad = new DPad(this);


    /**
     * Construct an instance of a device.
     *
     * @param port The port index on the Driver Station that the device is plugged
     *             into.
     */
    public PlaystationController(int port) {
        super(port);
    }

}
