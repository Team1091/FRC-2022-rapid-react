package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.BallConsumptionState;
import frc.robot.subsystems.BallConsumptionSubsystem;

public class RetractBallPickUpCommand extends CommandBase {
    private final BallConsumptionSubsystem ballConsumptionSubsystem;

    public RetractBallPickUpCommand(BallConsumptionSubsystem ballConsumptionSubsystem) {
        this.ballConsumptionSubsystem = ballConsumptionSubsystem;
        this.addRequirements(ballConsumptionSubsystem);
    }
    @Override
    public void execute() {
        ballConsumptionSubsystem.setPickUpMode(BallConsumptionState.out);
    }
}