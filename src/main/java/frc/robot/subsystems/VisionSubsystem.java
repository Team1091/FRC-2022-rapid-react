package frc.robot.subsystems;

import edu.wpi.first.cscore.VideoCamera;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.vision.BallPosition;
import org.ejml.data.Matrix;

import java.util.ArrayList;
import java.util.List;

public class VisionSubsystem extends SubsystemBase {
    private final int cameraPort = Constants.Vision.cameraPort;
    private VideoCamera videoCamera;
    private Matrix videoMatrix[];
    public VisionSubsystem(VideoCamera videoCamera) {
        this.videoCamera = videoCamera;
    }

    public List<BallPosition> getBallLocation() {
        return new ArrayList<BallPosition>();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}
