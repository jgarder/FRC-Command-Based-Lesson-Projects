// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

//package must match the folders the file is in. this is also what other files would use to "import" this class (This class is frc.robot.Robot)!
package frc.robot;

//the imports are other folders and files used in this file (sometimes it points to libraries from other companies or FRC teams)
import edu.wpi.first.wpilibj.TimedRobot;

//this is the class for this file. the file Robot.java must be for this class.
public class Robot extends TimedRobot {

  //This method has the same name as the class it will always run when an object is instantiated into memory
  public Robot() {
    System.out.println(" World!");
  }

  
}
