package frc.robot.subsystems;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.VideoCamera;
import edu.wpi.first.vision.VisionThread;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import frc.robot.vision.BallLocation;
import frc.robot.vision.GripPipeline;
import org.opencv.core.Point;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class VisionSubsystem extends SubsystemBase {
    private List<BallLocation> rawPositions;

    public VisionSubsystem() {
        VideoCamera camera = CameraServer.startAutomaticCapture(Constants.Vision.cameraPort);

        camera.setResolution(Constants.Vision.resizeImageWidth, Constants.Vision.resizeImageHeight);

        //so basically the above converts the findBlobsOutput to a list with a bunch of
        //points and then it is collected into a new list at the end
        VisionThread visionThread = new VisionThread(camera, new GripPipeline(), pipeline -> {
            if (!pipeline.findBlobsOutput().empty()) {
                rawPositions = pipeline.findBlobsOutput().toList().stream()
                        .map(it -> new BallLocation(it.pt))
                        .collect(Collectors.toList());
                //so basically the above converts the findBlobsOutput to a list with a bunch of
                //points and then it is collected into a new list at the end
            } else {
                rawPositions = new ArrayList<>();
            }
        });
        visionThread.start();
    }

    public List<BallLocation> getBallLocations() {
        return rawPositions;
        //this just returns the collected list of points from the vision thread
    }

    public BallLocation getClosestBall(){
        var ballLocations = getBallLocations();
       return ballLocations.stream().max(Comparator.comparing(it->it.getPoint().y)).get();
    }
}