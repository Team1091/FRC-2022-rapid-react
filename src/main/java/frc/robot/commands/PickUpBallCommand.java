package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.BallConsumptionState;
import frc.robot.subsystems.BallConsumptionSubsystem;

public class PickUpBallCommand extends CommandBase {
    private final BallConsumptionSubsystem ballConsumptionSubsystem;

    public PickUpBallCommand(BallConsumptionSubsystem ballConsumptionSubsystem) {
        this.ballConsumptionSubsystem = ballConsumptionSubsystem;
    }
    @Override
    public void execute() {
        ballConsumptionSubsystem.setPickUpMode(BallConsumptionState.in);
    }
}

