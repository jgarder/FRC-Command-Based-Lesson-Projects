/*
 * FRC #8608 "a dummies guide to programming FRC java"
 * 
 * this guide is just a rewriting of the 100% awesome WPILIB docs! 
 * please just read the zero to robot section and you wont even need a mentor! 
 * 
 * Wpilib references when in trouble :
 *  Command based programming -> https://docs.wpilib.org/en/stable/docs/software/commandbased/index.html
 *  binding commands to triggers -> https://docs.wpilib.org/en/stable/docs/software/commandbased/binding-commands-to-triggers.html#binding-commands-to-triggers
 *  Whiletrue trigger ->  https://docs.wpilib.org/en/stable/docs/software/commandbased/binding-commands-to-triggers.html#whiletrue
 * 
 * lesson 1 - Your first Command based project and binding a command to a trigger. 
 * 
 * you will show a completed lesson folder to your teacher. 
 * -> by branching this in git and updating your branch. 
 * -> or showing them your personal laptop ready to compile/run or simulate your code!
 * -> The Project should always "Build successful" When shown to mentor unless asking a question during developement!
 * requirements to pass this lesson:
 *
 * Task 0 : Project should "Build successful" When shown to mentor
 * 
 * Task 1 : read the comments (green text) below to learn -> 
 *  -> this is the skeleton of a command based robot. take a few minutes to see how it expands on the timed robot
 *  -> robot.java  runs the command scheduler periodically and runs the auto through the scheduler. 
 *  -> when public RobotContainer() is ran all that happens is configureBindings(); that is where we will be binding a trigger today
 *  
 * Task 2 (viewed by Mentor): Run the command m_exampleSubsystem.exampleMethodCommand() by pressing the b button. 
 *  Step 1 of "running" a command is to actually build the command 
 *  Step 2 is to bind that command to a trigger (something that is true or false)
 *  step 3 is now to simply make that trigger go "true". which will run the commands. 
 *  in our case our trigger is going to come from our controller on port 0 that we call m_driverController. 
 *  if we stop pressing the "A" button the trigger for "while false" that would become true would be  "m_driverController.a().Whilefalse(COMMANDS TO RUN GO HERE)"
 *   
 *  you can view the solutions folder if you give up, but the best option is to look at the WPIlib resources above 
 *    and look for WhileTrue and drop the examplemethodcommand into the arguments/parameters 
 *   
 * 
 * This is just a "skeleton", or "boilerplate" of a robots code. its all the stuff that every robot team has to have. 
 *  if a team runs a command based java then they run commands through the command scheduler. many teams have command based java projects, 
 *  but do not run commands.  preferring to instead check periodics (if xButtonPressed ==true then do something) which makes them in fact, not command based. 
 *  to tell if you are command based you will "bind" commands to triggers. these commands will be re-used throughout the entire qualifier/playoff match.
*/

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    new Trigger(m_exampleSubsystem::exampleCondition)
        .onTrue(new ExampleCommand(m_exampleSubsystem));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    //m_driverController.b().whileTrue();
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_exampleSubsystem);
  }
}
