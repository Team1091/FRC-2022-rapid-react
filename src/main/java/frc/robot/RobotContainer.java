// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    //driver station receive what team color we are on
    final VisionLookForBallColor teamColor = DriverStation.getAlliance()== DriverStation.Alliance.Blue ? VisionLookForBallColor.blue:VisionLookForBallColor.red;

    // The robot's subsystems and commands are defined here...
    private final DriveTrainSubsystem driveTrainSubsystem = new DriveTrainSubsystem();
    private final EscalatorSubsystem escalatorSubsystem = new EscalatorSubsystem();
    private final ClimbSubsystem climbSubsystem = new ClimbSubsystem();
    private final VisionSubsystem visionSubsystem = new VisionSubsystem(teamColor);
    private final BallPickupSubsystem ballConsumptionSubsystem = new BallPickupSubsystem();
    private final XboxController controller = new XboxController(Constants.XboxController.port);

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Configure the button bindings
        configureButtonBindings();
        driveTrainSubsystem.setDefaultCommand(
                new MecanumDriveCommand(
                        driveTrainSubsystem,
                        () -> {
                            var input = controller.getLeftX();
                            SmartDashboard.putNumber("strafing", input);
                            return input;
                        },
                        () -> {
                            var input = controller.getLeftY();
                            SmartDashboard.putNumber("forwards", input);
                            return input;
                        },
                        () -> {
                            var input = controller.getRightX();
                            SmartDashboard.putNumber("rotation", input);
                            return input;
                        }
                )
        );
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        //climber out
        var xButton = new JoystickButton(controller, XboxController.Button.kX.value);
        xButton.whenActive(new ClimbCommand(climbSubsystem, ClimberState.out));

        //climber in
        var aButton = new JoystickButton(controller, XboxController.Button.kA.value);
        aButton.whenActive(new ClimbCommand(climbSubsystem, ClimberState.in));

        //escalator down
        var rightBumper = new JoystickButton(controller, XboxController.Button.kRightBumper.value);
        rightBumper.whileHeld(new RunEscalatorCommand(escalatorSubsystem, 1));

        //escalator up
        var leftBumper = new JoystickButton(controller, XboxController.Button.kLeftBumper.value);
        leftBumper.whileHeld(new RunEscalatorCommand(escalatorSubsystem, -1));

        //ball consumption system down and spin rotors in
        var bButton = new JoystickButton(controller, XboxController.Button.kB.value);
        bButton.whenActive(new RetractBallPickUpCommand(ballConsumptionSubsystem));

        //Changes cameras
        var yButton = new JoystickButton(controller, XboxController.Button.kY.value);
        yButton.whileActiveOnce(new ToggleCameraCommand(visionSubsystem));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return new SequentialCommandGroup(
                new ParallelRaceGroup(
                        new RunEscalatorCommand(escalatorSubsystem, 1),
                        new TimerCommand(5)
                ),
                new DistanceDriveCommand(driveTrainSubsystem, 3.0),
                new AutoBallSeekingCommand(driveTrainSubsystem, visionSubsystem),
                new ParallelRaceGroup(
                    new PickUpBallCommand(ballConsumptionSubsystem),
                    new TimerCommand(5)
                )
        );
    }
}