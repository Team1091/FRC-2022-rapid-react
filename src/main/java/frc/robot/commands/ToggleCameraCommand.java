package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.VisionSubsystem;


public class ToggleCameraCommand extends CommandBase {


    private final VisionSubsystem visionSubsystem;

    public ToggleCameraCommand(VisionSubsystem visionSubsystem) {
        this.visionSubsystem = visionSubsystem;
    }

    @Override
    public void execute() {
        visionSubsystem.setForward(!visionSubsystem.getForward());
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}

