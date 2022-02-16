package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LightSubsystem extends SubsystemBase {

    private final LightSubsystem lightSubsystem;

    public LightSubsystem(LightSubsystem lightSubsystem) {
        this.lightSubsystem = lightSubsystem;
    }
}
