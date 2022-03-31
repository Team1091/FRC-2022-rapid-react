package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrainSubsystem;

public class TurnCommand extends CommandBase {
    private final DriveTrainSubsystem driveTrainSubsystem;
    private boolean isClock;
    private double speed;
    //private final Double degreeTarget;

    public TurnCommand(
            DriveTrainSubsystem driveTrainSubsystem,
            boolean isClock,
            double speed
            //Double degreeClockwise
    ) {
        this.driveTrainSubsystem = driveTrainSubsystem;
        this.isClock = isClock;
        this.speed = speed;
        //this.degreeTarget = degreeClockwise;
        addRequirements(this.driveTrainSubsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if (isClock) {
            driveTrainSubsystem.mecanumDrive(0, 0, speed);
        } else {
            driveTrainSubsystem.mecanumDrive(0, 0, -speed);
        }

    }

    @Override
    public void end(boolean interrupted) {
        driveTrainSubsystem.mecanumDrive(0, 0, 0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
