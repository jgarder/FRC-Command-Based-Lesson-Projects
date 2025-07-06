// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;

/** An example command that uses an example subsystem. */
// NOTE : we are extending the PrintCommand class, this is called inheiritance. we are making our own version that does a more specific task than the file we are extending (often called inheiriting or polymorphism)
// This class will have all the functions of the parent (super) class we are extending
public class SimpleExampleCommand extends PrintCommand {

  //this function is only called once when this object is created using a "new" keyword. it is called the contructor. 
  public SimpleExampleCommand() {
    //Calling Super runs the constructor of the parent type this class extends
    super("Simpler command Executing! you can put any code you want here!");
  }

  
}
