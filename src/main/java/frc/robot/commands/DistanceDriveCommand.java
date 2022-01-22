package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrainSubsystem;

public class DistanceDriveCommand extends CommandBase {
    private final DriveTrainSubsystem driveTrainSubsystem;
    private final Double xDistance;
    private double leftEncoderTarget;

    public DistanceDriveCommand(
            DriveTrainSubsystem driveTrainSubsystem,
            Double xDistance
    ) {
        this.driveTrainSubsystem = driveTrainSubsystem;
        this.xDistance = xDistance;
        addRequirements(this.driveTrainSubsystem);
    }

    @Override
    public void initialize() {
       this.leftEncoderTarget = driveTrainSubsystem.getLeftEncoder() + xDistance;
    }

    @Override
    public void execute() {
        driveTrainSubsystem.mecanumDrive(0.5, 0, 0);
    }

    @Override
    public void end(boolean interrupted) {
        driveTrainSubsystem.mecanumDrive(0, 0, 0);
    }

    @Override
    public boolean isFinished() {
        return (driveTrainSubsystem.getLeftEncoder() > leftEncoderTarget);
    }
}
