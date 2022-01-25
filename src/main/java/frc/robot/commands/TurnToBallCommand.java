package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.vision.BallLocation;

public class TurnToBallCommand extends CommandBase {
    private final VisionSubsystem visionSubsystem;
    private final DriveTrainSubsystem driveTrainSubsystem;
    private BallLocation lastSeenPosition = null;
    private final int tolerance;
    private final double rotationSpeed = .4;

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

        if(visionSubsystem.isImageNotUpdating()){
            return true;
        }

        var ballInSight = lastSeenPosition!=null;
        boolean ballScoped = ballInSight &&
                             Math.abs(lastSeenPosition.getPoint().x - Constants.Vision.resizeImageWidth / 2) < tolerance;
        return ballScoped;
    }

    private void lookForBall() {
        driveTrainSubsystem.mecanumDrive(0,0,rotationSpeed);
    }

    private void turnToBall() {
        if (lastSeenPosition.getPoint().x> Constants.Vision.resizeImageWidth/2){
            driveTrainSubsystem.mecanumDrive(0,0,rotationSpeed);
            return;
        }

        driveTrainSubsystem.mecanumDrive(0,0,-rotationSpeed);
    }
}