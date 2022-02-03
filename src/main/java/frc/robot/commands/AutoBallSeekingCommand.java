package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.vision.BallLocation;

public class AutoBallSeekingCommand extends CommandBase {
    private final VisionSubsystem visionSubsystem;
    private final DriveTrainSubsystem driveTrainSubsystem;
    private final int middleOfCam = Constants.Vision.resizeImageWidth / 2;
    private final int forwardTolerance = Constants.Vision.resizeImageWidth / 10;
    private final int strafeTolerance = Constants.Vision.resizeImageWidth / 5;

    public AutoBallSeekingCommand(
            DriveTrainSubsystem driveTrainSubsystem,
            VisionSubsystem visionSubsystem
    ) {
        this.visionSubsystem = visionSubsystem;
        this.driveTrainSubsystem = driveTrainSubsystem;
        addRequirements(this.driveTrainSubsystem);
        addRequirements(this.visionSubsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        var ballLocation = visionSubsystem.getClosestBall();

        if (ballLocation != null){
            SmartDashboard.putString("ballLocation", ballLocation.toString());
        }

        boolean cantSeeBall = ballLocation == null;
        if (cantSeeBall) {
            lookForBall();
            return;
        }

        if (canDriveForward(ballLocation)) {
            driveForward();
            return;
        }

        if (canStrafe(ballLocation)) {
            strafe(ballLocation);
            return;
        }

        turnToBall(ballLocation);
    }

    @Override
    public void end(boolean interrupted) {
        stopDriving();
    }

    @Override
    public boolean isFinished() {
        if (visionSubsystem.isImageNotUpdating()) {
            SmartDashboard.putNumber("name", System.currentTimeMillis());
            return true;
        }

        BallLocation closestBall = visionSubsystem.getClosestBall();
        if (closestBall == null){
            return false;
        }

        return closestBall.canPickUpBall(Constants.Vision.resizeImageHeight / 20);
    }

    public boolean canDriveForward(BallLocation ballLocation) {
        boolean lowerForwardTolerance = ballLocation.getPoint().x >= middleOfCam - forwardTolerance;
        boolean upperForwardTolerance = ballLocation.getPoint().x <= middleOfCam + forwardTolerance;
        return lowerForwardTolerance && upperForwardTolerance;
    }

    public boolean canStrafe(BallLocation ballLocation) {
        boolean lowerStrafeTolerance = ballLocation.getPoint().x >= middleOfCam - forwardTolerance - strafeTolerance;
        boolean upperStrafeTolerance = ballLocation.getPoint().x <= middleOfCam + forwardTolerance + strafeTolerance;
        return lowerStrafeTolerance && upperStrafeTolerance;
    }

    private void strafe(BallLocation ballLocation) {
        boolean inLeftStrafeTolerance = ballLocation.getPoint().x < middleOfCam - forwardTolerance && ballLocation.getPoint().x >= middleOfCam - strafeTolerance;
        if (inLeftStrafeTolerance) {
            driveTrainSubsystem.mecanumDrive(-0.5, 0, 0);
            return;
        }
        boolean inRightStrafeTolerance = ballLocation.getPoint().x > middleOfCam + forwardTolerance && ballLocation.getPoint().x <= middleOfCam + strafeTolerance;
        if (inRightStrafeTolerance) {
            driveTrainSubsystem.mecanumDrive(0.5, 0, 0);
        }
    }

    private void turnToBall(BallLocation ballLocation) {
        if (ballLocation.getPoint().x < Constants.Vision.resizeImageWidth - strafeTolerance) {
            driveTrainSubsystem.mecanumDrive(0, 0, -0.5);
            return;
        }
        driveTrainSubsystem.mecanumDrive(0, 0, 0.5);
    }

    private void lookForBall() {
        driveTrainSubsystem.mecanumDrive(0, 0, 0.4);
    }

    private void driveForward() {
        driveTrainSubsystem.mecanumDrive(0, 0.5, 0);
    }

    private void stopDriving() {
        driveTrainSubsystem.mecanumDrive(0, 0, 0);
    }
}