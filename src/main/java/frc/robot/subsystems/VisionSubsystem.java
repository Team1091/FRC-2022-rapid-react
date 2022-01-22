package frc.robot.subsystems;

import edu.wpi.first.cscore.VideoCamera;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.vision.BallPosition;

import frc.robot.vision.GripPipeline;

import java.util.ArrayList;
import java.util.List;

public class VisionSubsystem extends SubsystemBase {
    private final int cameraPort = Constants.Vision.cameraPort;
    private VideoCamera videoCamera;
    //private Mat videoMatrix;
    private GripPipeline gripPipeline;

    public VisionSubsystem(VideoCamera videoCamera) {
        this.videoCamera = videoCamera;
        this.gripPipeline = new GripPipeline();
    }

    public List<BallPosition> getBallLocation() {
        return new ArrayList<BallPosition>();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        //videoCamera
        //gripPipeline.process(videoMatrix);
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}
