package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.LightSubsystem;

public class LightCommand extends CommandBase {
    private final LightSubsystem lightSubsystem;

    public LightCommand(LightSubsystem lightSubsystem) {
        this.lightSubsystem = lightSubsystem;
    }


    @Override
    public void initialize() {
        if(DriverStation.getAlliance() == DriverStation.Alliance.Blue){
            this.lightSubsystem.setLights(LightSubsystem.LightColors.BLUE);
        }

        if(DriverStation.getAlliance() == DriverStation.Alliance.Red){
            this.lightSubsystem.setLights(LightSubsystem.LightColors.RED);
        }
    }

//    @Override
//    public void execute() {
//
//    }

    @Override
    public void end(boolean interrupted) {
        this.lightSubsystem.setLights(LightSubsystem.LightColors.OFF);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
