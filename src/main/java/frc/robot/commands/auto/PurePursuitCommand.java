package frc.robot.commands.auto;

import com.regisjesuit.purepursuit.PurePursuit;
import com.regisjesuit.purepursuit.path.PurePursuitPath;
import com.regisjesuit.purepursuit.utils.Point2d;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;
import frc.robot.utils.ShuffleboardTabs;

import static frc.robot.Constants.PurePursuitConstants.*;


public class PurePursuitCommand extends CommandBase {

    private final DriveTrain driveTrain;
    private final PurePursuit purePursuit;
    private final SlewRateLimiter leftRateLimiter;
    private final SlewRateLimiter rightRateLimiter;

    private final NetworkTableEntry targetLeftEntry;
    private final NetworkTableEntry targetRightEntry;
    private final NetworkTableEntry actualLeftEntry;
    private final NetworkTableEntry actualRightEntry;

    private DifferentialDriveWheelSpeeds previousSpeeds = new DifferentialDriveWheelSpeeds(0, 0);

    public PurePursuitCommand(DriveTrain driveTrain, int lookaheadDistance, Point2d... points) {
        this.driveTrain = driveTrain;

        PurePursuitPath path = new PurePursuitPath(MAX_VELOCITY, lookaheadDistance);

        for (Point2d point : points) {
            path.addPoint(point.x, point.y);
        }

        path.injectPoints();
        path.smoothPoints(SMOOTHING_A, SMOOTHING_B, SMOOTHING_TOLERANCE);

        path.calculateCurvatures();
        path.calculateMaxVelocities(VELOCITY_CONSTANT);
        path.calculateVelocities(MAX_VELOCITY);

        purePursuit = new PurePursuit(path, Constants.DriveConstants.trackWidthMeters);
        // Want to go zero to max in 1 second
        leftRateLimiter = new SlewRateLimiter(MAX_VELOCITY);
        rightRateLimiter = new SlewRateLimiter(MAX_VELOCITY);

        targetLeftEntry = ShuffleboardTabs.getAutoTab().add("Target Left", 0).getEntry();
        targetRightEntry = ShuffleboardTabs.getAutoTab().add("Target Right", 0).getEntry();
        actualLeftEntry = ShuffleboardTabs.getAutoTab().add("Actual Left", 0).getEntry();
        actualRightEntry = ShuffleboardTabs.getAutoTab().add("Actual Right", 0).getEntry();

        ShuffleboardTabs.getAutoTab().add("PurePursuit", purePursuit);

        addRequirements(driveTrain);
    }

    @Override
    public void initialize() {
        driveTrain.resetOdometry();

        // Rate limiters are time based, so we have to call it at the beginning.
        leftRateLimiter.calculate(0);
        rightRateLimiter.calculate(0);
    }

    /**
     * The main body of a command. Called repeatedly while the command is scheduled.
     * (That is, it is called repeatedly until {@link #isFinished()}) returns true.)
     */
    @Override
    public void execute() {
        DifferentialDriveWheelSpeeds speeds = purePursuit.calculate(driveTrain.getPosition());

        speeds.leftMetersPerSecond = leftRateLimiter.calculate(speeds.leftMetersPerSecond);
        speeds.rightMetersPerSecond = rightRateLimiter.calculate(speeds.rightMetersPerSecond);

        targetLeftEntry.setNumber(speeds.leftMetersPerSecond);
        targetRightEntry.setNumber(speeds.rightMetersPerSecond);
        actualLeftEntry.setNumber(driveTrain.getLeftEncoderRate());
        actualRightEntry.setNumber(driveTrain.getRightEncoderRate());

        double leftFeedback = KP * (speeds.leftMetersPerSecond - driveTrain.getLeftEncoderRate());
        double rightFeedback = KP * (speeds.rightMetersPerSecond - driveTrain.getRightEncoderRate());

        double leftFeedforward = KV * speeds.leftMetersPerSecond
                + KA * getAccel(speeds.leftMetersPerSecond, previousSpeeds.leftMetersPerSecond);
        double rightFeedforward = KV * speeds.rightMetersPerSecond
                + KA * getAccel(speeds.rightMetersPerSecond, previousSpeeds.rightMetersPerSecond);

        previousSpeeds = speeds;
        driveTrain.tankDrive(leftFeedforward + leftFeedback, rightFeedforward + rightFeedback);
    }

    private double getAccel(double targetVel, double previousTarget) {
        return (targetVel - previousTarget) / 0.02;
    }

    /**
     * <p>
     * Returns whether this command has finished. Once a command finishes --
     * indicated by this method returning true -- the scheduler will call its
     * {@link #end(boolean)} method.
     * </p>
     * <p>
     * Returning false will result in the command never ending automatically. It may
     * still be cancelled manually or interrupted by another command. Hard coding
     * this command to always return true will result in the command executing once
     * and finishing immediately. It is recommended to use *
     * {@link edu.wpi.first.wpilibj2.command.InstantCommand InstantCommand} for such
     * an operation.
     * </p>
     *
     * @return whether this command has finished.
     */
    @Override
    public boolean isFinished() {
        return purePursuit.isDone();
    }

    /**
     * The action to take when the command ends. Called when either the command
     * finishes normally -- that is it is called when {@link #isFinished()} returns
     * true -- or when it is interrupted/canceled. This is where you may want to
     * wrap up loose ends, like shutting off a motor that was being used in the
     * command.
     *
     * @param interrupted whether the command was interrupted/canceled
     */
    @Override
    public void end(boolean interrupted) {
        driveTrain.tankDrive(0, 0);
        targetLeftEntry.setNumber(0);
        targetRightEntry.setNumber(0);
    }
}
