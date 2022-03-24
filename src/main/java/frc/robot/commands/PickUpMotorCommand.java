// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PickUpMotorSubsystem;

public class PickUpMotorCommand extends CommandBase {
    private final PickUpMotorSubsystem pickUpMotorSubSystem;
    private final double speed;

    public PickUpMotorCommand(PickUpMotorSubsystem pickUpMotorSubSystem, double speed) {
        this.pickUpMotorSubSystem = pickUpMotorSubSystem;
        this.speed = speed;
        addRequirements(pickUpMotorSubSystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        pickUpMotorSubSystem.setPickUpMotorSpeed(speed);
    }

    @Override
    public void end(boolean interrupted) {
        pickUpMotorSubSystem.setPickUpMotorSpeed(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
