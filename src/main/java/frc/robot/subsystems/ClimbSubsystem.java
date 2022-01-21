// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimbSubsystem extends SubsystemBase {

  private final DoubleSolenoid solenoid;
  private int pushAndShove;
              //-1 = retract, 1 = out, 0 = nothing

  public ClimbSubsystem(DoubleSolenoid solenoid) {
    this.solenoid = solenoid;
  }

  public void setPushAndShove(int pushAndShove) {
    this.pushAndShove = pushAndShove;
  }

  @Override
  public void periodic() {
    if (this.pushAndShove == 0) {
      solenoid.set(DoubleSolenoid.Value.kOff);
    } else if (this.pushAndShove == 1) {
      solenoid.set(DoubleSolenoid.Value.kForward);
    } else if (this.pushAndShove == -1){
      solenoid.set(DoubleSolenoid.Value.kReverse);
    }
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
