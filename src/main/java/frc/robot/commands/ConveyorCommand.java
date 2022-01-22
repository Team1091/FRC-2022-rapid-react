// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ConveyorSubsystem;

/**
 * An example command that uses an example subsystem.
 */
public class ConveyorCommand extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final ConveyorSubsystem conveyorSubsystem;
    private final double speed;

    /**
     * Creates a new ExampleCommand.
     *
     * @param conveyorSubsystem The subsystem used by this command.
     */
    public ConveyorCommand(ConveyorSubsystem conveyorSubsystem, double speed) {
        this.conveyorSubsystem = conveyorSubsystem;
        this.speed = speed;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(conveyorSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        conveyorSubsystem.setConveyorSpeed(speed);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        conveyorSubsystem.setConveyorSpeed(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}