// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class ExampleCommand extends Command {

  //this function is only called once when this object is created using a "new" keyword
  public ExampleCommand() {
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("command init! you can put any code you want here!");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() { System.out.println("command Executing! you can put any code you want here!");}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
