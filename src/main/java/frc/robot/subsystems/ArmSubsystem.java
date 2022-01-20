package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase
{
    private MotorController ArmRotateMotor;

    private MotorController ArmLiftMotor;

    public ArmSubsystem()
    {
        ArmRotateMotor = new Victor(3);
        ArmLiftMotor = new Victor(4);
    }

    public void rotateArmRight ()
    {
        ArmRotateMotor.set(.2);
        wait(2000);
        ArmRotateMotor.stopMotor();
    }

    public void rotateArmLeft ()
    {
        ArmRotateMotor.set(-.2);
        wait(2000);
        ArmRotateMotor.stopMotor();
    }

    public void liftArmUp ()
    {
        ArmLiftMotor.set(.2);
        wait(2000);
        ArmLiftMotor.stopMotor();
    }

    public void liftArmDown ()
    {
        ArmLiftMotor.set(-.2);
        wait(2000);
        ArmLiftMotor.stopMotor();
    }

    public void wait(int milliseconds)
    {
        var watch = new Timer();
        try {
            watch.wait(milliseconds);
        }catch (Exception e){

        }
    }
}
