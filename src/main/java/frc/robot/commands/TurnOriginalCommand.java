package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrainSubsystem;

public class TurnOriginalCommand extends CommandBase {
    private final DriveTrainSubsystem driveTrainSubsystem;
    private double leftEncoder;
    private double rightEncoder;
    private boolean isReverse;
    private double tolerance = Constants.DriveTrain.originTolerance;

    public TurnOriginalCommand(DriveTrainSubsystem driveTrainSubsystem) {
        this.driveTrainSubsystem = driveTrainSubsystem;
        addRequirements(this.driveTrainSubsystem);
    }

    @Override
    public void initialize() {
        this.leftEncoder = driveTrainSubsystem.getLeftEncoder();
        this.rightEncoder = driveTrainSubsystem.getRightEncoder();
    }

    @Override
    public void execute() {
        if (Math.abs(leftEncoder - rightEncoder) > tolerance) {
            if (leftEncoder < rightEncoder) { //turn left
                driveTrainSubsystem.mecanumDrive(0,0,-0.4);
            } else { //turn right
                driveTrainSubsystem.mecanumDrive(0,0,0.4);
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
        driveTrainSubsystem.mecanumDrive(0, 0, 0);
    }

    @Override
    public boolean isFinished() {
        if (Math.abs(leftEncoder - rightEncoder) > tolerance) {
            return true;
        } else {
            return false;
        }
    }
}
