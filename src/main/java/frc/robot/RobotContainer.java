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
    //driver station recieve what team color we are on
    final VisionLookForBallColor teamColor = DriverStation.getAlliance()== DriverStation.Alliance.Blue ? VisionLookForBallColor.blue:VisionLookForBallColor.red;

    // The robot's subsystems and commands are defined here...
    private final DriveTrainSubsystem driveTrainSubsystem = new DriveTrainSubsystem();
    private final ConveyorSubsystem conveyorSubsystem = new ConveyorSubsystem();
    private final ClimbSubsystem climbSubsystem = new ClimbSubsystem(new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.Pneumatics.pneumaticIn, Constants.Pneumatics.pneumaticOut));
    private final VisionSubsystem visionSubsystem = new VisionSubsystem(teamColor);
    private final BallConsumptionSubsystem ballConsumptionSubsystem = new BallConsumptionSubsystem();
    //module type may not be correct
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
                        controller::getLeftY,
                        controller::getLeftX,
                        controller::getRightX
                )
        );

        ballConsumptionSubsystem.setDefaultCommand(
                new RetractBallPickUpCommand(ballConsumptionSubsystem)
        );
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        //up pneumatic climb
        var xButton = new JoystickButton(controller, XboxController.Button.kX.value);
        xButton.whenActive(new ClimbCommand(climbSubsystem, 1));

        //down pneumatic climb
        var aButton = new JoystickButton(controller, XboxController.Button.kA.value);
        aButton.whenActive(new ClimbCommand(climbSubsystem, -1));

        //forward conveyor
        var rightBumper = new JoystickButton(controller, XboxController.Button.kRightBumper.value);
        rightBumper.whenActive(new ConveyorCommand(conveyorSubsystem, 1));

        //reverse conveyor
        var leftBumper = new JoystickButton(controller, XboxController.Button.kLeftBumper.value);
        leftBumper.whenActive(new ConveyorCommand(conveyorSubsystem, -1));

        //ball consumption system down and spin rotors in
        var bButton = new JoystickButton(controller, XboxController.Button.kB.value);
        bButton.whenActive(new RetractBallPickUpCommand(ballConsumptionSubsystem));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return new SequentialCommandGroup(
                new ParallelRaceGroup(
                        new ConveyorCommand(conveyorSubsystem, 1),
                        new TimerCommand(5)
                ),
                new DistanceDriveCommand(driveTrainSubsystem, 3.0),
                new TurnToBallCommand(visionSubsystem, driveTrainSubsystem)

        );
    }

//    public MecanumDriveCommand getMecanumDriveCommand() {
//        return new MecanumDriveCommand(driveTrainSubsystem,);
//    }
}
