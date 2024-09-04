// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {

  private final XboxController m_controller = new XboxController(0);
  private final Timer m_timer = new Timer();
  public int periodicCounter = 1;

  public Robot() {
    m_timer.reset();
    System.out.println("Robot constructor method Running. the fields declared outside this have already constructed such as the timer. ");
  }

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    System.out.println("robotInit method Running (this is functionally same as the constructor you should use that)");

  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    System.out.println("autonomousInit Running MatchTime :" + DriverStation.getMatchTime());
    m_timer.restart();
  }
  
    /** This periodic runs at all times peridocially. */
  @Override
  public void robotPeriodic() {
    periodicCounter++;
    if (periodicCounter % 750.0 == 0) 
    {
      System.out.println("robotPeriodic has ran 750 more times MatchTime :" + DriverStation.getMatchTime());
    }
  }
  /** This periodic runs at all times while disabled peridocially. */
  @Override
  public void disabledPeriodic() {
    if (periodicCounter % 500.0 == 0) 
    {
      System.out.println("disabledPeriodic has ran 500 more times MatchTime :" + DriverStation.getMatchTime());
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    // Drive for 2 seconds
    if (m_timer.get() < 2.0) {
      System.out.println("autonomousPeriodic Running MatchTime :" + DriverStation.getMatchTime());
    } else {
      System.out.println("autonomousPeriodic Running but greater than 2 seconds - MatchTime :" + DriverStation.getMatchTime());
    }
  }

  /** This function is called once each time the robot enters teleoperated mode. */
  @Override
  public void teleopInit() {System.out.println("teleopInit Running");}

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    //you will need a function here that runs ever 20 milliseconds (one period of our periodic)
    if (m_controller.getAButtonPressed()) {
      System.out.println("A button pressed");
    }
  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {System.out.println("testInit Running");}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {System.out.println("testPeriodic Running"); }
}
