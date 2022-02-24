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
    private final BallPickupSubsystem ballPickupSubsystem = new BallPickupSubsystem();
    private final LightSubsystem lightSubsystem = new LightSubsystem();
    private final XboxController driverController = new XboxController(Constants.XboxController.driverPort);
    private final XboxController payloadSpecialistController = new XboxController(Constants.XboxController.payloadSpecallistPort);

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
                            var input = driverController.getLeftX();
                            SmartDashboard.putNumber("strafing", input);
                            return input;
                        },
                        () -> {
                            var input = driverController.getLeftY();
                            SmartDashboard.putNumber("forwards", input);
                            return input;
                        },
                        () -> {
                            var input = -driverController.getRightX();
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
        var aButton = new JoystickButton(payloadSpecialistController, XboxController.Button.kA.value);
        var bButton = new JoystickButton(payloadSpecialistController, XboxController.Button.kB.value);
        var xButton = new JoystickButton(payloadSpecialistController, XboxController.Button.kX.value);
        var yButton = new JoystickButton(payloadSpecialistController, XboxController.Button.kY.value);
        var rightBumper = new JoystickButton(payloadSpecialistController, XboxController.Button.kRightBumper.value);
        var leftBumper = new JoystickButton(payloadSpecialistController, XboxController.Button.kLeftBumper.value);
        var backButt = new JoystickButton(payloadSpecialistController, XboxController.Button.kBack.value);



        //climber out
        yButton.whenActive(new ClimbCommand(climbSubsystem, ClimberState.out));
        //climber in
        xButton.whenActive(new ClimbCommand(climbSubsystem, ClimberState.in));

        //escalator half speed
        aButton.whileHeld(new RunEscalatorCommand(escalatorSubsystem, -0.5));

        //escalator full up
        bButton.whileHeld(new RunEscalatorCommand(escalatorSubsystem, -1));

        //escalator full back
        backButt.whileHeld(new RunEscalatorCommand(escalatorSubsystem,1));

        //ball consumption system down and spin rotors in
        rightBumper.whileHeld(new PickUpBallCommand(ballPickupSubsystem));

        // bButton.whenActive(new LightCommand(lightSubsystem));

        //Changes cameras
        //yButton.whileActiveOnce(new ToggleCameraCommand(visionSubsystem));

        //pick up ball --> drop picker upper and run pick up motor
        var startButt = new JoystickButton(payloadSpecialistController, XboxController.Button.kStart.value);
        startButt.whileHeld(new PickUpBallCommand(ballPickupSubsystem));

        var backButton = new JoystickButton(payloadSpecialistController, XboxController.Button.kBack.value);
        backButton.whileHeld(new LightCommand(lightSubsystem));
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
                        new TimerCommand(1)
                ),
                new DistanceDriveCommand(driveTrainSubsystem, -6.0),
                new AutoBallSeekingCommand(driveTrainSubsystem, visionSubsystem),
                new ParallelRaceGroup(
                    new PickUpBallCommand(ballPickupSubsystem),
                    new TimerCommand(5)
                )
        );
    }
}