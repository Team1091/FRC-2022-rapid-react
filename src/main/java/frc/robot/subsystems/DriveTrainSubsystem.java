package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrainSubsystem extends SubsystemBase {

    private final MecanumDrive mecanumDrive;
    private final CANSparkMax frontLeftMotor;
    private final CANSparkMax frontRightMotor;
    private final RelativeEncoder leftEncoder;
    private final RelativeEncoder rightEncoder;

    public DriveTrainSubsystem() {
        this.frontLeftMotor = new CANSparkMax(Constants.DriveTrain.frontLeftMotorChannel, CANSparkMaxLowLevel.MotorType.kBrushless);
        CANSparkMax backLeftMotor = new CANSparkMax(Constants.DriveTrain.backLeftMotorChannel, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.frontRightMotor = new CANSparkMax(Constants.DriveTrain.frontRightMotorChannel, CANSparkMaxLowLevel.MotorType.kBrushless);
        CANSparkMax backRightMotor = new CANSparkMax(Constants.DriveTrain.backRightMotorChannel, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftEncoder = frontLeftMotor.getEncoder();
        rightEncoder = frontRightMotor.getEncoder();

        mecanumDrive = new MecanumDrive(
                this.frontLeftMotor,
                backLeftMotor,
                this.frontRightMotor,
                backRightMotor);
    }

    @Override
    public void periodic() {

    }

    public void mecanumDrive(double strafeVelocity, double forwardBackwardVelocity, double rotationVelocity) {
        mecanumDrive.driveCartesian(forwardBackwardVelocity, strafeVelocity, rotationVelocity);
    }

    public double getLeftEncoder(){
        return leftEncoder.getPosition();
    }

    public double getRightEncoder(){
        return rightEncoder.getPosition();
    }
}
