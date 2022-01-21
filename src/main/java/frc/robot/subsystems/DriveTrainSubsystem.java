package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrainSubsystem extends SubsystemBase {

    private MecanumDrive mecanumDrive;
    private CANSparkMax frontLeftMotor;
    private CANSparkMax frontRightMotor;
    private CANSparkMax backLeftMotor;
    private CANSparkMax backRightMotor;
    private RelativeEncoder leftEncoder = frontLeftMotor.getEncoder();
    private RelativeEncoder rightEncoder = frontRightMotor.getEncoder();

    public DriveTrainSubsystem() {
        this.frontLeftMotor = new CANSparkMax(Constants.DriveTrain.frontLeftMotorChannel, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.backLeftMotor = new CANSparkMax(Constants.DriveTrain.backLeftMotorChannel, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.frontRightMotor = new CANSparkMax(Constants.DriveTrain.frontRightMotorChannel, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.backRightMotor = new CANSparkMax(Constants.DriveTrain.backRightMotorChannel, CANSparkMaxLowLevel.MotorType.kBrushless);

        mecanumDrive = new MecanumDrive(
                this.frontLeftMotor,
                this.backLeftMotor,
                this.frontRightMotor,
                this.backRightMotor);
    }

    @Override
    public void periodic() {

    }

    public void mecanumDrive(double xVelocity, double yVelocity, double rotationVelocity) {
        //Change depending on driver's control preferences


        mecanumDrive.driveCartesian(yVelocity, xVelocity, rotationVelocity);
    }
    public double getLeftEncoder(){
        return leftEncoder.getPosition();
    }

    public double getRightEncoder(){
        return rightEncoder.getPosition();
    }
}
