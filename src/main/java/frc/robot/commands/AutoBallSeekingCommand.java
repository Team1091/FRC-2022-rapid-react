package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class AutoBallSeekingCommand extends CommandBase
{
    private final int forwardTolerance = 7;
    private final int strafeTolerance = 35;
    private final DriveTrainSubsystem driveTrainSubsystem;
    private final VisionSubsystem visionSubsystem;
    private boolean UltimateFail = false;
    private int timerCounter = 0;

    public AutoBallSeekingCommand(DriveTrainSubsystem driveTrainSubsystem, VisionSubsystem visionSubsystem){
        this.driveTrainSubsystem = driveTrainSubsystem;
        this.visionSubsystem = visionSubsystem;
    }
    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if(timerCounter < 420){
           if (visionSubsystem.getClosestBall().getPoint().x < forwardTolerance){//determines if within forward tolerance
               driveTrainSubsystem.mecanumDrive(0, 0.8, 0);
           } else if (visionSubsystem.getClosestBall().getPoint().x < strafeTolerance){//determines if within strafe tolerance
               if(visionSubsystem.getClosestBall().getPoint().x < Constants.Vision.resizeImageWidth / 2){//on left
                   driveTrainSubsystem.mecanumDrive(0.8, 0.3, 0);
               }else if(visionSubsystem.getClosestBall().getPoint().x > Constants.Vision.resizeImageWidth / 2){//on right
                   driveTrainSubsystem.mecanumDrive(-0.8, 0.3, 0);
               }else {
                   //weird case that should never happen... do nothing
               }
           } else {
               driveTrainSubsystem.mecanumDrive(0, 0, 0);
               if(visionSubsystem.getBallLocations() != null) {//sees ball
                   if (visionSubsystem.getClosestBall().getPoint().x < Constants.Vision.resizeImageWidth / 2) {// on left
                       driveTrainSubsystem.mecanumDrive(0, 0, 0.8);
                   } else if (visionSubsystem.getClosestBall().getPoint().x > Constants.Vision.resizeImageWidth / 2) {// on right
                       driveTrainSubsystem.mecanumDrive(0, 0, -0.8);
                   }
               }else {
                   driveTrainSubsystem.mecanumDrive(0, 0, 0.4);
                   timerCounter=timerCounter+1;
               }
           }
        }else{
            UltimateFail = true;
        }driveTrainSubsystem.mecanumDrive(0, 0.3, 0.4);
    }

    @Override
    public void end(boolean interrupted) {
        driveTrainSubsystem.mecanumDrive(0,0,0);
    }

    @Override
    public boolean isFinished() {

        if(visionSubsystem.isImageNotUpdating()){
            return true;
        } else if (UltimateFail){
            return true;
        }else{
            return false;
        }
    }
}
