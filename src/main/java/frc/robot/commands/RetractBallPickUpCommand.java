package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.BallPickupState;
import frc.robot.subsystems.BallPickupSubsystem;

public class RetractBallPickUpCommand extends CommandBase {
    private final BallPickupSubsystem ballConsumptionSubsystem;

    public RetractBallPickUpCommand(BallPickupSubsystem ballConsumptionSubsystem) {
        this.ballConsumptionSubsystem = ballConsumptionSubsystem;
        this.addRequirements(ballConsumptionSubsystem);
    }
    @Override
    public void execute() {
        ballConsumptionSubsystem.setPickUpMode(BallPickupState.out);
    }

    @Override
    public void end(boolean isInterrupted){
        ballConsumptionSubsystem.setPickUpMode(BallPickupState.in);
    }
}