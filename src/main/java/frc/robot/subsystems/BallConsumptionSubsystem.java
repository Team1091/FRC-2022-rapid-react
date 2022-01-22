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
import frc.robot.Constants;

public class BallConsumptionSubsystem extends SubsystemBase {

    private final DoubleSolenoid ballIngestionSolenoid;
    private final MotorController inputMotor;
    private int outAndIn;
    //-1 = in, 1 = out, 0 = return to top position
    //have it automatically pull up when no button is pressed

    public BallConsumptionSubsystem() {
        this.ballIngestionSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.Pneumatics.grabberIn, Constants.Pneumatics.grabberOut);
        this.inputMotor = new CANSparkMax(Constants.Pneumatics.inputMotorChannel, CANSparkMaxLowLevel.MotorType.kBrushless);
    }

    public void setOutAndIn(int outAndIn) {
        this.outAndIn = outAndIn;
    }

    @Override
    public void periodic() {
        if (this.outAndIn == 0) {
            ballIngestionSolenoid.set(DoubleSolenoid.Value.kReverse);
            inputMotor.stopMotor();
        } else if (this.outAndIn == 1) {
            ballIngestionSolenoid.set(DoubleSolenoid.Value.kForward);
            inputMotor.set(Constants.Pneumatics.inputMotorSpeed);
        } else if (this.outAndIn == -1) {
            ballIngestionSolenoid.set(DoubleSolenoid.Value.kForward);
            inputMotor.set(-Constants.Pneumatics.inputMotorSpeed);
        }
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}
