package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ACustomDriveTrain extends SubsystemBase {
    //private double forward; //positive means forward drive
    private double right; //positive means right strafe
    private double clock; //positive means clockwise rotation

    //calibrations
    private double FL = 1.0; //front left
    private double FR = 1.0; //front right
    private double BL = 1.0; //back left
    private double BR = 1.0; //back right

    //motors
    private CANSparkMax frontLeftMotor;
    private CANSparkMax backLeftMotor;
    private CANSparkMax frontRightMotor;
    private CANSparkMax backRightMotor;



    public ACustomDriveTrain() {
        frontLeftMotor = new CANSparkMax(Constants.DriveTrain.frontLeftMotorChannel, CANSparkMaxLowLevel.MotorType.kBrushless);
        backLeftMotor = new CANSparkMax(Constants.DriveTrain.backLeftMotorChannel, CANSparkMaxLowLevel.MotorType.kBrushless);
        frontRightMotor = new CANSparkMax(Constants.DriveTrain.frontRightMotorChannel, CANSparkMaxLowLevel.MotorType.kBrushless);
        backRightMotor = new CANSparkMax(Constants.DriveTrain.backRightMotorChannel, CANSparkMaxLowLevel.MotorType.kBrushless);

        //backRightMotor.setInverted(true); //change as neccessary for logic
        //backLeftMotor.setInverted(true);

    }

    @Override
    public void periodic() {

    }

    public void customMecanumDrive(double forward, double right, double clock) {
        if (forward < 0.1) forward = 0; //if its too small, it can be unnecessary nudge
        if (right < 0.1) right = 0;
        if (clock < 0.1) clock = 0;

        if (right == 0 && clock == 0) { //for move forward have equal power
            frontLeftMotor.set((forward-right-clock) * 1.0);
            backLeftMotor.set((-forward-right-clock) * 1.0);
            frontRightMotor.set((forward+right+clock) * 1.0);
            backRightMotor.set((-forward+right+clock) * 1.0);
        } else if(forward + right + clock <= 1.0) {
            frontLeftMotor.set((forward-right-clock) * FL);
            backLeftMotor.set((-forward-right-clock) * BL);
            frontRightMotor.set((forward+right+clock) * FR);
            backRightMotor.set((-forward+right+clock) * BR);
        } else { //we are trying to strafe, turn, and more at the same time...
            double magnitudeSet = 1/(forward + right + clock);

            //lower power setting for all movements
            frontLeftMotor.set((-forward+right+clock) * FL * magnitudeSet);
            backLeftMotor.set((forward+right+clock) * BL * magnitudeSet);
            frontRightMotor.set((forward+right+clock) * FR * magnitudeSet);
            backRightMotor.set((-forward+right+clock) * BR * magnitudeSet);
        }

    }

    public double getLeftEncoder() {
        return (frontLeftMotor.getEncoder().getPosition() + backLeftMotor.getEncoder().getPosition())/2.0;
    }

    public double getRightEncoder() {
        return (frontRightMotor.getEncoder().getPosition() + backRightMotor.getEncoder().getPosition())/2.0;
    }
}
