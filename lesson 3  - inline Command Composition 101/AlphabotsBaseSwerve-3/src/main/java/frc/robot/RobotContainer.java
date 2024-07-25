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
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.cmdPrintToRioLog;
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
    .andThen(Commands.runOnce(()->{System.out.println("button A now false");}))
    ); 

    //The X button will LOOK like it operates in a manner that May seem like the B button, but will have more timing control.
    //notice how ontrue is a fire and forget. with whiletrue we had to hold the trigger on or it would cancel the entire command composition
        joystick.x().onTrue(
            Commands.sequence(
              new cmdPrintToRioLog("button B While True 1"),//notice the LACK OF an extra parameter for timeout!  
              new cmdPrintToRioLog("button B While True 2",.2),//notice the number for timeouts! its using a different constructor!
              new cmdPrintToRioLog("button B While True 3",.3),
              new cmdPrintToRioLog("button B While True 4",.4),
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
  

    ////////////////////////////////other task during binding?/////////////////////////////////////////
    drivetrain.registerTelemetry(logger::telemeterize);
  }



  public Command getAutonomousCommand() {
    /* First put the drivetrain into auto run mode, then run the auto */
    return runAuto;
  }
}
