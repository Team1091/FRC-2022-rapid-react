// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class EscalatorSubsystem extends SubsystemBase {

    private final MotorController escalatorMotor;
    private double speed;

    public EscalatorSubsystem() {
        this.escalatorMotor = new Victor(Constants.Escalator.motorChannel);
    }

    public void setEscalatorSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public void periodic() {
        escalatorMotor.set(speed);
    }
}
