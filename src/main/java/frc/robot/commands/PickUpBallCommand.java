package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.BallPickupState;
import frc.robot.subsystems.BallPickupSubsystem;

public class PickUpBallCommand extends CommandBase {
    private final BallPickupSubsystem ballConsumptionSubsystem;

    public PickUpBallCommand(BallPickupSubsystem ballConsumptionSubsystem) {
        this.ballConsumptionSubsystem = ballConsumptionSubsystem;
    }
    @Override
    public void execute() {
        ballConsumptionSubsystem.setPickUpMode(BallPickupState.out);
    }

    @Override
    public void end(boolean interrupted){
        ballConsumptionSubsystem.setPickUpMode(BallPickupState.in);
    }
}

