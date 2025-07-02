// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  // This is our xbox controller object that we use around the program.
  //private final XboxController controller = new XboxController(0);

  public Robot() {
    System.out.println("Robot constructor method Running. the fields declared outside this have already constructed such as the timer. ");
  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    System.out.println("autonomousInit Running");
  }
  
    /** This periodic runs at all times peridocially. */
  @Override
  public void robotPeriodic() {
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
  public void teleopInit() {System.out.println("teleopInit Running");}

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    //if (controller.getAButtonPressed()) {
      //System.out.println("A button pressed");
    //}
  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {System.out.println("testInit Running");}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {System.out.println("testPeriodic Running"); }
}
