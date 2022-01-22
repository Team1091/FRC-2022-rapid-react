// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbSubsystem;

/**
 * An example command that uses an example subsystem.
 */
public class ClimbCommand extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final ClimbSubsystem climbSubsystem;
    private final int inAndOutPneumatic;


    public ClimbCommand(ClimbSubsystem subsystem, int inAndOutPneumatic) {
        this.climbSubsystem = subsystem;
        this.inAndOutPneumatic = inAndOutPneumatic;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(subsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        climbSubsystem.setPushAndShove(inAndOutPneumatic);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        climbSubsystem.setPushAndShove(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
