// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
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
    final VisionLookForBallColor teamColor = DriverStation.getAlliance() == DriverStation.Alliance.Blue ? VisionLookForBallColor.blue : VisionLookForBallColor.red;

    // The robot's subsystems and commands are defined here...
    private final DriveTrainSubsystem driveTrainSubsystem = new DriveTrainSubsystem();
    private final EscalatorSubsystem escalatorSubsystem = new EscalatorSubsystem();
    private final ClimbSubsystem climbSubsystem = new ClimbSubsystem();
    private final VisionSubsystem visionSubsystem = new VisionSubsystem(teamColor);
    private final BallPickupSubsystem ballPickupSubsystem = new BallPickupSubsystem();
    private final LightSubsystem lightSubsystem = new LightSubsystem();
    private final XboxController controller = new XboxController(Constants.XboxController.port);
    private final PickUpMotorSubsystem pickUpMotorSubsystem = new PickUpMotorSubsystem();

    private final SendableChooser<StartingPositions> startPosChooser = new SendableChooser<StartingPositions>();

    private int toggleState = 0;

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
                            var input = -controller.getLeftX(); //put negative here to change polarity
                            SmartDashboard.putNumber("strafing", input);
                            return input;
                        },
                        () -> {
                            var input = controller.getLeftY();
                            SmartDashboard.putNumber("forwards", input);
                            return input;
                        },
                        () -> {
                            var input = -controller.getRightX();
                            SmartDashboard.putNumber("rotation", input);
                            return input;
                        }
                )
        );

        for(StartingPositions p: StartingPositions.values()) {
            startPosChooser.addOption(p.name(), p);
        }
        startPosChooser.setDefaultOption(StartingPositions.Straight.name(), StartingPositions.Straight);
        SmartDashboard.putData(startPosChooser);
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        //climber out
        var startButt = new JoystickButton(controller, XboxController.Button.kStart.value);
        startButt.whenActive(new ClimbCommand(climbSubsystem, ClimberState.out));

        //climber in
        var backButton = new JoystickButton(controller, XboxController.Button.kBack.value);
        backButton.whenActive(new ClimbCommand(climbSubsystem, ClimberState.in));

        //escalator up
        var rightBumper = new JoystickButton(controller, XboxController.Button.kRightBumper.value);
        rightBumper.whileHeld(new RunEscalatorCommand(escalatorSubsystem, 1));

        //escalator down
        var aButt = new JoystickButton(controller, XboxController.Button.kA.value);
        aButt.whileHeld(new ParallelRaceGroup(
                new RunEscalatorCommand(escalatorSubsystem, -1),
                new LightCommand(lightSubsystem)
        ));

        //ball consumption system down
        var bButton = new JoystickButton(controller, XboxController.Button.kB.value);
        bButton.whenActive(new PickUpBallCommand(ballPickupSubsystem, BallPickupState.out));

        //ball consumption system up
        var yButton = new JoystickButton(controller, XboxController.Button.kY.value);
        yButton.whenActive(new PickUpBallCommand(ballPickupSubsystem, BallPickupState.in));

        var xButton = new JoystickButton(controller, XboxController.Button.kX.value);
        xButton.whileHeld(new PickUpMotorCommand(pickUpMotorSubsystem, -1.0));


        // bButton.whenActive(new LightCommand(lightSubsystem));

        //Changes cameras
        //var yButton = new JoystickButton(controller, XboxController.Button.kY.value);
        //yButton.whileActiveOnce(new ToggleCameraCommand(visionSubsystem));

        //pick up ball --> drop picker upper and run pick up motor
        var leftBumper = new JoystickButton(controller, XboxController.Button.kLeftBumper.value);
        //While holding A, it will keep it down but once it gets released it will go back up
        leftBumper.whileHeld(new PickUpMotorCommand(pickUpMotorSubsystem, Constants.BallPickup.inputMotorSpeed));
//
        //lights are commented because not important, change later if we want to
//        var backButton = new JoystickButton(controller, XboxController.Button.kBack.value);
//        backButton.whileHeld(new LightCommand(lightSubsystem));
    }


    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // get smart dashboard dropdown value,
        StartingPositions startPos = startPosChooser.getSelected();
        SmartDashboard.putString("Current Auto Start Config",startPos.name());
        Command customCommand = new DistanceDriveCommand(driveTrainSubsystem, -25.0);
        if (startPos == StartingPositions.Straight) {
            customCommand = new SequentialCommandGroup(
                    new ParallelRaceGroup(
                            new RunEscalatorCommand(escalatorSubsystem, 1),
                            new LightCommand(lightSubsystem),
                            new TimerCommand(1)
                    ),
                    new PickUpBallCommand(ballPickupSubsystem, BallPickupState.out),
                    new DistanceDriveCommand(driveTrainSubsystem,-25.0)
            );
        } else if (startPos == StartingPositions.A) {
            customCommand = new SequentialCommandGroup(
                    new ParallelRaceGroup(
                            new RunEscalatorCommand(escalatorSubsystem, 1),
                            new LightCommand(lightSubsystem),
                            new TimerCommand(1)
                    ),
                    new PickUpBallCommand(ballPickupSubsystem, BallPickupState.out),
                    new DistanceDriveCommand(driveTrainSubsystem, -25.0),
                    new ParallelRaceGroup(
                            new TimerCommand(0.25),
                            new TurnCommand(driveTrainSubsystem, true, 0.6)//turn slightly
                    ),
                    new DistanceDriveCommand(driveTrainSubsystem, -6.5),
                    new ParallelRaceGroup(
                            new PickUpMotorCommand(pickUpMotorSubsystem, 1.0),
                            //new RunEscalatorCommand(escalatorSubsystem, 1.0),
                            new TimerCommand(2.2)
                    ),
                    //new TimerCommand(0.2),
                    new DistanceDriveCommand(driveTrainSubsystem,6.5),
                    new ParallelRaceGroup( //turn back
                            new TimerCommand(0.25),
                            new TurnCommand(driveTrainSubsystem, false, 0.6)
                    ),
                    //new TurnOriginalCommand(driveTrainSubsystem), //if no work then switch to turn
                    new DistanceDriveCommand(driveTrainSubsystem,25.0),
                    new TimerCommand(0.8),
                    new DistanceDriveCommand(driveTrainSubsystem, 1.0),
                    new ParallelRaceGroup(
                            //new RunEscalatorCommand(escalatorSubsystem,-0.2),
                            new TimerCommand(0.3)
                    ),
                    new ParallelRaceGroup(
                        new RunEscalatorCommand(escalatorSubsystem,1.0),
                        new TimerCommand(1.3)
                    )
            );
        } else if (startPos == StartingPositions.B) {
            customCommand = new SequentialCommandGroup(
                    new ParallelRaceGroup(
                            new RunEscalatorCommand(escalatorSubsystem, 1),
                            new LightCommand(lightSubsystem),
                            new TimerCommand(1)
                    ),
                    new PickUpBallCommand(ballPickupSubsystem, BallPickupState.out),
                    new DistanceDriveCommand(driveTrainSubsystem, -25.0),
                    new ParallelRaceGroup(
                        new TimerCommand(0.25),
                        new TurnCommand(driveTrainSubsystem, false, 0.54)//turn slightly right
                    ),
                    new DistanceDriveCommand(driveTrainSubsystem, -6.5),
                    new ParallelRaceGroup(
                            new PickUpMotorCommand(pickUpMotorSubsystem, 1.0),
                            //new RunEscalatorCommand(escalatorSubsystem, 1.0),
                            new TimerCommand(2.2)
                    ),
                    //new TimerCommand(0.2),
                    new DistanceDriveCommand(driveTrainSubsystem, 6.5),
                   // new TurnOriginalCommand(driveTrainSubsystem), //if no work then switch to manual turn
                    new ParallelRaceGroup(
                            new TimerCommand(0.25),
                            new TurnCommand(driveTrainSubsystem,true,0.54)
                    ),
                    new DistanceDriveCommand(driveTrainSubsystem, 24.0),
                    new TimerCommand(0.8),
                    new DistanceDriveCommand(driveTrainSubsystem, 1.0),
                    new ParallelRaceGroup(
                            //new RunEscalatorCommand(escalatorSubsystem,-0.2),
                            new TimerCommand(0.3)
                    ),
                    new ParallelRaceGroup(
                            new RunEscalatorCommand(escalatorSubsystem,1.0),
                            new TimerCommand(1.3)
                    )
            );
        } else if (startPos == StartingPositions.None){
            customCommand = new ParallelRaceGroup(
                    new RunEscalatorCommand(escalatorSubsystem, 1),
                    new LightCommand(lightSubsystem),
                    new TimerCommand(1)
            );
            new PickUpBallCommand(ballPickupSubsystem, BallPickupState.out);

        } else if (startPos == StartingPositions.Move) {
            customCommand = new DistanceDriveCommand(driveTrainSubsystem, -25.0);
        }

        return new SequentialCommandGroup(

                customCommand // --> is replacement for auto ball seeking

                //new AutoBallSeekingCommand(driveTrainSubsystem, visionSubsystem, ballPickupSubsystem),
//                new ParallelRaceGroup(
//                        new PickUpMotorCommand(pickUpMotorSubsystem, 1.0),
//                        new RunEscalatorCommand(escalatorSubsystem, 1.0),
//                        new TimerCommand(2.4)
//                ),
//                new TurnOriginalCommand(driveTrainSubsystem),
//                new DistanceDriveCommand(driveTrainSubsystem, 50.0)//,
//                new ParallelRaceGroup(
//                        new RunEscalatorCommand(escalatorSubsystem, 1.0),
//                        new TimerCommand(2)
//                )
        );
    }
}