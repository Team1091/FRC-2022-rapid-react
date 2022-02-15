// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.EscalatorSubsystem;

public class RunEscalatorCommand extends CommandBase {
    private final EscalatorSubsystem escalatorSubsystem;
    private final double speed;

    public RunEscalatorCommand(EscalatorSubsystem escalatorSubsystem, double speed) {
        this.escalatorSubsystem = escalatorSubsystem;
        this.speed = speed;
        addRequirements(escalatorSubsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        escalatorSubsystem.setEscalatorSpeed(speed);
    }

    @Override
    public void end(boolean interrupted) {
        escalatorSubsystem.setEscalatorSpeed(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
