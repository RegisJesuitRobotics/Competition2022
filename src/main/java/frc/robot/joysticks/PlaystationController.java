package frc.robot.joysticks;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS4Controller.*;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.utils.MathUtils;

@SuppressWarnings("unused")
public class PlaystationController extends Joystick {

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

    public static class Trigger extends edu.wpi.first.wpilibj2.command.button.Button {
        private final int axisPort;
        private final GenericHID parent;

        public Trigger(GenericHID parent, int axisPort) {
            this.parent = parent;
            this.axisPort = axisPort;
        }


        @Override
        public boolean get() {
            return getAxis() >= 0.6;
        }

        /**
         * Get the axis of the trigger
         *
         * @return Between 0-1
         */
        public double getAxis() {
            return parent.getRawAxis(axisPort);
        }
    }

    public static class ThumbStick extends JoystickButton {
        public static final double thumbStickDeadZone = 0.1;
        private final int xAxisPort;
        private final int yAxisPort;

        private final GenericHID parent;

        public ThumbStick(GenericHID parent, int buttonNumber, int xAxisPort, int yAxisPort) {
            super(parent, buttonNumber);
            this.parent = parent;
            this.xAxisPort = xAxisPort;
            this.yAxisPort = yAxisPort;
        }

        public double getXAxis() {
            return MathUtils.deadZone(parent.getRawAxis(xAxisPort), thumbStickDeadZone);
        }

        public double getYAxis() {
            return -MathUtils.deadZone(parent.getRawAxis(yAxisPort), thumbStickDeadZone);
        }
    }

    public static class DPad {
        public final GenericHID parent;
        public final DPadButton up = new DPadButton(this, DPadDirection.UP);
        public final DPadButton down = new DPadButton(this, DPadDirection.DOWN);
        public final DPadButton left = new DPadButton(this, DPadDirection.LEFT);
        public final DPadButton right = new DPadButton(this, DPadDirection.RIGHT);

        public DPad(GenericHID parent) {
            this.parent = parent;
        }

        public int angle() {
            return parent.getPOV();
        }

        public enum DPadDirection {
            UP(0),
            RIGHT(90),
            DOWN(180),
            LEFT(270);

            public final int angle;

            DPadDirection(int angle) {
                this.angle = angle;
            }
        }

        public static class DPadButton extends edu.wpi.first.wpilibj2.command.button.Button {
            public final DPad parent;
            private final DPadDirection direction;

            public DPadButton(DPad parent, DPadDirection direction) {
                this.parent = parent;
                this.direction = direction;
            }

            @Override
            public boolean get() {
                return parent.angle() == direction.angle;
            }
        }
    }

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
