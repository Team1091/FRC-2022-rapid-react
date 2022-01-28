package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.vision.BallLocation;

public class TheBetterBallCommand extends CommandBase {
    private final VisionSubsystem visionSubsystem;
    private final DriveTrainSubsystem driveTrainSubsystem;
    private final int middleOfCam = Constants.Vision.resizeImageWidth / 2;
    private final int forwardToleance = Constants.Vision.resizeImageWidth / 10;
    private final int strafeTolerance = Constants.Vision.resizeImageWidth / 5;
    private int timerCounter = 0;
    private int ballSearchTimeOut = 420;

    public TheBetterBallCommand(
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

        boolean cantSeeBall = ballLocation == null;
        if (cantSeeBall) {
            lookForBall();
            return;
        }

        resetBallSearchTimeOut();

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

    private void resetBallSearchTimeOut() {
        timerCounter = 0;
    }

    @Override
    public void end(boolean interrupted) {
        stopDriving();
    }

    @Override
    public boolean isFinished() {
        if (visionSubsystem.isImageNotUpdating()) {
            return true;
        }
        if (timerCounter >= ballSearchTimeOut){
            return true;
        }

        return visionSubsystem.getClosestBall().canPickUpBall(Constants.Vision.resizeImageHeight / 20);
    }

    public boolean canDriveForward(BallLocation ballLocation) {
        boolean lowerForwardTolerance = ballLocation.getPoint().x >= middleOfCam - forwardToleance;
        boolean upperForwardTolerance = ballLocation.getPoint().x <= middleOfCam + forwardToleance;
        return lowerForwardTolerance && upperForwardTolerance;
    }

    public boolean canStrafe(BallLocation ballLocation) {
        boolean lowerStrafeTolerance = ballLocation.getPoint().x >= middleOfCam - forwardToleance - strafeTolerance;
        boolean upperStrafeTolerance = ballLocation.getPoint().x <= middleOfCam + forwardToleance + strafeTolerance;
        return lowerStrafeTolerance && upperStrafeTolerance;
    }

    private void strafe(BallLocation ballLocation) {
        boolean inLeftStrafeTolerance = ballLocation.getPoint().x < middleOfCam - forwardToleance && ballLocation.getPoint().x >= middleOfCam - strafeTolerance;
        if (inLeftStrafeTolerance) {
            driveTrainSubsystem.mecanumDrive(-0.5, 0, 0);
            return;
        }
        boolean inRightStrafeTolerance = ballLocation.getPoint().x > middleOfCam + forwardToleance && ballLocation.getPoint().x <= middleOfCam + strafeTolerance;
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
        timerCounter = timerCounter + 1;
    }
    private void driveForward() {
        driveTrainSubsystem.mecanumDrive(0, 0.5, 0);
    }
    private void stopDriving() {
        driveTrainSubsystem.mecanumDrive(0, 0, 0);
    }
}