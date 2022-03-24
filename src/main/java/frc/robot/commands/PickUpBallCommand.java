package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.BallPickupState;
import frc.robot.subsystems.BallPickupSubsystem;

public class PickUpBallCommand extends CommandBase {
    private final BallPickupSubsystem ballConsumptionSubsystem;
    private BallPickupState togglePickUp; // 0 means undetermined, 1 in, -1 out

    public PickUpBallCommand(BallPickupSubsystem ballConsumptionSubsystem, BallPickupState togglePickUp) {
        this.ballConsumptionSubsystem = ballConsumptionSubsystem;
        this.togglePickUp = togglePickUp;
        addRequirements(ballConsumptionSubsystem);
    }
    @Override
    public void execute() {
        ballConsumptionSubsystem.setPickUpMode(togglePickUp);
    }

    @Override
    public void end(boolean interrupted){

    }
}

