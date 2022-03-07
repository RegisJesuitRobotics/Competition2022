package frc.robot.joysticks;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class Logitech3DProController extends RaiderJoystick {
    public final JoystickButton buttonOne = new JoystickButton(this, 1);
    public final JoystickButton buttonTwo = new JoystickButton(this, 2);
    public final JoystickButton buttonThree = new JoystickButton(this, 3);
    public final JoystickButton buttonFour = new JoystickButton(this, 4);
    public final JoystickButton buttonFive = new JoystickButton(this, 5);
    public final JoystickButton buttonSix = new JoystickButton(this, 6);
    public final JoystickButton buttonSeven = new JoystickButton(this, 7);
    public final JoystickButton buttonEight = new JoystickButton(this, 8);
    public final JoystickButton buttonNine = new JoystickButton(this, 9);
    public final JoystickButton buttonTen = new JoystickButton(this, 10);
    public final JoystickButton buttonEleven = new JoystickButton(this, 11);
    public final JoystickButton buttonTwelve = new JoystickButton(this, 12);

    // TODO: get this axis
    public final Trigger throttle = new Trigger(this, 1);
    public final DPad topStick = new DPad(this);


    public Logitech3DProController(int port) {
        super(port);
    }
}
