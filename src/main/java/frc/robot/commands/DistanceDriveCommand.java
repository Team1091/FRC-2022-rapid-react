package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrainSubsystem;

import java.util.function.Supplier;

public class DistanceDriveCommand extends CommandBase {
    private final DriveTrainSubsystem driveTrainSubsystem;
    private final Double xDistance;
    private final Double yDistance;
    private final Double rotationVelocity;

    public DistanceDriveCommand(
            DriveTrainSubsystem driveTrainSubsystem,
            Double xDistance,
            Double yDistance,
            Double rotationVelocity
    ) {
        this.driveTrainSubsystem = driveTrainSubsystem;
        this.xDistance = xDistance;
        this.yDistance = yDistance;
        this.rotationVelocity = rotationVelocity;
        addRequirements(this.driveTrainSubsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        driveTrainSubsystem.mecanumDrive(xDistance, yDistance, rotationVelocity);
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
