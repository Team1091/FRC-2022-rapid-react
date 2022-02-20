// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BallPickupState;
import frc.robot.Constants;

public class BallPickupSubsystem extends SubsystemBase {

    private final DoubleSolenoid ballPickupSolenoid;
    private final MotorController inputMotor;
    private BallPickupState ballPickupState;

    public BallPickupSubsystem() {
        this.ballPickupSolenoid = new DoubleSolenoid(
                PneumaticsModuleType.CTREPCM,
                Constants.BallPickup.grabberIn,
                Constants.BallPickup.grabberOut);
        this.inputMotor = new Victor(Constants.BallPickup.inputMotorChannel);
        this.ballPickupState = BallPickupState.in;
    }

    @Override
    public void periodic() {
        switch (ballPickupState) {
            case undetermined:
            case in:
                SmartDashboard.putString("ballPickupSoleniod", "In");
                ballPickupSolenoid.set(DoubleSolenoid.Value.kReverse);
                inputMotor.stopMotor();
                break;
            case out:
                SmartDashboard.putString("ballPickupSoleniod", "Out");
                ballPickupSolenoid.set(DoubleSolenoid.Value.kForward);
                inputMotor.set(Constants.BallPickup.inputMotorSpeed);
                break;
        }
    }

    public void setPickUpMode(BallPickupState ballPickupState){
        this.ballPickupState = ballPickupState;
    }
}
