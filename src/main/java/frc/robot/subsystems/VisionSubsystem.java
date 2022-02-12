package frc.robot.subsystems;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.VideoCamera;
import edu.wpi.first.cscore.VideoSink;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.vision.VisionThread;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import frc.robot.VisionLookForBallColor;
import frc.robot.vision.BallLocation;
import frc.robot.vision.FindBallsGripPipeline;
import frc.robot.vision.FindColorBallsGripPipeline;
import org.opencv.core.Point;

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
    NetworkTableEntry seenEntry;
    NetworkTableEntry centerXEntry;
    NetworkTableEntry centerYEntry;
    NetworkTableEntry distanceEntry;

    public VisionSubsystem(VisionLookForBallColor ballColor) {
        frontcam = CameraServer.startAutomaticCapture(Constants.Vision.frontCameraPort);
        backcam = CameraServer.startAutomaticCapture(Constants.Vision.backCameraPort);
        sink = CameraServer.getServer();
        sink.setSource(backcam);

        frontcam.setResolution(Constants.Vision.resizeImageWidth, Constants.Vision.resizeImageHeight);
       // FindBallsGripPipeline findBallsGripPipeline = getFindBallsGripPipeline(ballColor);

        lastImageTaken = System.currentTimeMillis();

//        VisionThread visionThread = new VisionThread(frontcam, findBallsGripPipeline, pipeline -> {
//            SmartDashboard.putNumber("testVisionThread", System.currentTimeMillis());
//            if (!pipeline.findBlobsOutput().empty()) {
//                rawPositions = pipeline.findBlobsOutput().toList().stream()
//                        .map(it -> new BallLocation(it.pt))
//                        .collect(Collectors.toList());
//
//                lastImageTaken = System.currentTimeMillis();
//                //so basically the above converts the findBlobsOutput to a list with a bunch of
//                //points and then it is collected into a new list at the end
//            } else {
//                rawPositions = new ArrayList<>();
//            }
//        });
//        visionThread.start();
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable table = inst.getTable("datatable");
        seenEntry = table.getEntry("seen");
        centerXEntry = table.getEntry("centerX");
        centerYEntry = table.getEntry("centerY");
        distanceEntry = table.getEntry("distance");
    }
    @Override
    public void periodic(){
        boolean seen = seenEntry.getBoolean(false);
        double centerX = centerXEntry.getDouble(0);
        double centerY = centerYEntry.getDouble(0);
        double distance = distanceEntry.getDouble(0);

        if(seen){
            rawPositions = List.of(new BallLocation(new Point(centerX, centerY)));
        }else{
            rawPositions = List.of();
        }
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
            double[] hsvThresholdHue = {92, 141};
            double[] hsvThresholdSaturation = {80, 255};
            double[] hsvThresholdValue = {122, 255};

            findBallsGripPipeline = new FindColorBallsGripPipeline(hsvThresholdHue, hsvThresholdSaturation, hsvThresholdValue);
        } else {
            double[] hsvThresholdHue = {67.98561151079139, 125.13080444735127};
            double[] hsvThresholdSaturation = {98.60611510791368, 244.26767676767676};
            double[] hsvThresholdValue = {119.24460431654674, 255.0};

            findBallsGripPipeline = new FindColorBallsGripPipeline(hsvThresholdHue, hsvThresholdSaturation, hsvThresholdValue);
        }
        return findBallsGripPipeline;
    }
}