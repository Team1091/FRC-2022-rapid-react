// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BallConsumptionState;
import frc.robot.Constants;

public class BallConsumptionSubsystem extends SubsystemBase {

//    private final DoubleSolenoid ballIngestionSolenoid;
//    private final MotorController inputMotor;

    public BallConsumptionSubsystem() {
//        this.ballIngestionSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,
//                Constants.Pneumatics.grabberIn, Constants.Pneumatics.grabberOut);
//        this.inputMotor = new CANSparkMax(Constants.Pneumatics.inputMotorChannel,
//                CANSparkMaxLowLevel.MotorType.kBrushless);
    }

    public void setPickUpMode(BallConsumptionState ballConsumptionState){
        switch (ballConsumptionState) {
            case undetermined:
//                ballIngestionSolenoid.set(DoubleSolenoid.Value.kReverse);
//                inputMotor.stopMotor();
                break;
            case out:
//                ballIngestionSolenoid.set(DoubleSolenoid.Value.kForward);
//                inputMotor.set(Constants.Pneumatics.inputMotorSpeed);
                break;
            case in:
//                ballIngestionSolenoid.set(DoubleSolenoid.Value.kForward);
//                inputMotor.set(-Constants.Pneumatics.inputMotorSpeed);
                break;
        }
    }
}
