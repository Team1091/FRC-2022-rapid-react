package frc.robot.vision;

import frc.robot.Constants;
import org.opencv.core.Point;

public class BallLocation {
    private Point point;
    private long imageTakenOn;

    public BallLocation(Point point){
        this.point = point;
        imageTakenOn = System.currentTimeMillis();
    }

    public Point getPoint(){
        return point;
    }

    @Override
    public boolean equals(Object other){
        if (this == other) return true;
        if (!(other instanceof BallLocation)) return false;

        BallLocation it = (BallLocation) other;
        var pointEqual = it.point.equals(this.point);
        var timestampMatch = it.imageTakenOn == this.imageTakenOn;
        return pointEqual && timestampMatch;
    }

    public long getAgeInMilliseconds(){
        return System.currentTimeMillis() - imageTakenOn;
    }

    public boolean canPickUpBall(int tolerance){
        double bottomDeadZone = Constants.Vision.resizeImageHeight*0.1;

        int lowerXBound = Constants.Vision.resizeImageWidth / 2 - tolerance;
        int upperXBound = Constants.Vision.resizeImageWidth / 2 + tolerance;

        boolean isInXRange = this.point.x >= lowerXBound && this.point.x <= upperXBound;

        boolean lowerYBound = this.point.y >= Constants.Vision.resizeImageHeight-bottomDeadZone-tolerance-tolerance;
        boolean upperYBound = this.point.y <= Constants.Vision.resizeImageHeight-bottomDeadZone;

        boolean isInYRange = lowerYBound && upperYBound;

        if (isInXRange && isInYRange){
            return true;
        }
        return  false;
    }
}
