package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class VacuumSubsystem extends SubsystemBase
{
    private MotorController VacuumMotor;

    public VacuumSubsystem()
    {
        VacuumMotor = new Victor(0);
    }

    public void vacuumOn (int vacuumSpeed){
        double motorSpeed = vacuumSpeed/10;
        VacuumMotor.set(motorSpeed);
    }

    public void vacuumOff(){
        VacuumMotor.stopMotor();
    }

    public boolean isVacuumOn(){
        return VacuumMotor.get()!=0;
    }
}
