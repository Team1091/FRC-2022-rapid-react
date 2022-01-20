package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase
{
    private MotorController armRotateMotor;

    private MotorController armLiftMotor;

    public ArmSubsystem()
    {
        armRotateMotor = new Victor(3);
        armLiftMotor = new Victor(4);
    }

    public void rotateArmRight ()
    {
        armRotateMotor.set(.2);
        wait(2000);
        armRotateMotor.stopMotor();
    }

    public void rotateArmLeft ()
    {
        armRotateMotor.set(-.2);
        wait(2000);
        armRotateMotor.stopMotor();
    }

    public void liftArmUp ()
    {
        armLiftMotor.set(.2);
        wait(2000);
        armLiftMotor.stopMotor();
    }

    public void liftArmDown ()
    {
        armLiftMotor.set(-.2);
        wait(2000);
        armLiftMotor.stopMotor();
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
