package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrainSubsystem;

import java.util.function.Supplier;

public class MecanumDriveCommand extends CommandBase {
    private final DriveTrainSubsystem driveTrainSubsystem;
    private final Supplier<Double> strafeVelocity;
    private final Supplier<Double> forwardBackwardVelocity;
    private final Supplier<Double> rotationVelocity;

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
        driveTrainSubsystem.mecanumDrive(strafeVelocity.get(), forwardBackwardVelocity.get(), rotationVelocity.get());
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
