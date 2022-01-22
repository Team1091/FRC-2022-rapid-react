package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import org.opencv.core.Point;

import java.util.Comparator;


public class TurnToBallCommand extends CommandBase {
    private final VisionSubsystem visionSubsystem;
    private final DriveTrainSubsystem driveTrainSubsystem;
    private Point lastSeenPosition = null;
    private final int tolerance;

    public TurnToBallCommand(
            VisionSubsystem visionSubsystem,
            DriveTrainSubsystem driveTrainSubsystem) {
        this (visionSubsystem,driveTrainSubsystem,10);
    }

    public TurnToBallCommand(
            VisionSubsystem visionSubsystem,
            DriveTrainSubsystem driveTrainSubsystem,
            int tolerance) {
        this.visionSubsystem = visionSubsystem;
        this.driveTrainSubsystem = driveTrainSubsystem;
        this.tolerance = tolerance;
        addRequirements(this.driveTrainSubsystem);
        addRequirements(this.visionSubsystem);
    }


    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        lastSeenPosition = visionSubsystem.getClosestBall();
       if (lastSeenPosition==null){
           lookForBall();
           return;
       }

        turnToBall();
    }

    @Override
    public void end(boolean interrupted) {
        driveTrainSubsystem.mecanumDrive(0,0,0);
    }

    @Override
    public boolean isFinished() {
        var ballInSight = lastSeenPosition!=null;

        return ballInSight &&
                Math.abs(lastSeenPosition.x-Constants.Vision.resizeImageWidth/2)< tolerance;
    }

    private void lookForBall() {
        driveTrainSubsystem.mecanumDrive(0,0,0.3);
    }

    private void turnToBall() {
        if (lastSeenPosition.x> Constants.Vision.resizeImageWidth/2){
            driveTrainSubsystem.mecanumDrive(0,0,0.4);
            return;
        }

        driveTrainSubsystem.mecanumDrive(0,0,-0.4);
    }
}