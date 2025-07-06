// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.event.BooleanEvent;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  // This is our xbox controller object that we use around the program.
  private final CommandXboxController controller = new CommandXboxController(0);
  //private final Command myFirstCommand = new PrintCommand("printing a statement from my first command!").withName("My First Command!");
  public Robot() {
    System.out.println("Robot constructor method Running. the fields declared outside this have already constructed such as the timer. ");

    // notice that last project we checked the xbox controller every periodic loop to see if any button is pressed. 
    // in the command based framework we declare triggers and bindings at the bootup of the robot (i.e this code only runs ONCE at startup)
    // controller -> A button -> when it becomes true -> schedule my first command
    //controller.a().onTrue(myFirstCommand);

    //we can bind the same command to run under different triggers
    //SmartDashboard.putData("My print CMD",myFirstCommand);
  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    System.out.println("autonomousInit Running");
  }
  
    /** This periodic runs at all times peridocially. */
  @Override
  public void robotPeriodic() {
    // the robotPeriodic runs each periodic loop in any state (disabled,auton,teleop,test)
    // the command scheduler must run each loop to run teh scheduled commands! without this piece of code the command based framework DOES NOT WORK!
    //CommandScheduler.getInstance().run();
  }
  /** This periodic runs at all times while disabled peridocially. */
  @Override
  public void disabledPeriodic() {
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
  }

  /** This function is called once each time the robot enters teleoperated mode. */
  @Override
  public void teleopInit() {
    //when teleop begins we like to freeze the robot and stop it from doing whatever it was doing. if this doesnt work for us then we can change it later.
    CommandScheduler.getInstance().cancelAll(); 
    System.out.println("teleopInit Running");
  }

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    //nothing needs to run only during teleop in this project.
  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {System.out.println("testInit Running");}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {System.out.println("testPeriodic Running"); }
}
