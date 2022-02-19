package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.AccelerationCurve;
import frc.robot.subsystems.DriveTrainSubsystem;

import java.util.function.Supplier;

public class MecanumDriveCommand extends CommandBase {
    private final DriveTrainSubsystem driveTrainSubsystem;
    private final Supplier<Double> strafeVelocity;
    private final Supplier<Double> forwardBackwardVelocity;
    private final Supplier<Double> rotationVelocity;

    private final AccelerationCurve strafeCurve = new AccelerationCurve(3, .275, .6);
    private final AccelerationCurve forwardBackwardCurve = new AccelerationCurve(1, .125, .6);
    private final AccelerationCurve rotationCurve = new AccelerationCurve(3, .275, .6);

    public MecanumDriveCommand(
            DriveTrainSubsystem driveTrainSubsystem,
            Supplier<Double> strafeVelocity,
            Supplier<Double> forwardBackwardVelocity,
            Supplier<Double> rotationVelocity
    ) {
        this.driveTrainSubsystem = driveTrainSubsystem;
        this.strafeVelocity = strafeVelocity;
        this.forwardBackwardVelocity = forwardBackwardVelocity;
        this.rotationVelocity = rotationVelocity;
        addRequirements(this.driveTrainSubsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        strafeCurve.set(strafeVelocity.get());
        forwardBackwardCurve.set((forwardBackwardVelocity.get()));
        rotationCurve.set(rotationVelocity.get());

        driveTrainSubsystem.mecanumDrive(
                strafeCurve.getCurrentSpeed(),
                forwardBackwardCurve.getCurrentSpeed(),
                rotationCurve.getCurrentSpeed());
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
