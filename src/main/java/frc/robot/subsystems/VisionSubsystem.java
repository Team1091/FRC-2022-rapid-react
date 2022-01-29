package frc.robot.subsystems;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.VideoCamera;
import edu.wpi.first.cscore.VideoSink;
import edu.wpi.first.vision.VisionPipeline;
import edu.wpi.first.vision.VisionThread;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import frc.robot.VisionLookForBallColor;
import frc.robot.vision.BallLocation;
import frc.robot.vision.FindBallsGripPipeline;
import frc.robot.vision.FindBlueBallsGripPipeline;
import frc.robot.vision.FindRedBallsGripPipeline;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class VisionSubsystem extends SubsystemBase {
    private List<BallLocation> rawPositions;
    private long lastImageTaken;
    private VideoSink sink;
    private final int checkIfUpdatingAfterMillis = 3000;

    public VisionSubsystem(VisionLookForBallColor ballColor) {
        VideoCamera camera = CameraServer.startAutomaticCapture(Constants.Vision.cameraPort);
        sink = CameraServer.getServer();
        sink.setSource(camera);

        camera.setResolution(Constants.Vision.resizeImageWidth, Constants.Vision.resizeImageHeight);
        FindBallsGripPipeline findBallsGripPipeline = getFindBallsGripPipeline(ballColor);

        VisionThread visionThread = new VisionThread(camera, findBallsGripPipeline, pipeline -> {
            if (!pipeline.findBlobsOutput().empty()) {
                rawPositions = pipeline.findBlobsOutput().toList().stream()
                        .map(it -> new BallLocation(it.pt))
                        .collect(Collectors.toList());
                        lastImageTaken = System.currentTimeMillis();
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

    public boolean isImageNotUpdating(){
        return System.currentTimeMillis()-lastImageTaken> checkIfUpdatingAfterMillis;
    }

    private FindBallsGripPipeline getFindBallsGripPipeline(VisionLookForBallColor ballColor) {
        FindBallsGripPipeline findBallsGripPipeline = null;
        if(ballColor == VisionLookForBallColor.blue){
            findBallsGripPipeline = new FindBlueBallsGripPipeline();
        } else {
            findBallsGripPipeline = new FindRedBallsGripPipeline();
        }
        return findBallsGripPipeline;
    }
}