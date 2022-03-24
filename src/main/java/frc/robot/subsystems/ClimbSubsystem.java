// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ClimberState;
import frc.robot.Constants;

public class ClimbSubsystem extends SubsystemBase {

    private final DoubleSolenoid solenoid;
    private ClimberState state = ClimberState.undetermined;

    public ClimbSubsystem() {
        this.solenoid = new DoubleSolenoid(
                PneumaticsModuleType.CTREPCM,
                Constants.Climber.climberIn,
                Constants.Climber.climberOut
        );
        this.state = ClimberState.in;
    }

    public void setState(ClimberState state) {
        this.state = state;

    }

    @Override
    public void periodic() {
        switch (state) {
            case undetermined:
            case in:
                solenoid.set(DoubleSolenoid.Value.kReverse);
                SmartDashboard.putString("Climb", "In");
                break;
            case out:
                solenoid.set(DoubleSolenoid.Value.kForward);
                SmartDashboard.putString("Climb", "Out");
                break;
        }
    }
}
