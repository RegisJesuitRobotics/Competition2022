package frc.robot.joysticks;

import edu.wpi.first.wpilibj.GenericHID;
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

    public final Trigger throttle = new Trigger(this, 3);
    public final DPad dPad = new DPad(this);
    private final ThreeAxisStick stick = new ThreeAxisStick(this, 0, 1, 2);

    public static class ThreeAxisStick {
        private final int xAxisPort;
        private final int yAxisPort;
        private final int zAxisPort;

        private final GenericHID parent;

        public ThreeAxisStick(GenericHID parent, int xAxisPort, int yAxisPort, int zAxisPort) {
            this.parent = parent;
            this.xAxisPort = xAxisPort;
            this.yAxisPort = yAxisPort;
            this.zAxisPort = zAxisPort;
        }

        public double getXAxis() {
            return parent.getRawAxis(xAxisPort);
        }

        public double getYAxis() {
            return parent.getRawAxis(yAxisPort);
        }

        public double getZAxis() {
            return parent.getRawAxis(zAxisPort);
        }
    }

    public Logitech3DProController(int port) {
        super(port);
    }
}
