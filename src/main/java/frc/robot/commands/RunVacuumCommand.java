package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.VacuumSubsystem;

public class RunVacuumCommand extends CommandBase {
    private VacuumSubsystem vacuum;
    Timer endTimer=new Timer();

    public RunVacuumCommand(VacuumSubsystem Vacuum){
        this.vacuum = Vacuum;
    }

    @Override
    public void execute(){
        vacuum.vacuumOn(8);
    }
    @Override
    public void end(boolean interrupted){
        vacuum.vacuumOff();
    }
    @Override
    public boolean isFinished(){
        return endTimer.hasElapsed(5);
    }
    @Override
    public void initialize(){
        endTimer.start();

    }
}
