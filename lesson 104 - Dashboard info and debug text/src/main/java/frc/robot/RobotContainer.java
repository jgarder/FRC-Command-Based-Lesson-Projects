// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.filter.Debouncer.DebounceType;
import edu.wpi.first.wpilibj.shuffleboard.EventImportance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.cmdPrintToRioLog;
import frc.robot.commands.cmd_SequenceOfCommands;

import frc.robot.subsystems.ComplexMechanismCommandFactory;
import frc.robot.subsystems.MyFirstSubsystem;

public class RobotContainer {
  
  private boolean boolTriggersWhenTrue = false;
  /* Setting up bindings for necessary control of the swerve drive platform */
  // The following classes are part of the main mechanism logic factory. every subsystem needs to be added to the factory via dependency injection.
  private final CommandXboxController joystick = new CommandXboxController(0); // My joystick
  public final MyFirstSubsystem mFSS = new MyFirstSubsystem();
  // this is the factory that take all the subsystems and ties them together
  // this is where our operations will be constructed and some logic for each operation
  public final frc.robot.subsystems.ComplexMechanismCommandFactory cmdFactory; //NOTICE THAT WE ARENT INSTANTIATING THIS OBJECT HERE! this is choice at this time but could be for a more important reason 

 

  /* Path follower */
  private Command runAuto = new InstantCommand(()->{});//drivetrain.getAutoPath("Tests");

  
  
  //THIS is our constructor method when our robot container is created/instantiated! it runs once.
  public RobotContainer() {
    cmdFactory = new ComplexMechanismCommandFactory(joystick,null,mFSS);
    configureBindings();
    SetupDashboard();

  }
  private void SetupDashboard()
  {
    //show the command scheduler on the dashboard
    SmartDashboard.putData(CommandScheduler.getInstance());

    //send a delegate to run some code. whatever code you want inside the {}
    SmartDashboard.putData("Say Hello Delegate ", new InstantCommand(()->{System.out.println("Saying hello from a delegate!");}));
    //here we create a new command and bind it to the dashboard as a button. it will run like any other command when clicked from dashboard
    SmartDashboard.putData("Say Hello Cmd ", new cmdPrintToRioLog("Saying hello from a bound command!"));
    //add a subsystem to the shuffleboard tab of your choice
    Shuffleboard.getTab("BMFSS").add(mFSS);
    //add some system to the smartdashboard
    SmartDashboard.putData("DMFSS",mFSS);
    
    // Log Shuffleboard events for command initialize, execute, finish, interrupt
    CommandScheduler.getInstance()
        .onCommandInitialize(
            command ->
                Shuffleboard.addEventMarker(
                    "Command initialized", command.getName(), EventImportance.kNormal));
    CommandScheduler.getInstance()
        .onCommandExecute(
            command ->
                Shuffleboard.addEventMarker(
                    "Command executed", command.getName(), EventImportance.kNormal));
    CommandScheduler.getInstance()
        .onCommandFinish(
            command ->
                Shuffleboard.addEventMarker(
                    "Command finished", command.getName(), EventImportance.kNormal));
    CommandScheduler.getInstance()
        .onCommandInterrupt(
            command ->
                Shuffleboard.addEventMarker(
                    "Command interrupted", command.getName(), EventImportance.kNormal));
    ///////////////////////////////////////////////////////////////////////////////////
  }

  private void configureBindings() {
    //////////////////////////////////////////All Other Button setup///////////////////////////////////////////////////////
    
    //bind the a button to a runnable action while true (while is checked every iteration for trueness)
    //The command that is ran "while true" is instant, therefor it will run once and stop. 
    //BUT the output should run while held, and stop when not so we add a repeatedly decorator to the runnable command to keep it repeating until cancelled. 
    joystick.a().whileTrue(new cmd_SequenceOfCommands().repeatedly());
    
    //binding the onfalse trigger can be done SEPERATE like the follwing line. or done together (inline) like the rest of the examples
    joystick.a().onFalse(Commands.runOnce(()->{System.out.println("button A now false");}));

    //this will bind the B button a series of sequential (in order 1 by 1) commands. Notice it runs once, its a sequential command group without any decorator telling it to repeat
    //see how we can insert any code we want into the instant command? we are just wrapping our code with something the scheduler can run!
    //When testing. notice how holding the button only runs the command once EVEN THOUGH its a while true. 
    joystick.b().whileTrue( cmdFactory.sequenceOfPrints()); 

    //The X button will LOOK like it operates in a manner that May seem like the B button, but will have more timing control.
    //notice how ontrue is a fire and forget. with whiletrue we had to hold the trigger on or it would cancel the entire command composition
        joystick.x().onTrue(cmdFactory.sequenceWithTimeouts())
          .onFalse(Commands.runOnce(()->{System.out.println("button x on false");}));

    //y button runs until you press the left trigger! look at the until decorator in the composition!
    joystick.y().onTrue(cmdFactory.printUntilTrigger());

    // The right bumper on true is normal, so is the print statement, BUT LOOK we have a .debounce of 1 (second) next to the TRIGGER THAT WE WANT TO DEBOUNCE!. 
    // go ahead and try to press this button quickly. This debounce is called the falling edge debounce. 
    // it will fire instantly but will not fire again until the button has been false for a full second. then it will instantly trigger, again until 1 second of false.
    joystick.rightBumper().debounce(1,DebounceType.kFalling)
    .onTrue(new cmdPrintToRioLog("button RBumper on true! debounced Falling for a whole second!, Get button to false for the whole debounce before trying again!"));

    //This Command for the right trigger is also debounced but its on the rising edge of the signal, so it will not trigger UNLESS the signal is true for an entire second!
    //go ahead and click this as much as you want, but you must HOLD it the debounce duration before it will trigger.
    joystick.rightTrigger().debounce(1,DebounceType.kRising)
    .onTrue(new cmdPrintToRioLog("button RTrigger on true! debounced Rising for a whole second! You Must Hold the button for a whole second!"));
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // this is us building a trigger object that will run the code inside the {} to get a true or false answer.
    // our trigger construction just checks our bool "boolTriggersWhenTrue" to see if it is true or false. though we could put anything that returns true and false.
    Trigger isMyBoolTrue = new Trigger(()->{return boolTriggersWhenTrue;});
    // we are now able to use our trigger to trigger a command. we dont need to keep the trigger object for any reason its now built into the binding.
    isMyBoolTrue.onTrue(
      Commands.print(" isMyBoolTrue trigger has went TRUE! this command fired .ontrue() resetting bool to false now!")
      .andThen(Commands.runOnce(()->{boolTriggersWhenTrue = false;}))
      );

    // Pov 0 (up)
    joystick.pov(0).onTrue(
      Commands.runOnce(()->{boolTriggersWhenTrue = true;})
      .alongWith(Commands.print("setting trigger bool true"))
      );

    // THIS BUTTON REQUIRES 2 BUTTONS TO USE!!! READ CAREFULLY AND INTERPRET!
    // also notice the use of alongwith Inside an andthen()! you can sequence groups of parallel commands! 
    // joystick pov 90 (right)
    joystick.povRight().and(joystick.rightStick()).onTrue(cmdFactory.sequenceWithparallel());



    // pov 180 down ( DO NOT USE DOES NOT WORK AS EXPECTED)
    //as of wpilib 2024.3.2 i am not perfectly sure that this toggleontrue works in the manner described.
    // but since its not very used we dont care? it may be our implementation and code! our fault!.  
    // im leaving this, if i learn i will have a place to update!
    // smart students read this and fix my mistakes -> https://docs.wpilib.org/en/stable/docs/software/commandbased/binding-commands-to-triggers.html
    Command jake = new InstantCommand(()->{System.out.print("POV Toggled on!");});
    joystick.pov(180).toggleOnTrue(jake);    
  }






  public Command getAutonomousCommand() {
    /* First put the drivetrain into auto run mode, then run the auto */
    return runAuto;
  }
}
