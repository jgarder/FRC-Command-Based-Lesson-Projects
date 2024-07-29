// examples in these files are from wpilib please read the everything regarding this project if you want to succeed!
// command composition is used extensivly here in robotcontainer or anywhere we are building long strings of differetn commands for different subsystems. 
// command composition -> https://docs.wpilib.org/en/stable/docs/software/commandbased/command-compositions.html
// binding triggers is stuff like .ontrue(),.onfalse(),.negate(), .and(), these are used to fire a command when trigger is true. (button is pressed)
// binding triggers (buttons) -> https://docs.wpilib.org/en/stable/docs/software/commandbased/binding-commands-to-triggers.html
// for some advanced knowledge on what is actually scheduling and running the commands we make. read this -> https://docs.wpilib.org/en/stable/docs/software/commandbased/command-scheduler.html

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;

import edu.wpi.first.math.filter.Debouncer.DebounceType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.cmdPrintToRioLog;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.MyFirstSubsystem;

public class RobotContainer {
  private double MaxSpeed = TunerConstants.kSpeedAt12VoltsMps; // kSpeedAt12VoltsMps desired top speed
  private double MaxAngularRate = 1.5 * Math.PI; // 3/4 of a rotation per second max angular velocity
  private boolean boolTriggersWhenTrue = false;
  /* Setting up bindings for necessary control of the swerve drive platform */
  private final CommandXboxController joystick = new CommandXboxController(0); // My joystick
  public final CommandSwerveDrivetrain drivetrain = TunerConstants.DriveTrain; // My drivetrain


  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
      .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
      .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // I want field-centric
                                                               // driving in open loop

  /* Path follower */
  private Command runAuto = drivetrain.getAutoPath("Tests");

  private final Telemetry logger = new Telemetry(MaxSpeed);
  
  //THIS is our constructor method when our robot container is created/instantiated! it runs once.
  public RobotContainer() {
    configureBindings();
  }

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
    
    //bind the a button to a runnable action while true (while is checked every iteration for trueness)
    //The command that is ran "while true" is instant, therefor it will run once and stop. 
    //BUT the output should run while held, and stop when not so we add a repeatedly decorator to the runnable command to keep it repeating until cancelled. 
    joystick.a().whileTrue(new cmdPrintToRioLog("button A While True").repeatedly());
    
    //binding the onfalse trigger can be done SEPERATE like the follwing line. or done together (inline) like the rest of the examples
    joystick.a().onFalse(Commands.runOnce(()->{System.out.println("button A now false");}));

    //this will bind the B button a series of sequential (in order 1 by 1) commands. Notice it runs once, its a sequential command group without any decorator telling it to repeat
    //see how we can insert any code we want into the instant command? we are just wrapping our code with something the scheduler can run!
    //When testing. notice how holding the button only runs the command once EVEN THOUGH its a while true. 
    joystick.b().whileTrue(
      new InstantCommand(()->{System.out.println("B button on true");})
    .andThen(new cmdPrintToRioLog("button B While True 2"))
    .andThen(Commands.runOnce(()->{System.out.println("button b true 3");}))
    ); 

    //The X button will LOOK like it operates in a manner that May seem like the B button, but will have more timing control.
    //notice how ontrue is a fire and forget. with whiletrue we had to hold the trigger on or it would cancel the entire command composition
        joystick.x().onTrue(
            Commands.sequence(
              new cmdPrintToRioLog("button B While True 1"),//notice the LACK OF an extra parameter for timeout!  
              new cmdPrintToRioLog("button B While True 2",.2),//notice the number for timeouts! its using a different constructor!
              new cmdPrintToRioLog("button B While True 3",.3),
              new cmdPrintToRioLog("button B While True 4",.001),
              new cmdPrintToRioLog("button B While True 5",.5),
              new cmdPrintToRioLog("button B While True 6",.6)
            )
          )
          .onFalse(Commands.runOnce(()->{System.out.println("button x on false");}));

    //y button runs until you press the left trigger! look at the until decorator in the composition!
    joystick.y().onTrue(
      Commands.run(()->{System.out.println("button Y onTrue Run until press of left Trigger!!");})
      .until(()->joystick.leftTrigger().getAsBoolean())
    );

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
    joystick.povRight().and(joystick.rightStick())
    .onTrue(
      new cmdPrintToRioLog("pressing right POV and Right stick button together! sequence soon!")
      .alongWith(new WaitCommand(1))
        .andThen(new cmdPrintToRioLog("Sequence that fires after the 1 second - 1 "),
        new cmdPrintToRioLog("Sequence that fires after the 1 second - 2 "),
        new cmdPrintToRioLog("Sequence that fires after the 1 second - 3 "),
        new cmdPrintToRioLog("Sequence that fires after the 1 second - 4 ")
          .alongWith(new cmdPrintToRioLog("Sequence that fires after the 1 second - 4 parallel! ")),
        new cmdPrintToRioLog("Sequence that fires after the 1 second - 5 "),
        new cmdPrintToRioLog("Sequence that fires after the 1 second - 6 "),
        new WaitCommand(1)
      )
      .andThen(Commands.print("This fires after the sequence before it. its another andthen outside the last alongwith or andthen"))
    );



    // pov 180 down ( DO NOT USE DOES NOT WORK AS EXPECTED)
    //as of wpilib 2024.3.2 i am not perfectly sure that this toggleontrue works in the manner described.
    // but since its not very used we dont care? it may be our implementation and code! our fault!.  
    // im leaving this, if i learn i will have a place to update!
    // smart students read this and fix my mistakes -> https://docs.wpilib.org/en/stable/docs/software/commandbased/binding-commands-to-triggers.html
    Command jake = new InstantCommand(()->{System.out.print("POV Toggled on!");});
    joystick.pov(180).toggleOnTrue(jake);



    ////////////////////////////////other task during binding?/////////////////////////////////////////
    drivetrain.registerTelemetry(logger::telemeterize);
  }



  public Command getAutonomousCommand() {
    /* First put the drivetrain into auto run mode, then run the auto */
    return runAuto;
  }
}
