/*
 * FRC #8608 "a dummies guide to programming FRC java"
 * 
 * this guide is just a rewriting of the 100% awesome WPILIB docs! 
 * please just read the zero to robot section and you wont even need a mentor! 
 * 
 * Wpilib references when in trouble :
 *  Command based programming -> https://docs.wpilib.org/en/stable/docs/software/commandbased/index.html
 *  instant command/RunOnce -> https://docs.wpilib.org/en/stable/docs/software/commandbased/commands.html
 *  subsystems -> https://docs.wpilib.org/en/stable/docs/software/commandbased/subsystems.html#subsystems
 * 
 * lesson 2 - 1st subsystem and basic command compositions
 * 
 * you will show a completed lesson folder to your teacher. 
 * -> by branching this in git and updating your branch. 
 * -> or showing them your personal laptop ready to compile/run or simulate your code!
 * -> The Project should always "Build successful" When shown to a mentor unless asking a question during developement!
 * requirements to pass this lesson:
 *
 * Task 0 : Open the Subsystem folder then open MyFirstSubsystem.java and try to observe -> 
 *  -> in the subsystem you will see that when the object is created (at robot container bootup) the subsystem prints some text. find that in your terminal during bootup. struggling? read on.
 *  -> notice every subsystem has a public void periodic()  that also runs every 20 milliseconds. here we can check sensors or update dashboard items to alert the driver
 *  -> this periodic runs some code doesnt it? it only runs when the if doPrintPeriodic is "true".. how do we set it to false?
 *  -> there is methods at the bottom, one returns void and one returns a command. whats the difference? we need to know! 

 * Task 1 (viewed by Mentor): inline an instant command/RunOnce command to disable the periodic printing wehn the B buttons is pressed.
 *  -> this can be complex but we are skipping everything and just running code. to run a delegate we use this piece of code ()->{}
 *  -> code inside codeblocks {} will run when an instant command is called.
 *  -> code is not a command, so using Instantcommand(()->{}) we are just "wrapping" our code in a command so the scheduler will see it as a command.
 *  -> notice you need to wrap the method setPrintPeriodic(boolean setToThis) not the method that returns a command!
 * 
 * Task 2 (viewed by Mentor): Call a command from our first subsystem to set that periodic to false.
 *  -> find the method in the subsystem that we can use to disable the periodic printing! make it run using the A button
 *  -> you will need to pass a parameter of true or false to this method! its expecting a boolean called setToThis
 *  -> Run the command of the subsystem named objectOfMyFirstClass.  use the method and argument/parameter cmd_setPrintPeriodic(false)
 * 
 *  you can view the solutions folder if you give up, but the best option is to look at the WPIlib resources above 
 *    and look at the topic at hand.
 *   
 * 
*/

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
    //joystick.b().whileTrue(new InstantCommand(()->{})); 
    
    //when pressing x it will run an instant command to set the print to true. when the x button goes false it will set the print to false!
    joystick.x().onTrue(objectOfMyFirstClass.cmd_setPrintPeriodic(true)).onFalse(objectOfMyFirstClass.cmd_setPrintPeriodic(false));
     
    //when pressing y is true this will Run and instant command using the factory Commands.runonce();
    // when Y is UNPRESSED it will automatically set the print to false aswell.
    //NOTICE HOW .onFalse is on a seperate line! since there is no ; then the statement keeps going!
    joystick.y().onTrue(Commands.runOnce(()->{objectOfMyFirstClass.setPrintPeriodic(true);}, objectOfMyFirstClass))
    .onFalse(new cmdSetPeriodicPrint(objectOfMyFirstClass, false));
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //joystick.pov(0).whileTrue();
  }



  public Command getAutonomousCommand() {
    /* First put the drivetrain into auto run mode, then run the auto */
    return runAuto;
  }
}
