package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.LightSubsystem;

public class LightCommand extends CommandBase {
    private final LightSubsystem lightSubsystem;

    public LightCommand(LightSubsystem lightSubsystem) {
        this.lightSubsystem = lightSubsystem;
    }
}
