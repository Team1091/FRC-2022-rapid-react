package frc.robot.vision;

import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoCamera;
import frc.robot.Constants;

public class BallPosition {
    private int x;
    private int y;

    public BallPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX () {
        return x;
    }

    public int getY (){
        return y;
    }
}
