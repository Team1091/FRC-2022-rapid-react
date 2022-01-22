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
    private  int tolerance;

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
        var ballLocations = visionSubsystem.getBallLocation();
        var closestBall = ballLocations.stream().max(Comparator.comparing(it->it.y)).get();
        lastSeenPosition = closestBall;
       if (closestBall==null){
           driveTrainSubsystem.mecanumDrive(0,0,0.3);
           return;
       }

       if (closestBall.x> Constants.Vision.resizeImageWidth/2){
           driveTrainSubsystem.mecanumDrive(0,0,0.4);

       } else{
           driveTrainSubsystem.mecanumDrive(0,0,-0.4);
       }

    }

    @Override
    public void end(boolean interrupted) {
        driveTrainSubsystem.mecanumDrive(0,0,0);
    }

    @Override
    public boolean isFinished() {
        var ballInSight = lastSeenPosition!=null;
        var ballScoped = ballInSight &&
                         Math.abs(lastSeenPosition.x-Constants.Vision.resizeImageWidth/2)< tolerance;

        return ballScoped;

    }
}