// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

//package must match the folders the file is in
package frc.robot;

//the imports are other folders and files used in this file (sometimes it points to libraries from other companies or FRC teams)
import edu.wpi.first.wpilibj.TimedRobot;

//this is the class for this file. the file Robot.java must be for this class.
public class Robot extends TimedRobot {

  //This method has the same name as the class it will always run when an object is instantiated into memory
  public Robot() {
    System.out.println("Robot constructor method Running. the fields declared outside this have already constructed such as the timers and the class this class inheirets from. ");
  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    System.out.println("autonomousInit function now Running");
  }

  //This will run one time everytime the robot is disabled
  //@Override
  //public void disabledInit()
  //{
    //System.out.println("Robot now Disabled");
  //}


  /** This function is called once each time the robot enters teleoperated mode. */
  @Override
  public void teleopInit() {System.out.println("teleopInit function now Running");}

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {System.out.println("testInit function now Running");}
}
