package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LiftSubsystem extends SubsystemBase {
    private MotorController liftMotor;
    private LiftPosition newPosition;
    private DigitalInput bottomLimitSwitch;
    private DigitalInput topLimitSwitch;

    public LiftSubsystem() {
        liftMotor = new Spark(0);
        newPosition = LiftPosition.Down;
        bottomLimitSwitch = new DigitalInput(0);
        topLimitSwitch = new DigitalInput(1);
    }

    public void setNewPosition(LiftPosition liftPosition) {
        newPosition = liftPosition;
    }

    @Override
    public void periodic() {
        if (newPosition == LiftPosition.Down) {
            if (bottomLimitSwitch.get()) {
                liftMotor.set(0);
            } else {
                liftMotor.set(-1);
            }
        } else {
            if (topLimitSwitch.get()) {
                liftMotor.set(0);
            } else {
                liftMotor.set(1);
            }
        }
    }

    public enum LiftPosition {
        Up,
        Down,
    }
}
