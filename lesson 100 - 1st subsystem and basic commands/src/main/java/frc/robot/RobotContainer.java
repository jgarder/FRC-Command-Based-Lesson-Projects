// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.cmdSetPeriodicPrint;
import frc.robot.subsystems.MyFirstSubsystem;

public class RobotContainer {


  /* Setting up bindings for necessary control of the swerve drive platform */
  private final CommandXboxController joystick = new CommandXboxController(0); // My joystick

  private final MyFirstSubsystem objectOfMyFirstClass = new MyFirstSubsystem();


  

  /* Path follower */
  private Command runAuto = new InstantCommand();//empty command returned.

  
  //THIS is our constructor method when our robot container is created/instantiated! it runs once.
  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    //////////////////////////////////////////All Other Button setup///////////////////////////////////////////////////////
    //this will bind the A button a to disable the periodic print.   
    //this will call a method that returns a command that it can schedule! Please observe the difference to the B button!
    //joystick.a().whileTrue();
    
    //this will bind the B button a to disable the periodic print. 
    //see how we can insert any code we want into the instant command? we are just wrapping our code with something the scheduler can run!
    //joystick.b().whileTrue(new InstantCommand(()->{objectOfMyFirstClass.cmd_setPrintPeriodic(false)})); 
    
    //when pressing x it will run an instant command to set the print to true. when the x button goes false it will set the print to false!
    joystick.x().onTrue(objectOfMyFirstClass.cmd_setPrintPeriodic(true));//.onFalse(objectOfMyFirstClass.cmd_setPrintPeriodic(false));
     
    //when pressing y is true this will Run and instant command using the factory Commands.runonce();
    // when Y is UNPRESSED it will automatically set the print to false aswell.
    //NOTICE HOW .onFalse is on a seperate line! since there is no ; then the statement keeps going!
    joystick.y().onTrue(Commands.runOnce(()->{objectOfMyFirstClass.setPrintPeriodic(true);}, objectOfMyFirstClass));
    //.onFalse(new cmdSetPeriodicPrint(objectOfMyFirstClass, false));
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //joystick.pov(0).whileTrue();
  }



  public Command getAutonomousCommand() {
    /* First put the drivetrain into auto run mode, then run the auto */
    return runAuto;
  }
}
