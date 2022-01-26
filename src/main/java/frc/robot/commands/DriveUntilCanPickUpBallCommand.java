package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.vision.BallLocation;

public class DriveUntilCanPickUpBallCommand extends CommandBase {
    private final VisionSubsystem visionSubsystem;
    private final DriveTrainSubsystem driveTrainSubsystem;
    private final double speed = 0.4;
    private final int tolerance = Constants.Vision.resizeImageHeight/20;

    public DriveUntilCanPickUpBallCommand(
            VisionSubsystem visionSubsystem,
            DriveTrainSubsystem driveTrainSubsystem){
        this.visionSubsystem = visionSubsystem;
        this.driveTrainSubsystem = driveTrainSubsystem;
        addRequirements(this.driveTrainSubsystem);
        addRequirements(this.visionSubsystem);
    }

    @Override
    public void execute(){
        var ballLocation = visionSubsystem.getClosestBall();

        if (ballLocation==null){
            driveTrainSubsystem.mecanumDrive(0,0,0);
            return;
        }

        if (ballLocation.canPickUpBall(tolerance)){
            driveTrainSubsystem.mecanumDrive(0,0,0);
            return;
        }

        driveTrainSubsystem.mecanumDrive(0,speed,0);
    }

    @Override
    public void end(boolean interrupted) {
        driveTrainSubsystem.mecanumDrive(0,0,0);
    }

    @Override
    public boolean isFinished() {
        if (visionSubsystem.isImageNotUpdating()) {
            return true;
        }

        BallLocation closestBall = visionSubsystem.getClosestBall();
        if (closestBall == null){
            return true;
        }

        return closestBall.canPickUpBall(tolerance);
    }
}

