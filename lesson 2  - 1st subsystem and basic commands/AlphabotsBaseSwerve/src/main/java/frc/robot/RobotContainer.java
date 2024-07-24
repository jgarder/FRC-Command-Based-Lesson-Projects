// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.commands.cmdSetPeriodicPrint;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.MyFirstSubsystem;

public class RobotContainer {
  private double MaxSpeed = TunerConstants.kSpeedAt12VoltsMps; // kSpeedAt12VoltsMps desired top speed
  private double MaxAngularRate = 1.5 * Math.PI; // 3/4 of a rotation per second max angular velocity

  /* Setting up bindings for necessary control of the swerve drive platform */
  private final CommandXboxController joystick = new CommandXboxController(0); // My joystick
  public final CommandSwerveDrivetrain drivetrain = TunerConstants.DriveTrain; // My drivetrain
  private final MyFirstSubsystem objectOfMyFirstClass = new MyFirstSubsystem();


  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
      .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
      .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // I want field-centric
                                                               // driving in open loop
  private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
  private final SwerveRequest.RobotCentric forwardStraight = new SwerveRequest.RobotCentric().withDriveRequestType(DriveRequestType.OpenLoopVoltage);
  private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

  /* Path follower */
  private Command runAuto = drivetrain.getAutoPath("Tests");

  private final Telemetry logger = new Telemetry(MaxSpeed);

  private void configureBindings() {
    //////////////////////////////////////Drivetrain setup///////////////////////////////////////////////////////////
    //the default command is the command that will run when nothing else is scheduled for that subsytem.
    drivetrain.setDefaultCommand( // Drivetrain will execute this command periodically
        drivetrain.applyRequest(() -> drive.withVelocityX(-joystick.getLeftY() * MaxSpeed) // Drive forward with
                                                                                           // negative Y (forward)
            .withVelocityY(-joystick.getLeftX() * MaxSpeed) // Drive left with negative X (left)
            .withRotationalRate(-joystick.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
        ).ignoringDisable(true));
    //////////////////////////////////////////Drivetrain setup///////////////////////////////////////////////////////
    //////////////////////////////////////////All Other Button setup///////////////////////////////////////////////////////
    //this will bind the A button a to disable the periodic print.   
    //this will call a method that returns a command that it can schedule! Please observe the difference to the B button!
    joystick.a().whileTrue(objectOfMyFirstClass.cmd_setPrintPeriodic(false));
    
    //this will bind the B button a to disable the periodic print. 
    //see how we can insert any code we want into the instant command? we are just wrapping our code with something the scheduler can run!
    joystick.b().whileTrue(new InstantCommand(()->{objectOfMyFirstClass.setPrintPeriodic(false);})); 
    
    //when pressing x it will run an instant command to set the print to true. when the x button goes false it will set the print to false!
    joystick.x().onTrue(objectOfMyFirstClass.cmd_setPrintPeriodic(true)).onFalse(objectOfMyFirstClass.cmd_setPrintPeriodic(false));
     
    //when pressing y is true this will Run and instant command using the factory Commands.runonce();
    // when Y is UNPRESSED it will automatically set the print to false aswell.
    //NOTICE HOW .onFalse is on a seperate line! since there is no ; then the statement keeps going!
    joystick.y().onTrue(Commands.runOnce(()->{objectOfMyFirstClass.setPrintPeriodic(true);}, objectOfMyFirstClass))
    .onFalse(new cmdSetPeriodicPrint(objectOfMyFirstClass, false));
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //joystick.pov(0).whileTrue();


    drivetrain.registerTelemetry(logger::telemeterize);


  }

  public RobotContainer() {
    configureBindings();
  }

  public Command getAutonomousCommand() {
    /* First put the drivetrain into auto run mode, then run the auto */
    return runAuto;
  }
}
