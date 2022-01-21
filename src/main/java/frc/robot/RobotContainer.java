// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ConveyerCommand;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.ClimbCommand;
import frc.robot.commands.MacanumDriveCommand;
import frc.robot.commands.TimerCommand;
import frc.robot.subsystems.ConveyerSubsystem;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.ExampleSubsystem;

import java.time.Duration;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
    private final DriveTrainSubsystem driveTrainSubsystem = new DriveTrainSubsystem();
    private final ConveyerSubsystem conveyerSubsystem = new ConveyerSubsystem();
    private final ClimbSubsystem climbSubsystem = new ClimbSubsystem(new DoubleSolenoid(PneumaticsModuleType.CTREPCM,Constants.Pnemautics.pneumaticIn,Constants.Pnemautics.pneumaticOut));
                                                                                //module type may not be correct
    private final XboxController controller = new XboxController(Constants.XboxController.port);


    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Configure the button bindings
        configureButtonBindings();

        driveTrainSubsystem.setDefaultCommand(
                new MacanumDriveCommand(
                        driveTrainSubsystem,
                        () -> (controller.getLeftY()),
                        () -> (controller.getLeftX()),
                        () -> controller.getRightX()
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
        //up pneumatic
        var xButton = new JoystickButton(controller,XboxController.Button.kX.value);
        xButton.whenActive(new ClimbCommand(climbSubsystem, 1) );

        //down pneumatic
        var aButton = new JoystickButton(controller,XboxController.Button.kA.value);
        aButton.whenActive(new ClimbCommand(climbSubsystem, -1) );

        //forward conveyor
        var rightBumper = new JoystickButton(controller, XboxController.Button.kRightBumper.value);
        rightBumper.whenActive(new ConveyerCommand(conveyerSubsystem, 1));

        //reverse conveyor
        var leftBumper = new JoystickButton(controller, XboxController.Button.kLeftBumper.value);
        leftBumper.whenActive(new ConveyerCommand(conveyerSubsystem, -1));

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
                        new ConveyerCommand(conveyerSubsystem, 1),
                        new TimerCommand(Duration.ofSeconds(5))
                ),
                new MacanumDriveCommand(driveTrainSubsystem, () -> 0.0, () -> 1.0, () -> 0.0)

        );
    }

//    public MacanumDriveCommand getMecanumDriveCommand() {
//        return new MacanumDriveCommand(driveTrainSubsystem,);
//    }
}
