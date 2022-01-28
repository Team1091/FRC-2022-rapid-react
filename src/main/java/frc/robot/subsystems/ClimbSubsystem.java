// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimbSubsystem extends SubsystemBase {

//    private final DoubleSolenoid solenoid;
    private int upOrDown;
    //-1 = retract, 1 = out, 0 = nothing

    public ClimbSubsystem() {
//        this.solenoid = new DoubleSolenoid(
//                PneumaticsModuleType.CTREPCM,
//                Constants.Pneumatics.pneumaticIn,
//                Constants.Pneumatics.pneumaticOut
//        );
    }

    public void upOrDown(int upOrDown) {
        this.upOrDown = upOrDown;
    }

    @Override
    public void periodic() {
//        if (this.upOrDown == 0) {
//            solenoid.set(DoubleSolenoid.Value.kOff);
//        } else if (this.upOrDown == 1) {
//            solenoid.set(DoubleSolenoid.Value.kForward);
//        } else if (this.upOrDown == -1) {
//            solenoid.set(DoubleSolenoid.Value.kReverse);
//        }
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}
