package frc.robot.subsystems;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrainSubsystem extends SubsystemBase {

    private MecanumDrive mecanumDrive;
    private MotorController frontLeftMotor;
    private MotorController frontRightMotor;
    private MotorController backLeftMotor;
    private MotorController backRightMotor;

    public DriveTrainSubsystem() {
        this.frontLeftMotor = new Spark(Constants.DriveTrain.frontLeftMotorChannel);
        this.backLeftMotor = new Spark(Constants.DriveTrain.backLeftMotorChannel);
        this.frontRightMotor = new Spark(Constants.DriveTrain.frontRightMotorChannel);
        this.backRightMotor = new Spark(Constants.DriveTrain.backRightMotorChannel);

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
}
