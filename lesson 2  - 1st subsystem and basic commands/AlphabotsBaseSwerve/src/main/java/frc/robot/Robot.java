// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.generated.Constants;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  private final boolean UseLimelight = true;
  double maxrotationalVelocityForLLUpdate = 100;

  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();

    m_robotContainer.drivetrain.getDaqThread().setThreadPriority(99);
  }
  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();

    /**
     * This example of adding Limelight is very simple and may not be sufficient for on-field use.
     * Users typically need to provide a standard deviation that scales with the distance to target
     * and changes with number of tags available.
     *
     * This example is sufficient to show that vision integration is possible, though exact implementation
     * of how to use vision should be tuned per-robot and to the team's specification.
     */
    if (UseLimelight) {
      double rotationalvelocity = m_robotContainer.drivetrain.getPigeon2().getAngularVelocityZWorld().getValueAsDouble();  
      SmartDashboard.putNumber("rotationalvelocity", rotationalvelocity);
      
        ///////////
        ////CAMERA UP
        boolean useMegaTag2 = false; //set to false to use MegaTag1
        boolean doRejectUpdate = false;
        if(useMegaTag2 == false)
        {
          LimelightHelpers.PoseEstimate mt1 = LimelightHelpers.getBotPoseEstimate_wpiBlue(Constants.LimelightName);
          
          if(mt1.tagCount == 1 && mt1.rawFiducials.length == 1)
          {
            if(mt1.rawFiducials[0].ambiguity > .7)
            {
              doRejectUpdate = true;
            }
            if(mt1.rawFiducials[0].distToCamera > 3)
            {
              doRejectUpdate = true;
            }
          }
          if(mt1.tagCount == 0)
          {
            doRejectUpdate = true;
          }
          if(Math.abs(rotationalvelocity) > maxrotationalVelocityForLLUpdate) // if our angular velocity is greater than 720 degrees per second, ignore vision updates
          {
            doRejectUpdate = true;
          }

          if(!doRejectUpdate)
          {
            //m_robotContainer.drivetrainManager.drivetrain.setVisionMeasurementStdDevs(VecBuilder.fill(.5,.5,9999999));
            m_robotContainer.drivetrain.addVisionMeasurement(
                mt1.pose,
                mt1.timestampSeconds);
                return;//only 1 sample per robot periodic
          }
        }
        else if (useMegaTag2 == true)
        {
          

          LimelightHelpers.PoseEstimate mt2 = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(Constants.LimelightName);
          if(Math.abs(rotationalvelocity) > maxrotationalVelocityForLLUpdate) // if our angular velocity is greater than 720 degrees per second, ignore vision updates
          {
            doRejectUpdate = true;
          }
          if(mt2.tagCount == 0)
          {
            doRejectUpdate = true;
          }
          if(!doRejectUpdate)
          {
            //m_robotContainer.drivetrainManager.drivetrain.setVisionMeasurementStdDevs(VecBuilder.fill(.7,.7,9999999));
            m_robotContainer.drivetrain.addVisionMeasurement(
                mt2.pose,
                mt2.timestampSeconds);
                return;//only 1 sample per robot periodic
          }
        }
      ///////////
      }
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
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void teleopExit() {
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
