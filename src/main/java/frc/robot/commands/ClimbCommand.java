// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.ClimberState;
import frc.robot.subsystems.ClimbSubsystem;

public class ClimbCommand extends CommandBase {

    private final ClimbSubsystem climbSubsystem;
    private final ClimberState state;

    public ClimbCommand(ClimbSubsystem subsystem, ClimberState state) {
        this.climbSubsystem = subsystem;
        this.state = state;
        addRequirements(subsystem);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        climbSubsystem.setState(state);
    }

    // Called when the command is initially scheduled.
//    @Override
//    public void initialize() {
    //   }


    // Called once the command ends or is interrupted.
//    @Override
//    public void end(boolean interrupted) {
//        climbSubsystem.setState(ClimberState.in);
//    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return true;
    }
}
