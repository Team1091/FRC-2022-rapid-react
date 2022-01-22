package frc.robot.vision;

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
}
