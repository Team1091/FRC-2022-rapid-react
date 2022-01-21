// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ConveyerSubsystem extends SubsystemBase {

    //Robots Suck Balls

    private MotorController conveyerMotor;
    private double speed;

    /**
     * Creates a new ExampleSubsystem.
     */
    public ConveyerSubsystem() {
        this.conveyerMotor = new Spark(Constants.ConveyerSubs.conveyerMotorChannel);
    }

    public void setConveyerSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        conveyerMotor.set(speed);
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}
