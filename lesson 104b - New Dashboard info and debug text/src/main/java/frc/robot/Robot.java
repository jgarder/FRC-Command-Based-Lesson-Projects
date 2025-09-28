// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import javax.xml.crypto.Data;

import edu.wpi.first.epilogue.Epilogue;
import edu.wpi.first.epilogue.EpilogueConfiguration;
import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.epilogue.logging.EpilogueBackend;
import edu.wpi.first.epilogue.logging.FileBackend;
import edu.wpi.first.epilogue.logging.errors.ErrorHandler;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.EventImportance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants;
import frc.robot.commands.cmdPrintToRioLog;

@Logged
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  
  private RobotContainer m_robotContainer;

  private Boolean isTeleopInit = false;
  public Robot() {
    enableLiveWindowInTest(true);
    DataLogManager.logNetworkTables(true);
    DataLogManager.logConsoleOutput(true);
    DataLogManager.log("Lesson104b - 2025 8608 Frc Java Lessons");
    DataLogManager.start();
    Epilogue.configure(config -> {
      // Log only to disk, instead of the default NetworkTables logging
      // Note that this means data cannot be analyzed in realtime by a dashboard
      //config.backend = new FileBackend(DataLogManager.getLog());

      if (isSimulation()) {
        // If running in simulation, then we'd want to re-throw any errors that
        // occur so we can debug and fix them!
        config.errorHandler = ErrorHandler.crashOnError();
      }

      // Change the root data path
      config.root = "AlphaBotsTelemetry";

      // Only log critical information instead of the default DEBUG level.
      // This can be helpful in a pinch to reduce network bandwidth or log file size
      // while still logging important information.
      //config.minimumImportance = Logged.Importance.CRITICAL;
    });
    Epilogue.bind(this);
    
    Epilogue.getConfig().backend.lazy().log("IsCommandDashBoardSetup", false);
    SetupDashboard();
    Epilogue.getConfig().backend.lazy().log("IsCommandDashBoardSetup", true);
    
  }
  private void SetupDashboard()
  {
    //show the command scheduler on the dashboard
    SmartDashboard.putData(CommandScheduler.getInstance());

    //send a delegate to run some code. whatever code you want inside the {}
    SmartDashboard.putData("Say Hello Delegate ", new InstantCommand(()->{System.out.println("Saying hello from a delegate!");}));
    //here we create a new command and bind it to the dashboard as a button. it will run like any other command when clicked from dashboard
    SmartDashboard.putData("Say Hello Cmd ", new cmdPrintToRioLog("Saying hello from a bound command!"));
    
    // Log Shuffleboard events for command initialize, execute, finish, interrupt
    //Epilogue.getConfig().backend.log("IsBackendSetup", true);
    CommandScheduler.getInstance()
        .onCommandInitialize(
            command -> 
              Epilogue.getConfig().backend.log("Commands/" + command.getName() + " " + command.hashCode(), "initialized"));
    CommandScheduler.getInstance()
        .onCommandExecute(
            command ->
              Epilogue.getConfig().backend.log("Commands/" + command.getName() + " " + command.hashCode(), "Executing"));
    CommandScheduler.getInstance()
        .onCommandFinish(
            command ->
            Epilogue.getConfig().backend.log("Commands/" + command.getName() + " " + command.hashCode(), "Finished"));
    CommandScheduler.getInstance()
        .onCommandInterrupt(
            command ->
            Epilogue.getConfig().backend.log("Commands/" + command.getName() + " " + command.hashCode(), "Interrupted"));
    ///////////////////////////////////////////////////////////////////////////////////
  }

  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();

    
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    Epilogue.getConfig().backend.lazy().log("AlphabotsTest/" + "isTeleopInit", isTeleopInit);
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    isTeleopInit = true; 

  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void teleopExit() {
    isTeleopInit = false; 
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}

  @Override
  public void simulationPeriodic() {}
}
