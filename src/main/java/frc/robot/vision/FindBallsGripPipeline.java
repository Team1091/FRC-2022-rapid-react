package frc.robot.vision;

import edu.wpi.first.vision.VisionPipeline;
import org.opencv.core.MatOfKeyPoint;

public interface FindBallsGripPipeline extends VisionPipeline {
    MatOfKeyPoint findBlobsOutput();
}
