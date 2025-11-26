// Copyright 2021-2025 FRC 6328
// http://github.com/Mechanical-Advantage
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// version 3 as published by the Free Software Foundation or
// available in the root directory of this project.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.

package frc.robot;

// import edu.wpi.first.epilogue.Epilogue;
// import edu.wpi.first.epilogue.logging.errors.ErrorHandler;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.RevLibExamples.RevClosedLoop;
import org.littletonrobotics.junction.LogFileUtil;
import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.NT4Publisher;
import org.littletonrobotics.junction.wpilog.WPILOGReader;
import org.littletonrobotics.junction.wpilog.WPILOGWriter;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends LoggedRobot {
  private final int testRevLibMotorID = 1;
  public RevClosedLoop OurRevLibClosedLoopExample = new RevClosedLoop(testRevLibMotorID);

  public Robot() {
    BootupAdvantageKit();
    // BootupWpilibEpilogueLogger();
  }

  /*
   * https://github.com/wpilibsuite/allwpilib/issues/7103
   * to run ./gradlew simulateJava directly through a terminal,
   * instead of using the Simulate Robot Program task in vscode.
   *  The vscode debugger extension loads the Eclipse-generated classes,
   *  not the gradle-built ones, so running the gradle task directly will load the correct classes.
   * You would need to manually attach the debugger to the gradle task,
   * but may behave oddly due to a mismatch between the JLS and gradle generated sources.
   * Intellisense for the generated classes will also get gradually
   * get more and more out of sync with your project,
   * but unless you're doing really custom things with Epilogue,
   * it should be fine since all you need are the Epilogue class and its bind and update methods
   */
  // UPDATE FIXED USING changes to build.gradle --->>
  // https://github.com/wpilibsuite/vscode-wpilib/pull/717
  // private void BootupWpilibEpilogueLogger() {
  //   enableLiveWindowInTest(true);
  //   DataLogManager.logNetworkTables(true);
  //   DataLogManager.logConsoleOutput(true);
  //   DataLogManager.log("Lesson110 - 2025 8608 Frc Java Lessons");
  //   DataLogManager.start();
  //   Epilogue.configure(
  //       config -> {
  //         // Log only to disk, instead of the default NetworkTables logging
  //         // Note that this means data cannot be analyzed in realtime by a dashboard
  //         // config.backend = new FileBackend(DataLogManager.getLog());

  //         if (isSimulation()) {
  //           // If running in simulation, then we'd want to re-throw any errors that
  //           // occur so we can debug and fix them!
  //           config.errorHandler = ErrorHandler.crashOnError();
  //         }

  //         // Change the root data path
  //         config.root = "AlphaBotsTelemetry";

  //         // Only log critical information instead of the default DEBUG level.
  //         // This can be helpful in a pinch to reduce network bandwidth or log file size
  //         // while still logging important information.
  //         // config.minimumImportance = Logged.Importance.CRITICAL;
  //       });
  //   Epilogue.bind(this);

  //   Epilogue.getConfig().backend.lazy().log("IsCommandDashBoardSetup", false);
  //   SetupDashboard();
  //   Epilogue.getConfig().backend.lazy().log("IsCommandDashBoardSetup", true);
  // }

  private void BootupAdvantageKit() {
    // Record metadata
    Logger.recordMetadata("ProjectName", BuildConstants.MAVEN_NAME);
    Logger.recordMetadata("BuildDate", BuildConstants.BUILD_DATE);
    Logger.recordMetadata("GitSHA", BuildConstants.GIT_SHA);
    Logger.recordMetadata("GitDate", BuildConstants.GIT_DATE);
    Logger.recordMetadata("GitBranch", BuildConstants.GIT_BRANCH);
    switch (BuildConstants.DIRTY) {
      case 0:
        Logger.recordMetadata("GitDirty", "All changes committed");
        break;
      case 1:
        Logger.recordMetadata("GitDirty", "Uncomitted changes");
        break;
      default:
        Logger.recordMetadata("GitDirty", "Unknown");
        break;
    }

    // Set up data receivers & replay source
    switch (Constants.currentMode) {
      case REAL:
        // Running on a real robot, log to a USB stick ("/U/logs")
        Logger.addDataReceiver(new WPILOGWriter());
        Logger.addDataReceiver(new NT4Publisher());
        break;

      case SIM:
        // Running a physics simulator, log to NT
        Logger.addDataReceiver(new NT4Publisher());
        break;

      case REPLAY:
        // Replaying a log, set up replay source
        setUseTiming(false); // Run as fast as possible
        String logPath = LogFileUtil.findReplayLog();
        Logger.setReplaySource(new WPILOGReader(logPath));
        Logger.addDataReceiver(new WPILOGWriter(LogFileUtil.addPathSuffix(logPath, "_sim")));
        break;
    }

    // Start AdvantageKit logger
    Logger.start();
  }

  /** This function is called periodically during all modes. */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler. This is responsible for polling buttons, adding
    // newly-scheduled commands, running already-scheduled commands, removing
    // finished or interrupted commands, and running subsystem periodic() methods.
    // This must be called from the robot's periodic block in order for anything in
    // the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {}

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
