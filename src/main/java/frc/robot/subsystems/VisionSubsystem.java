package frc.robot.subsystems;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.VideoCamera;
import edu.wpi.first.cscore.VideoSink;
import edu.wpi.first.vision.VisionPipeline;
import edu.wpi.first.vision.VisionThread;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
    private List<BallLocation> rawPositions = new ArrayList<>();
    private long lastImageTaken;
    private VideoSink sink;
    private final int checkIfUpdatingAfterMillis = 3000;
    private final VideoCamera frontcam;
    private final VideoCamera backcam;
    private boolean isForward = true;

    public VisionSubsystem(VisionLookForBallColor ballColor) {
        frontcam = CameraServer.startAutomaticCapture(Constants.Vision.frontCameraPort);
        backcam = CameraServer.startAutomaticCapture(Constants.Vision.backCameraPort);
        sink = CameraServer.getServer();
        sink.setSource(backcam);

        frontcam.setResolution(Constants.Vision.resizeImageWidth, Constants.Vision.resizeImageHeight);
        FindBallsGripPipeline findBallsGripPipeline = getFindBallsGripPipeline(ballColor);

        lastImageTaken = System.currentTimeMillis();

        VisionThread visionThread = new VisionThread(frontcam, findBallsGripPipeline, pipeline -> {
            SmartDashboard.putNumber("testVisionThread", System.currentTimeMillis());
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

    public boolean getForward() {
        return isForward;
    }

    public void setForward(boolean isForward) {
        this.isForward = isForward;
        if (isForward) {
            sink.setSource(frontcam);
        } else sink.setSource(backcam);
    }

    public BallLocation getClosestBall() {
        var ballLocations = getBallLocations();
        var closestBall = ballLocations.stream().max(Comparator.comparing(it -> it.getPoint().y));
        return closestBall.orElse(null);
    }

    public boolean isImageNotUpdating() {
        SmartDashboard.putNumber("lastImageTaken", lastImageTaken);
        return System.currentTimeMillis() - lastImageTaken > checkIfUpdatingAfterMillis;
    }

    private FindBallsGripPipeline getFindBallsGripPipeline(VisionLookForBallColor ballColor) {
        FindBallsGripPipeline findBallsGripPipeline = null;
        if (ballColor == VisionLookForBallColor.blue) {
            findBallsGripPipeline = new FindBlueBallsGripPipeline();
        } else {
            findBallsGripPipeline = new FindRedBallsGripPipeline();
        }
        return findBallsGripPipeline;
    }
}