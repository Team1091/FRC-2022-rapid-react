package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrainSubsystem;

import java.util.function.Supplier;

public class MacanumDriveCommand extends CommandBase {
    private final DriveTrainSubsystem driveTrainSubsystem;
    private final Supplier<Double> xVelocity;
    private final Supplier<Double> yVelocity;
    private final Supplier<Double> rotationVelocity;

    public MacanumDriveCommand(
            DriveTrainSubsystem driveTrainSubsystem,
            Supplier<Double> xVelocity,
            Supplier<Double> yVelocity,
            Supplier<Double> rotationVelocity
    ) {
        this.driveTrainSubsystem = driveTrainSubsystem;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.rotationVelocity = rotationVelocity;
        addRequirements(this.driveTrainSubsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        driveTrainSubsystem.mecanumDrive(xVelocity.get(), yVelocity.get(), rotationVelocity.get());
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
