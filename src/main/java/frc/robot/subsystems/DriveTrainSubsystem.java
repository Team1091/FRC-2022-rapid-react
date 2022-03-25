package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrainSubsystem extends SubsystemBase {

    private final MecanumDrive mecanumDrive;
    private final RelativeEncoder leftEncoder;
    private final RelativeEncoder rightEncoder;
    double forwardBackwardVelocity, strafeVelocity, rotationVelocity = 0;

    public DriveTrainSubsystem() {
        var frontLeftMotor = new CANSparkMax(Constants.DriveTrain.frontLeftMotorChannel, CANSparkMaxLowLevel.MotorType.kBrushless);
        var backLeftMotor = new CANSparkMax(Constants.DriveTrain.backLeftMotorChannel, CANSparkMaxLowLevel.MotorType.kBrushless);
        var frontRightMotor = new CANSparkMax(Constants.DriveTrain.frontRightMotorChannel, CANSparkMaxLowLevel.MotorType.kBrushless);
        var backRightMotor = new CANSparkMax(Constants.DriveTrain.backRightMotorChannel, CANSparkMaxLowLevel.MotorType.kBrushless);

        frontRightMotor.setInverted(true);
        backRightMotor.setInverted(true);

        leftEncoder = frontLeftMotor.getEncoder();
        rightEncoder = frontRightMotor.getEncoder();

        mecanumDrive = new MecanumDrive(
                frontLeftMotor,
                backLeftMotor,
                frontRightMotor,
                backRightMotor);
    }

    @Override
    public void periodic() {
        mecanumDrive.driveCartesian(
                forwardBackwardVelocity,
                strafeVelocity,
                rotationVelocity);
    }

    public void mecanumDrive(double strafeVelocity, double forwardBackwardVelocity, double rotationVelocity) {
        this.strafeVelocity = strafeVelocity;
        this.forwardBackwardVelocity = forwardBackwardVelocity;
        this.rotationVelocity = rotationVelocity;
    }

    public double getLeftEncoder() {
        return leftEncoder.getPosition();
    }

    public double getRightEncoder() {
        return rightEncoder.getPosition();
    }
}
