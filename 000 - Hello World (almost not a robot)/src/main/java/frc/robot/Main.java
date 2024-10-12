// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

//this file is the start point of any java program, Main will be called by our simulator or our Robo rio hardware. the start to our "program" or "app" is booting up our WPILib Robot class.

//package must match the folders the file is in. this is also what other files would use to "import" this class!
package frc.robot;

//the imports are other folders and files used in this file (sometimes it points to libraries from other companies or FRC teams)
import edu.wpi.first.wpilibj.RobotBase;

/**
 * Do NOT add any static variables to this class, or any initialization at all. Unless you know what
 * you are doing, do not modify this file except to change the parameter class to the startRobot
 * call.
 */
public final class Main {
  /**
   * Main initialization function. Do not perform any initialization here.
   *
   * <p>If you change your main robot class, change the parameter type.
   */
  public static void main(String... args) {
    System.out.print("Hello ");
    RobotBase.startRobot(Robot::new);
  }
}
